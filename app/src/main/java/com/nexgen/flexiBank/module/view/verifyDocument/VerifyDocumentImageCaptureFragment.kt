package com.nexgen.flexiBank.module.view.verifyDocument

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.FragmentVerifyDocumentBinding
import com.nexgen.flexiBank.module.view.base.BaseFragment
import com.nexgen.flexiBank.module.view.verifyDocument.viewModel.VerifyDocumentViewModel
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class VerifyDocumentImageCaptureFragment() : BaseFragment<VerifyDocumentViewModel, FragmentVerifyDocumentBinding, AppRepository>() {
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var imageCapture: ImageCapture

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!allPermissionsGranted()) {
            activityResultLauncher.launch(REQUIRED_PERMISSIONS)
        }
        startCamera()
        binding.btnCapture.setOnClickListener {
            takeCamera()
        }
        cameraExecutor = Executors.newSingleThreadExecutor()

    }

    private fun startCamera() {
        val cameraProviderFeature = ProcessCameraProvider.getInstance(requireContext())
        imageCapture = ImageCapture.Builder().setTargetAspectRatio(AspectRatio.RATIO_16_9).setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build()
        cameraProviderFeature.addListener({
            try {
                val cameraProvider: ProcessCameraProvider = cameraProviderFeature.get()
                val cameraSelection = CameraSelector.DEFAULT_BACK_CAMERA
                val display = (requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
                var rotation = Surface.ROTATION_0
                if (display.rotation == Surface.ROTATION_0) {
                    rotation = Surface.ROTATION_90
                } else if (display.rotation == Surface.ROTATION_270) {
                    rotation = Surface.ROTATION_180
                }
                val preview = Preview.Builder().setTargetRotation(rotation).build().also {
                    it.surfaceProvider = binding.cameraPreview.surfaceProvider
                }
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(lifecycleOwner = this, cameraSelection, preview, imageCapture)
            } catch (error: Exception) {
                Timber.tag(TAG).e(error, "Use case binding failed")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takeCamera() {
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())
        val outputDirectory = File(requireContext().filesDir, "images").apply { mkdirs() }
        val photoFile = File(outputDirectory, name)
        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(outputOption, ContextCompat.getMainExecutor(requireContext()), object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val msg = "Photo capture succeeded: ${outputFileResults.savedUri}"
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                previewDocImage(outputFileResults.savedUri.toString())
                Timber.tag(TAG).d(msg)
            }

            override fun onError(exc: ImageCaptureException) {
                Timber.tag(TAG).e(exc, "Photo capture failed: ${exc.message}")
            }
        })
    }

    fun previewDocImage(imageUri: String) {
        val navigation = findNavController()
        val bundle = Bundle()
        bundle.putString("imgUri", imageUri)
        navigation.navigate(R.id.action_verify_document_navigation_to_verifyCapturedDocument, bundle)
    }

    override fun getViewModel(): Class<VerifyDocumentViewModel> = VerifyDocumentViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentVerifyDocumentBinding = FragmentVerifyDocumentBinding.inflate(inflater, container, false)

    override fun getRepository(): AppRepository = AppRepository(remoteDataSource.buildApi(requireActivity(), ApiInterface::class.java))

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10

    }

}