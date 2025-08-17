package com.nexgen.flexiBank

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.request.crossfade
import com.nexgen.flexiBank.common.ModelPreferencesManager
import com.nexgen.flexiBank.module.crash.GlobalCrashHandler
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication : Application(), SingletonImageLoader.Factory {

    override fun onCreate() {
        super.onCreate()
        GlobalCrashHandler.initialize(applicationContext)
        ModelPreferencesManager.with(this)
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader.Builder(context)
            .crossfade(true)
            .build()
    }
}