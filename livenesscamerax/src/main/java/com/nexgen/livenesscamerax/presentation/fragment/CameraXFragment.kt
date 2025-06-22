package com.nexgen.livenesscamerax.presentation.fragment

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nexgen.camera.CameraX
import com.nexgen.camera.core.callback.CameraXCallback
import com.nexgen.camera.core.callback.CameraXCallbackFactory
import com.nexgen.camera.navigation.CameraXNavigation
import com.nexgen.core.extensions.observeOnce
import com.nexgen.core.extensions.orFalse
import com.nexgen.core.extensions.shouldShowRequest
import com.nexgen.core.extensions.snack
import com.nexgen.domain.model.PhotoResultDomain
import com.nexgen.domain.model.exceptions.LivenessCameraXException
import com.nexgen.domain.repository.ResultLivenessRepository
import com.nexgen.livenesscamerax.R
import com.nexgen.livenesscamerax.databinding.LivenessCameraxFragmentBinding
import com.nexgen.livenesscamerax.di.LibraryModule.container
import com.nexgen.livenesscamerax.domain.model.CameraSettings
import com.nexgen.livenesscamerax.domain.model.toDomain
import com.nexgen.livenesscamerax.navigation.EXTRAS_LIVELINESS_CAMERA_SETTINGS
import com.nexgen.livenesscamerax.presentation.viewmodel.LivenessViewModel
import com.nexgen.livenesscamerax.presentation.viewmodel.LivenessViewModelFactory
import kotlinx.coroutines.FlowPreview
import timber.log.Timber

@FlowPreview
internal class CameraXFragment : Fragment(R.layout.liveness_camerax_fragment) {

    private var _binding: LivenessCameraxFragmentBinding? = null
    private val binding get() = _binding!!

    private val cameraSettings: CameraSettings by lazy {
        activity?.intent?.extras?.getParcelable(
            EXTRAS_LIVELINESS_CAMERA_SETTINGS
        ) ?: CameraSettings()
    }

    private val resultLivenessRepository: ResultLivenessRepository<PhotoResultDomain> by lazy {
        container.provideResultLivenessRepository()
    }

    private val livenessViewModel: LivenessViewModel by viewModels {
        LivenessViewModelFactory()
    }

    private val cameraXCallback: CameraXCallback by lazy {
        CameraXCallbackFactory.apply {
            onImageSavedAction = ::handlePictureSuccess
            onErrorAction = resultLivenessRepository::error
        }.create()
    }

    private val cameraX: CameraX by lazy {
        CameraXNavigation(this).provideCameraXModule(
            cameraSettings.toDomain(),
            cameraXCallback
        )
    }

    private val cameraManifest = Manifest.permission.CAMERA
    private val cameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            handleCameraPermission(granted.orFalse(), binding.clRoot)
        }

    private fun handleCameraPermission(granted: Boolean, parentView: View) {
        when {
            granted -> permissionIsGranted()
            requireActivity().shouldShowRequest(cameraManifest) -> {
                parentView.snack(R.string.liveness_camerax_message_permission_denied) {
                    resultLivenessRepository.error(LivenessCameraXException.PermissionDenied())
                    requireActivity().finish()
                }
            }
            else -> parentView.snack(R.string.liveness_camerax_message_permission_unknown) {
                resultLivenessRepository.error(LivenessCameraXException.PermissionUnknown())
                requireActivity().finish()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LivenessCameraxFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        livenessViewModel.setupSteps(cameraSettings.livenessStepList)
        cameraPermission.launch(cameraManifest)
    }

    override fun onStop() {
        super.onStop()

        if (!requireActivity().isFinishing) {
            resultLivenessRepository.error(LivenessCameraXException.ContextSwitchException())
            requireActivity().finish()
        }
    }

    private fun permissionIsGranted() {
        startCamera()
        startObservers()
    }

    private fun startObservers() {
        lifecycle.addObserver(cameraX.getLifecycleObserver())

        livenessViewModel.state.observe(viewLifecycleOwner) { state ->
            binding.tvStepText.text = state.messageLiveness
            if(state.isButtonEnabled){
                cameraX.takePicture(true)
            }
        }

        livenessViewModel.apply {
            observeFacesDetection(cameraX.observeFaceList())
            observeLuminosity(cameraX.observeLuminosity())
            hasBlinked.observeOnce(viewLifecycleOwner) { cameraX.takePicture() }
            hasSmiled.observeOnce(viewLifecycleOwner) { cameraX.takePicture() }
            hasGoodLuminosity.observeOnce(viewLifecycleOwner) { cameraX.takePicture() }
            hasHeadMovedLeft.observeOnce(viewLifecycleOwner) { cameraX.takePicture() }
            hasHeadMovedRight.observeOnce(viewLifecycleOwner) { cameraX.takePicture() }
            hasHeadMovedCenter.observeOnce(viewLifecycleOwner) { cameraX.takePicture() }
        }
    }

    private fun startCamera() {
        cameraX.startCamera(binding.viewFinder)

        binding.overlayView.apply {
            init()
            invalidate()
            isVisible = true
        }

        binding.tvStepText.isVisible = true
    }

    private fun handlePictureSuccess(photoResult: PhotoResultDomain, takenByUser: Boolean) {
        if (takenByUser) {
            val filesPath = cameraX.getAllPictures()
            resultLivenessRepository.success(photoResult, filesPath)
            requireActivity().finish()
        } else {
            Timber.d(photoResult.toString())
        }
    }
}
