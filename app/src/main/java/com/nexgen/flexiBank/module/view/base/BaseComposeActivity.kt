package com.nexgen.flexiBank.module.view.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.nexgen.flexiBank.common.ModelPreferencesManager
import com.nexgen.flexiBank.network.RemoteDataSource
import com.nexgen.flexiBank.repository.BaseRepository
import com.nexgen.flexiBank.viewmodel.ViewModelFactory

/**
 * Base activity class for screens using Jetpack Compose UI
 * Follows the same architecture pattern as BaseMainActivity but tailored for Compose
 */
abstract class BaseComposeActivity<VM : ViewModel, B : ViewBinding, R : BaseRepository> : ComponentActivity() {

    lateinit var binding: B
    protected lateinit var viewModel: VM
    lateinit var remoteDataSource: RemoteDataSource
    var lockScreen: String = "true"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ModelPreferencesManager.with(application)
        remoteDataSource = RemoteDataSource()

        // Set up system UI appearance
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        @Suppress("DEPRECATION")
        window.statusBarColor = Color.TRANSPARENT

        // Set up binding and ViewModel
        binding = getActivityBinding()
        val factory = ViewModelFactory(getRepository())
        viewModel = ViewModelProvider(this, factory)[getViewModel()]

        // Set content view from binding
        setContentView(binding.root)

        // Initialize Compose content
        initComposeContent()
    }

    /**
     * Initialize the Compose content
     * This should be implemented by subclasses to set up their Compose UI
     */
    protected abstract fun initComposeContent()

    /**
     * Disable touch interactions with the screen
     */
    fun disableTouch() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    /**
     * Enable touch interactions with the screen
     */
    fun enableTouch() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    /**
     * Get the ViewModel class
     */
    abstract fun getViewModel(): Class<VM>

    /**
     * Get the ViewBinding for this activity
     */
    abstract fun getActivityBinding(): B

    /**
     * Get the repository for this activity
     */
    abstract fun getRepository(): R
}
