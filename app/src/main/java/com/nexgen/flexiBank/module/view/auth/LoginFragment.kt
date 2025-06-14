package com.nexgen.flexiBank.module.view.auth

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.FragmentLoginBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val htmlText =
            "By pressing <b>\"Sign Up\"</b> you accept our <font color='blue'>privacy policy</font> and <font color='blue'>terms & Conditions</font>. Your information will be securely " + "encrypted.";
        binding.txtTermCondition.text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY);
        binding.btnSignUp.setOnClickListener {
            val navigation = findNavController()
            navigation.navigate(R.id.action_LoginFragment_to_OtpChoiceFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}