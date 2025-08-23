package com.nexgen.flexiBank.module.view.otp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.FragmentOtpChoiceBinding

class OtpChoiceFragment : Fragment() {

    private var _binding: FragmentOtpChoiceBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtpChoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = bundleOf("screen" to OtpChoiceFragment::class.java.toString())
        binding.email.setOnClickListener {
            val navigation = findNavController()
            navigation.navigate(R.id.action_OtpChoiceFragment_to_OtpVerifyFragment, bundle)
        }
        binding.phone.setOnClickListener {
            val navigation = findNavController()
            navigation.navigate(R.id.action_OtpChoiceFragment_to_OtpVerifyFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}