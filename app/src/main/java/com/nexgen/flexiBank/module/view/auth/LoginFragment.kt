package com.nexgen.flexiBank.module.view.auth

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}