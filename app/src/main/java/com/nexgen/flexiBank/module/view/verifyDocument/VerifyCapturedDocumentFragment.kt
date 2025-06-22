package com.nexgen.flexiBank.module.view.verifyDocument

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.FragmentVerifyCapturedDocumentBinding
import com.nexgen.flexiBank.module.view.base.BaseFragment
import com.nexgen.flexiBank.module.view.verifyDocument.viewModel.VerifyDocumentViewModel
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository

class VerifyCapturedDocumentFragment : BaseFragment<VerifyDocumentViewModel, FragmentVerifyCapturedDocumentBinding, AppRepository>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imgUri = arguments?.getString("imgUri")
        binding.imgDocPreview.setImageURI(imgUri?.toUri())
        val navController = findNavController()
        val toolBar = binding.appBar.topAppBar
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolBar.setNavigationOnClickListener {
            navController.popBackStack()
        }
        toolBar.setupWithNavController(navController, appBarConfiguration)
        toolBar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.img_arrow_back)

        binding.btnSubmit.setOnClickListener {
            onSubmit()
        }
        binding.btnRetake.setOnClickListener {
            navController.popBackStack()
        }

    }

    fun onSubmit() {

    }

    override fun getViewModel(): Class<VerifyDocumentViewModel> = VerifyDocumentViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentVerifyCapturedDocumentBinding = FragmentVerifyCapturedDocumentBinding.inflate(layoutInflater)

    override fun getRepository(): AppRepository = AppRepository(remoteDataSource.buildApi(requireActivity(), ApiInterface::class.java))

}