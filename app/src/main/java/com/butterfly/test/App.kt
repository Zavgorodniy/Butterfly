package com.butterfly.test

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.butterfly.test.BuildConfig.PREF_NAME

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
        lateinit var prefs: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefs = getSharedPreferences()
    }

    private fun getSharedPreferences() = instance
        .applicationContext
        .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
}