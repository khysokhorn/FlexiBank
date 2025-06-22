package com.nexgen.flexiBank.module.view.verifyDocument

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.nexgen.flexiBank.databinding.FragmentLivelinessCaptureBinding
import com.nexgen.flexiBank.module.view.base.BaseFragment
import com.nexgen.flexiBank.module.view.verifyDocument.viewModel.VerifyDocumentViewModel
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LivelinessCaptureFragment : BaseFragment<VerifyDocumentViewModel, FragmentLivelinessCaptureBinding, AppRepository>() {
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var imageCapture: ImageCapture

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startCamera()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun startCamera() {
        val cameraProviderFeature = ProcessCameraProvider.getInstance(requireContext())
        imageCapture = ImageCapture.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
        cameraProviderFeature.addListener({
            try {
                val cameraProvider: ProcessCameraProvider = cameraProviderFeature.get()
                val cameraSelection = CameraSelector.DEFAULT_BACK_CAMERA
                val display =
                    (requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
                var rotation = Surface.ROTATION_0
                if (display.rotation == Surface.ROTATION_0) {
                    rotation = Surface.ROTATION_90
                } else if (display.rotation == Surface.ROTATION_270) {
                    rotation = Surface.ROTATION_180
                }
                val preview = Preview.Builder()
                    .setTargetRotation(rotation)
                    .build()
                    .also {
                        it.surfaceProvider = binding.cameraPreview.surfaceProvider
                    }
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(lifecycleOwner = this, cameraSelection, preview, imageCapture)
            } catch (error: Exception) {
                Timber.tag(TAG).e(error, "Use case binding failed")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    companion object {
        const val TAG = "LivelinessCaptureFragment"
    }

    override fun getViewModel(): Class<VerifyDocumentViewModel> = VerifyDocumentViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentLivelinessCaptureBinding = FragmentLivelinessCaptureBinding.inflate(layoutInflater)

    override fun getRepository(): AppRepository = AppRepository(remoteDataSource.buildApi(requireActivity(), ApiInterface::class.java))

}

