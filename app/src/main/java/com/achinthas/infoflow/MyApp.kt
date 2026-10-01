package com.achinthas.infoflow

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ThemeManager.applySaveTheme(this)
    }
}