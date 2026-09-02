package com.achinthas.infoflow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.achinthas.infoflow.onboarding.OnboardingActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // splash screen implements
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startActivity(Intent(this, OnboardingActivity::class.java))


//        val sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
//        val isCompleted = sharedPref.getBoolean("is_onboarding_completed", false)
//
//        if (!isCompleted) {
//            startActivity(Intent(this, OnboardingActivity::class.java))
//            finish()
//            return
//        }
    }
}