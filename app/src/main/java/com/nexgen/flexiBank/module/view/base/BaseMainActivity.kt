package com.nexgen.flexiBank.module.view.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.nexgen.flexiBank.common.ModelPreferencesManager
import com.nexgen.flexiBank.network.RemoteDataSource
import com.nexgen.flexiBank.repository.BaseRepository
import com.nexgen.flexiBank.viewmodel.ViewModelFactory

abstract class BaseMainActivity<VM : ViewModel, B : ViewBinding,
        R : BaseRepository> : AppCompatActivity() {

    lateinit var binding: B
    private lateinit var viewModel: VM
    lateinit var remoteDataSource: RemoteDataSource
    var lockScreen: String = "true"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ModelPreferencesManager.with(application)
        remoteDataSource = RemoteDataSource()

        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        @Suppress("DEPRECATION")
        window.statusBarColor = Color.TRANSPARENT

        binding = getActivityBinding()
        val factory = ViewModelFactory(getRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())

        setContentView(binding.root)
    }


    fun disableTouch() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun enableTouch() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getActivityBinding(): B

    abstract fun getRepository(): R
}
