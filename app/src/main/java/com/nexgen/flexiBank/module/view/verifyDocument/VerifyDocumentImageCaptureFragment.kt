package com.nexgen.flexiBank.module.view.verifyDocument

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nexgen.flexiBank.databinding.FragmentVerifyDocumentBinding
import com.nexgen.flexiBank.module.view.base.BaseFragment
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository

class VerifyDocumentImageCaptureFragment() : BaseFragment<VerifyDocumentViewModel, FragmentVerifyDocumentBinding, AppRepository>() {
    override fun getViewModel(): Class<VerifyDocumentViewModel> = VerifyDocumentViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentVerifyDocumentBinding = FragmentVerifyDocumentBinding.inflate(inflater, container, false)

    override fun getRepository(): AppRepository = AppRepository(remoteDataSource.buildApi(requireActivity(), ApiInterface::class.java))
}