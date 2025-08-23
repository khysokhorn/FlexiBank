package com.nexgen.flexiBank.module.view.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
//        binding.email.setOnClickListener {
//            val navigaton = findNavController()
//            navigaton.navigate(R.id.action_OtpChoiceFragment_to_OtpVerifyFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}