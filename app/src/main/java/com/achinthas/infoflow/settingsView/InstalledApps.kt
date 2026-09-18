package com.achinthas.infoflow.settingsView

import android.graphics.drawable.Drawable

data class InstalledApps(
    val appName: String,
    val packageName: String,
    val icon: Drawable
)