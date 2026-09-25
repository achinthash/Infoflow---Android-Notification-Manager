package com.achinthas.infoflow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


class HomeViewFragment : Fragment() {


    private lateinit var notificationRepository: NotificationRepository
    private lateinit var appRepository: AppRepository

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




        viewLifecycleOwner.lifecycleScope.launch {

            val database = AppDatabase.getDatabase(requireContext())
            val notificationRepository = NotificationRepository(database.notificationDao())
            val appRepository = AppRepository(database.appDao())

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





}