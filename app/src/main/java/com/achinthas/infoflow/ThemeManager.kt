package com.achinthas.infoflow

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {

    private const val PREF_NAME = "theme_prefs"
    private const val KEY_THEME = "selected_theme"

    const val THEME_LIGHT = "light"
    const val THEME_DARK = "dark"
    const val THEME_SYSTEM = "system"

    fun saveTheme(context: Context, theme: String){
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pref.edit().putString(KEY_THEME, theme).apply()
        applyTheme(theme)
    }

    fun applyTheme(theme: String){
        when(theme){
            THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            THEME_SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    fun getSavedTheme(context: Context): String{
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pref.getString(KEY_THEME, THEME_SYSTEM) ?: THEME_SYSTEM
    }

    fun applySaveTheme(context: Context){
        applyTheme(getSavedTheme(context))
    }

}