package com.nexgen.flexiBank

import android.app.Application
import com.nexgen.flexiBank.common.ModelPreferencesManager

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ModelPreferencesManager.with(this)
    }
}