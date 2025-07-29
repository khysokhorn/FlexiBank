package com.nexgen.flexiBank.module.view.liveliness

import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nexgen.flexiBank.databinding.FragmentLivelinessCaptureBinding
import com.nexgen.flexiBank.module.view.base.BaseFragment
import com.nexgen.flexiBank.module.view.verifyDocument.adapter.ImageListAdapter
import com.nexgen.flexiBank.module.view.verifyDocument.viewModel.VerifyDocumentViewModel
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.livenesscamerax.domain.model.CameraSettings
import com.nexgen.livenesscamerax.domain.model.StepLiveness
import com.nexgen.livenesscamerax.domain.model.StorageType
import com.nexgen.livenesscamerax.navigation.LivelinessEntryPoint
import timber.log.Timber

class LivelinessCaptureFragment : BaseFragment<VerifyDocumentViewModel, FragmentLivelinessCaptureBinding, AppRepository>() {
    private val livelinessEntryPoint = LivelinessEntryPoint
    private val mutableStepList = arrayListOf<StepLiveness>()
    private val imageListAdapter = ImageListAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mutableStepList.add(StepLiveness.STEP_HEAD_FRONTAL)
        mutableStepList.add(StepLiveness.STEP_SMILE)
        mutableStepList.add(StepLiveness.STEP_HEAD_LEFT)
        mutableStepList.add(StepLiveness.STEP_HEAD_RIGHT)
        mutableStepList.add(StepLiveness.STEP_HEAD_FRONTAL)
        livelinessEntryPoint.startLiveliness(
            cameraSettings = CameraSettings(
                livenessStepList = mutableStepList,
                storageType = StorageType.INTERNAL
            ),
            context = requireContext(),
        ) { livelinessCameraXResult ->
            if (livelinessCameraXResult.error == null) {
                val listOfImages = arrayListOf<ByteArray>().apply {
                    livelinessCameraXResult.createdBySteps?.let { photoResultList ->
                        this.addAll(photoResultList.map { Base64.decode(it.fileBase64, Base64.NO_WRAP) })
                    }
                }
                imageListAdapter.imageList = listOfImages
                Toast.makeText(requireContext(), "Move to next screen", Toast.LENGTH_LONG).show()
            } else {
                livelinessCameraXResult.error?.let {
                    Timber.tag("aaa").e(it.toString())
                }
            }
        }
    }

    companion object {

    }

    override fun getViewModel(): Class<VerifyDocumentViewModel> = VerifyDocumentViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentLivelinessCaptureBinding = FragmentLivelinessCaptureBinding.inflate(layoutInflater)

    override fun getRepository(): AppRepository = AppRepository(remoteDataSource.buildApi(requireActivity(), ApiInterface::class.java))

}

