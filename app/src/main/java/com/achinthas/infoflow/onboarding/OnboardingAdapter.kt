package com.achinthas.infoflow.onboarding

import android.content.Context
import android.os.PowerManager
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {


    private val fragments: List<Fragment> = buildList {
        add(WelcomeFragment())

        if (!isNotificationListenerEnabled(activity)) {
            add(NotificationPermFragment())
        }

        if (!isIgnoringBatteryOptimizations(activity)) {
            add(BatteryOptimizationPermFragment())
        }

        add(BackgroundAutoStartPermFragment())
        add(NotificationPostPermFragment())
        add(FinalStepFragment())
    }

    override fun getItemCount(): Int {
        return fragments.size
    }


    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
    // fun for check notification lister already got access
    fun isNotificationListenerEnabled(context: Context): Boolean {
        val enabledListeners = NotificationManagerCompat.getEnabledListenerPackages(context)
        return enabledListeners.contains(context.packageName)
    }

    // fun for check battery opt already got access
    fun isIgnoringBatteryOptimizations(context: Context): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return powerManager.isIgnoringBatteryOptimizations(context.packageName)
    }
}