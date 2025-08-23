package com.nexgen.flexiBank.module.view.auth.fragment

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.FragmentLoginBinding
import com.nexgen.flexiBank.module.view.auth.model.Country

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var selectedCountry = Country("Cambodia", "+855", "KH", R.drawable.img_khmer_flag)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val htmlText =
            "By pressing <b>\"Sign Up\"</b> you accept our <font color='blue'>privacy policy</font> and <font color='blue'>terms & Conditions</font>. Your information will be securely " + "encrypted."
        binding.txtTermCondition.text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
        binding.btnSignUp.setOnClickListener {
            val navigation = findNavController()
            navigation.navigate(R.id.action_LoginFragment_to_OtpChoiceFragment)
        }
        setCountryCode(selectedCountry)
        binding.layoutCountry.setOnClickListener {
            showCountryBottomSheet()
        }
    }

    private fun showCountryBottomSheet() {
        val bottomSheet = CountryBottomSheetFragment.newInstance(selectedCountry) { country ->
            selectedCountry = country
            setCountryCode(selectedCountry)
        }
        bottomSheet.show(parentFragmentManager, "CountryBottomSheet")
    }

    fun setCountryCode(country: Country) {
        binding.imgFlag.setImageResource(country.flagResource)
        binding.txtCountryCode.text = country.dialCode
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}