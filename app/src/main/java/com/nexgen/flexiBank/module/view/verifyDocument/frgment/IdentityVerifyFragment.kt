package com.nexgen.flexiBank.module.view.verifyDocument.frgment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.FragmentIdentityVerifyBinding
import com.nexgen.flexiBank.module.view.base.BaseFragment
import com.nexgen.flexiBank.module.view.verifyDocument.VerifyDocumentActivity
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.viewmodel.RegisterViewModel

class IdentityVerifyFragment :
    BaseFragment<RegisterViewModel, FragmentIdentityVerifyBinding, AppRepository>() {

    override fun getViewModel(): Class<RegisterViewModel> = RegisterViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIdentityVerifyBinding =
        FragmentIdentityVerifyBinding.inflate(layoutInflater, container, false)

    override fun getRepository(): AppRepository =
        AppRepository(remoteDataSource.buildApi(requireActivity(), ApiInterface::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnVerify.setOnClickListener {
            val intent = Intent(requireContext(), VerifyDocumentActivity::class.java)
            startActivity(intent)
        }

        binding.nid.setOnClickListener {
            updateDocumentTypeSelection(binding.nid.id, isNidSelected = true)
        }
        binding.passport.setOnClickListener {
            updateDocumentTypeSelection(binding.passport.id, isNidSelected = false)
        }
    }

    private fun updateDocumentTypeSelection(selectedId: Int, isNidSelected: Boolean) {
        viewModel.documentTypeOnClick(selectedId)

        binding.nid.setBackgroundResource(if (isNidSelected) R.drawable.rounded_border else R.drawable.rounded_background)
        binding.imgNidSelected.setImageResource(if (isNidSelected) R.drawable.circle_active_drawable else R.drawable.circle_drawable)

        binding.passport.setBackgroundResource(if (!isNidSelected) R.drawable.rounded_border else R.drawable.rounded_background)
        binding.imgPassportSelected.setImageResource(if (!isNidSelected) R.drawable.circle_active_drawable else R.drawable.circle_drawable)
    }


}