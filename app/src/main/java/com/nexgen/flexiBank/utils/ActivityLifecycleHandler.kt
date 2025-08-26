package com.nexgen.flexiBank.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import java.lang.ref.WeakReference

/**
 * Utility class to track the current active activity and provide callbacks for activity lifecycle events.
 */
object ActivityLifecycleHandler {
    private val lifecycleListeners = mutableListOf<ActivityLifecycleListener>()
    private var activityReference: WeakReference<Activity>? = null

    val currentActivity: Activity?
        get() = activityReference?.get()

    /**
     * Initialize the handler with the application instance.
     * This should be called in your Application's onCreate method.
     */
    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                // Not needed for this implementation
            }

            override fun onActivityStarted(activity: Activity) {
                // Not needed for this implementation
            }

            override fun onActivityResumed(activity: Activity) {
                activityReference = WeakReference(activity)
                if (activity is ComponentActivity) {
                    lifecycleListeners.forEach { it.onActivityResumed(activity) }
                }
            }

            override fun onActivityPaused(activity: Activity) {
                // Not needed for this implementation
            }

            override fun onActivityStopped(activity: Activity) {
                // Not needed for this implementation
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                // Not needed for this implementation
            }

            override fun onActivityDestroyed(activity: Activity) {
                if (activityReference?.get() == activity) {
                    activityReference = null
                }
            }
        })
    }

    /**
     * Register a listener for activity lifecycle events.
     */
    fun registerListener(listener: ActivityLifecycleListener) {
        if (!lifecycleListeners.contains(listener)) {
            lifecycleListeners.add(listener)
        }
    }

    /**
     * Unregister a previously registered listener.
     */
    fun unregisterListener(listener: ActivityLifecycleListener) {
        lifecycleListeners.remove(listener)
    }

    /**
     * Interface for activity lifecycle event callbacks.
     */
    interface ActivityLifecycleListener {
        fun onActivityResumed(activity: ComponentActivity)
    }
}
