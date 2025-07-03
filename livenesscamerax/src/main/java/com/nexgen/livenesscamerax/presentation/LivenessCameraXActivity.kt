package com.nexgen.livenesscamerax.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nexgen.camera.di.CameraModule
import com.nexgen.domain.model.exceptions.LivenessCameraXException
import com.nexgen.livenesscamerax.databinding.LivenessCameraxActivityBinding
import com.nexgen.livenesscamerax.di.LibraryModule
import com.nexgen.livenesscamerax.di.LibraryModule.container

class LivenessCameraXActivity : AppCompatActivity() {

    private val resultHandler by lazy { container.provideResultLivenessRepository() }
    private lateinit var binding: LivenessCameraxActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LivenessCameraxActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeModules()
    }

    private fun initializeModules() {
        LibraryModule.initializeDI(application)
        CameraModule.initializeDI(application)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        resultHandler.error(LivenessCameraXException.UserCanceledException())
    }
}
