package com.nexgen.flexiBank.module.view.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.FragmentIntroContentBinding
import com.nexgen.flexiBank.module.view.auth.LoginActivity
import com.nexgen.flexiBank.module.view.base.BaseFragment
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
        onLogin()
    
        val languages = listOf(
            LanguageItem(R.drawable.united_kingdom_flag, "English"),
            LanguageItem(R.drawable.united_kingdom_flag, "Khmer"),
            LanguageItem(R.drawable.united_kingdom_flag, "Chinese")
        )

        val adapter = LanguageAdapter(requireContext(), languages)
        binding.languageDropdown.setAdapter(adapter)
        binding.languageDropdown.setOnItemClickListener { _, _, position, _ ->
            // Handle language selection if needed
        }
    }

    override fun getViewModel(): Class<RegisterViewModel> = RegisterViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentIntroContentBinding.inflate(inflater, container, false)

    override fun getRepository(): AppRepository =
        AppRepository(remoteDataSource.buildApi(requireActivity(), ApiInterface::class.java))

    private fun onLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        binding.btnLogin.setOnClickListener { startActivity(intent) }
    }
}

data class LanguageItem(val flagResId: Int, val languageName: String)

class LanguageAdapter(context: Context, private val items: List<LanguageItem>) :
    ArrayAdapter<LanguageItem>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =
            convertView
                ?: LayoutInflater.from(context)
                    .inflate(R.layout.item_language_dropdown, parent, false)
        val item = items[position]
        view.findViewById<ImageView>(R.id.imgFlag).setImageResource(item.flagResId)
        view.findViewById<TextView>(R.id.tvLanguage).text = item.languageName
        return view
    }
}
