package com.trainee.project.swiggy

import android.app.Application
import com.trainee.project.swiggy.SharedPrefernces.SharedPreferencesManager

class Swiggy:Application() {
    override fun onCreate() {
        super.onCreate()

//        SharedPreferencesManager.init(applicationContext)
    }
}