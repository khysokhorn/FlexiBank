package com.nexgen.flexiBank.module.view.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.FragmentOtpVerifyBinding
import com.nexgen.flexiBank.module.view.base.BaseFragment
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.viewmodel.RegisterViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OtpVerifyFragment : BaseFragment<RegisterViewModel, FragmentOtpVerifyBinding, AppRepository>() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.otp.setOnCompleteListener({
            val navigator = findNavController()
            navigator.navigate(R.id.action_OtpVerifyFragment_to_IdentityVerifyFragment)
        })
    }

    override fun getViewModel(): Class<RegisterViewModel> = RegisterViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentOtpVerifyBinding.inflate(inflater, container, false)

    override fun getRepository(): AppRepository = AppRepository(remoteDataSource.buildApi(requireActivity(), ApiInterface::class.java))

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OtpVerifyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = OtpVerifyFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}