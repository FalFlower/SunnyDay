package com.sunnyday

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyDayApplication:Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        const val TOKEN="UwB7mMZ2Rh3pU0N2"
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}