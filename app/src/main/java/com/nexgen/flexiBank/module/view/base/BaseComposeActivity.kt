package com.nexgen.flexiBank.module.view.base

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nexgen.flexiBank.common.ModelPreferencesManager
import com.nexgen.flexiBank.network.RemoteDataSource
import com.nexgen.flexiBank.repository.BaseRepository
import com.nexgen.flexiBank.viewmodel.ViewModelFactory

abstract class BaseComposeActivity<VM : ViewModel, R : BaseRepository> : ComponentActivity() {

    protected lateinit var viewModel: VM
    lateinit var remoteDataSource: RemoteDataSource
    var lockScreen: String = "true"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ModelPreferencesManager.with(application)
        remoteDataSource = RemoteDataSource()

        val factory = ViewModelFactory(getRepository())
        viewModel = ViewModelProvider(this, factory)[getViewModel()]
        setContent {
            MyAppWithScaffold()
        }
    }

    @Composable
    fun MyAppWithScaffold() {
        Scaffold(
            topBar = {},
            content = { padding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    ComposeContent()
                }
            })
    }


    @Composable
    protected abstract fun ComposeContent()

    @Composable
    protected open fun ContentPreview() {
        ComposeContent()
    }

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
     * Get the repository for this activity
     */
    abstract fun getRepository(): R
}

