package com.achinthas.infoflow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.achinthas.infoflow.onboarding.OnboardingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {

        // splash screen implements
        installSplashScreen()

        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // onboarding status save on shared preference
        val sharedPref = getSharedPreferences("app_pref", Context.MODE_PRIVATE)
        val isCompleted = sharedPref.getBoolean("is_onboarding_completed", false)

        if (!isCompleted) {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
            return
        }


        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    replaceFragment(HomeViewFragment())
                    true
                }

                R.id.bottom_notification -> {
                    replaceFragment(NotificationViewFragment())
                    true
                }

                R.id.bottom_settings -> {
                    replaceFragment(SettingsViewFragment())
                    true
                }

                else -> false
            }
        }

        replaceFragment(HomeViewFragment())
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()

    }
}