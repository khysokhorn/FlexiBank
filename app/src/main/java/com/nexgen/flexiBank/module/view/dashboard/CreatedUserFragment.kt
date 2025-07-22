package com.nexgen.flexiBank.module.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexgen.flexiBank.databinding.FragmentCreatedUserBinding
import com.nexgen.flexiBank.module.view.base.BaseFragment
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.viewmodel.PasscodeViewModel

class CreatedUserFragment :
    BaseFragment<PasscodeViewModel, FragmentCreatedUserBinding, AppRepository>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getViewModel(): Class<PasscodeViewModel> = PasscodeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreatedUserBinding =
        FragmentCreatedUserBinding.inflate(layoutInflater, container, false)

    override fun getRepository(): AppRepository =
        AppRepository(remoteDataSource.buildApi(requireActivity(), ApiInterface::class.java))
}