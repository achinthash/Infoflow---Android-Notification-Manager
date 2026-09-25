package com.achinthas.infoflow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale


class HomeViewFragment : Fragment() {


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



    }


}