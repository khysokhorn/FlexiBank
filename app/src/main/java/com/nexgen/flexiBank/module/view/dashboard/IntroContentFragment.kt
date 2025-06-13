package com.nexgen.flexiBank.module.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.FragmentIntroContentBinding
import com.nexgen.flexiBank.model.Language
import com.nexgen.flexiBank.module.view.auth.LoginActivity
import com.nexgen.flexiBank.module.view.base.BaseFragment
import com.nexgen.flexiBank.module.view.utils.LanguageAdapter
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.viewmodel.RegisterViewModel

class IntroContentFragment(
    private val title: String,
    private val image: Int,
) : BaseFragment<RegisterViewModel, FragmentIntroContentBinding, AppRepository>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = title
        binding.img.setImageResource(image)
        val languages = listOf(
            Language("English", R.drawable.united_kingdom_flag),
            Language("Khmer", R.drawable.united_kingdom_flag),
            Language("Chinese", R.drawable.united_kingdom_flag)
        )

        val adapter = LanguageAdapter(requireContext(), languages)
        binding.languageSpinner.adapter = adapter
        onLogin()
    }

    override fun getViewModel(): Class<RegisterViewModel> = RegisterViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentIntroContentBinding.inflate(inflater, container, false)

    override fun getRepository(): AppRepository = AppRepository(remoteDataSource.buildApi(requireActivity(), ApiInterface::class.java))

    private fun onLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        binding.btnLogin.setOnClickListener {
            startActivity(intent)
        }
    }

}