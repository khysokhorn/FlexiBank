package com.nexgen.flexiBank

import android.app.Application
import com.nexgen.flexiBank.common.ModelPreferencesManager
import com.nexgen.flexiBank.module.crash.GlobalCrashHandler
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalCrashHandler.initialize(applicationContext)
        ModelPreferencesManager.with(this)
    }
}