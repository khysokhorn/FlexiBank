package com.nexgen.livenesscamerax

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

internal class CameraXApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeTimber()
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
