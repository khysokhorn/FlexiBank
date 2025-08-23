package com.nexgen.flexiBank.module.view.otp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.common.AppPreferenceManager
import com.nexgen.flexiBank.databinding.FragmentOtpVerifyBinding
import com.nexgen.flexiBank.module.view.base.BaseFragment
import com.nexgen.flexiBank.module.view.home.HomeActivity
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.viewmodel.RegisterViewModel

class OtpVerifyFragment :
    BaseFragment<RegisterViewModel, FragmentOtpVerifyBinding, AppRepository>() {
    var screenType: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screenType = arguments?.getString("screen")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.otp.setOnCompleteListener {
            if (screenType == OtpChoiceFragment::class.java.toString()) {
                val navigation = findNavController()
                navigation.navigate(R.id.action_OtpVerifyFragment_to_IdentityVerifyFragment)
                return@setOnCompleteListener
            }

            AppPreferenceManager.setAuth(true)
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getViewModel(): Class<RegisterViewModel> = RegisterViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentOtpVerifyBinding.inflate(inflater, container, false)

    override fun getRepository(): AppRepository =
        AppRepository(remoteDataSource.buildApi(requireActivity(), ApiInterface::class.java))


}