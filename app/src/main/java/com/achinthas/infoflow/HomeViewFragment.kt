package com.achinthas.infoflow

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.button.MaterialButtonToggleGroup

class HomeViewFragment : Fragment() {


    private lateinit var notificationRepository: NotificationRepository
    private lateinit var appRepository: AppRepository

    private lateinit var notificationBarChart : BarChart


    private lateinit var last7NotificationsCountByDay : List<DailyNotificationCount>
    private lateinit var last7WeeksNotificationsCounts : List<WeeklyNotificationCount>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtGreeting = view.findViewById<TextView>(R.id.txtGreeting)
        val txtCurrentDate = view.findViewById<TextView>(R.id.txtCurrentDate)
        val txtTodayNotificationCount = view.findViewById<TextView>(R.id.txtTodayNotificationCount)
        val txtTodayUnreadNotificationCount = view.findViewById<TextView>(R.id.txtTodayUnreadNotificationCount)
        val txtTotalNotificationCount = view.findViewById<TextView>(R.id.txtTotalNotificationCount)
        val txtTotalAppCount = view.findViewById<TextView>(R.id.txtTotalAppCount)

        val chartPeriodToggle = view.findViewById<MaterialButtonToggleGroup>(R.id.chartPeriodToggle)
        notificationBarChart = view.findViewById(R.id.notificationBarChart)


        // bar chart
        // Daily / Weekly switch
        chartPeriodToggle.addOnButtonCheckedListener { _, checkedId, isChecked ->

            if (!isChecked) return@addOnButtonCheckedListener

            when (checkedId) {

                R.id.btnDaily -> {
                    showDailyChart()
                }

                R.id.btnWeekly -> {
                    showWeeklyChart()
                }
            }
        }

        // greeting
        val hour = LocalTime.now().hour

        val greeting = when {
            hour < 12 -> "Good Morning,"
            hour < 18 -> "Good Afternoon,"
            else -> "Good Evening,"
        }

        txtGreeting.text = greeting

        // current date
        val today = LocalDate.now()

        val formattedDate = today.format(
            DateTimeFormatter.ofPattern("EEE, MMMM, dd, yyyy", Locale.ENGLISH)
        )

        txtCurrentDate.text = formattedDate


        // db
        viewLifecycleOwner.lifecycleScope.launch {

            val database = AppDatabase.getDatabase(requireContext())
            val notificationRepository = NotificationRepository(database.notificationDao())
            val appRepository = AppRepository(database.appDao())


            // bar chart

            last7NotificationsCountByDay = notificationRepository.getLast7NotificationsCountByDay()
            last7WeeksNotificationsCounts = notificationRepository.getLast7WeeksNotificationsCounts()

            // initial shows
            showDailyChart()

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val startOfDay = calendar.timeInMillis

            val todayCount = notificationRepository.getTodayNotificationCount(startOfDay)

            // today notification count (current date)
            txtTodayNotificationCount.text = todayCount.toString()

            // total today notification count
            val totalNotificationCount = notificationRepository.getTotalNotificationCount()
            txtTotalNotificationCount.text = totalNotificationCount.toString()

            // today total unread count
            val todayUnreadCount = notificationRepository.getTodayUnreadCount(startOfDay)
            txtTodayUnreadNotificationCount.text = todayUnreadCount.toString()

            // total apps count
            val totalAppsCount = appRepository.getTotalAppsCount()
            txtTotalAppCount.text = totalAppsCount.toString()

        }

    }


    private fun updateChart(entries: List<BarEntry>, labels: List<String>){

        // 2. Set up the dataset styling
        val dataSet = BarDataSet(entries, "Notifications").apply {
            color = Color.parseColor("#4CAF50") // Green bars
            valueTextColor = Color.BLACK
            valueTextSize = 12f
        }

        // 3. Bind dataset to BarData
        val barData = BarData(dataSet).apply {
            barWidth = 0.6f // Thickness of bars
        }

        // 4. Custom X-Axis Labels (Day Names)
        val xAxis = notificationBarChart.xAxis
        xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(labels)
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            granularity = 1f // Minimum step between labels
        }

        // 5. Clean up chart UI
        notificationBarChart.apply {
            data = barData
            legend.isEnabled = false
            setDrawGridBackground(false)
            description.isEnabled = false // Hide default description
            axisRight.isEnabled = false   // Hide right Y-axis
            animateY(1000) // Add vertical entry animation
            invalidate()
        }

    }
    private fun showDailyChart(){

        val entries = ArrayList<BarEntry>()

        last7NotificationsCountByDay.forEachIndexed { index, item ->
            entries.add(
                BarEntry(index.toFloat(), item.count.toFloat())
            )
        }

        val days  = last7NotificationsCountByDay.map {
                LocalDate.parse(it.date)
                    .format(DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH))
            }

        updateChart(entries, days)
    }


    private fun showWeeklyChart(){

        val entries = ArrayList<BarEntry>()

        last7WeeksNotificationsCounts.forEachIndexed { index, item ->
            entries.add(
                BarEntry(index.toFloat(), item.count.toFloat())
            )
        }

        // Formatter to read
        val dbDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        // Formatter to display (Sep 21)
        val labelFormatter = DateTimeFormatter.ofPattern("MMM dd", Locale.ENGLISH)

        val weekLabels: List<String> = last7WeeksNotificationsCounts.map { data ->
            val startDate = LocalDate.parse(data.week_start_date, dbDateFormatter)
            val endDate = startDate.plusDays(6)

            val startStr = startDate.format(labelFormatter)
            val endStr = endDate.format(labelFormatter)

            "$startStr - $endStr"
        }

        updateChart(entries, weekLabels)

    }


}