package com.nexgen.flexiBank.module.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexgen.flexiBank.common.AppPreferenceManager
import com.nexgen.flexiBank.databinding.FragmentGettingStartedBinding
import com.nexgen.flexiBank.module.view.base.BaseFragment
import com.nexgen.flexiBank.module.view.home.HomeActivity
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.viewmodel.PasscodeViewModel

class GetStartedFragment :
    BaseFragment<PasscodeViewModel, FragmentGettingStartedBinding, AppRepository>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGetStarted.setOnClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            AppPreferenceManager.setAuth(true)
            startActivity(intent)
        }
    }

    override fun getViewModel(): Class<PasscodeViewModel> = PasscodeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentGettingStartedBinding =
        FragmentGettingStartedBinding.inflate(layoutInflater, container, false)

    override fun getRepository(): AppRepository =
        AppRepository(remoteDataSource.buildApi(requireActivity(), ApiInterface::class.java))
}
