package com.achinthas.infoflow.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {


    private val fragments = listOf(
        WelcomeFragment(),
        NotificationPermFragment(),
        BatteryOptimizationPermFragment(),
        BackgroundAutoStartPermFragment(),
        NotificationPostPermFragment(),
        FinalStepFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }


    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}