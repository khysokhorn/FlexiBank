package com.nexgen.flexiBank.module.view.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.ActivityDashboardBinding
import com.nexgen.flexiBank.databinding.ItemLanguageDropdownBinding
import com.nexgen.flexiBank.module.view.auth.LoginActivity
import com.nexgen.flexiBank.module.view.base.BaseMainActivity
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.viewmodel.RegisterViewModel

class DashboardActivity : BaseMainActivity<RegisterViewModel, ActivityDashboardBinding, AppRepository>() {
    val languages = listOf(
        LanguageItem(R.drawable.img_uk_flag, "English"),
        LanguageItem(R.drawable.img_khmer_flag, "Khmer"),
        LanguageItem(R.drawable.img_china_flag, "Chinese"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.progress)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val viewPager = ViewPagerAdapter(this);
        viewPager.addFragment(
            IntroContentFragment(
                title = resources.getString(R.string.start_saving_the_smart_way_with_flexibank), image = R.drawable.saving_img
            ),
            resources.getString(R.string.start_saving_the_smart_way_with_flexibank),
        )
        viewPager.addFragment(
            IntroContentFragment(
                title = resources.getString(R.string.time_saving), image = R.drawable.img_time_saving
            ),
            resources.getString(R.string.time_saving),
        )
        viewPager.addFragment(
            IntroContentFragment(
                title = resources.getString(R.string.feature), image = R.drawable.img_feature
            ),
            resources.getString(R.string.feature),
        )

        binding.intoContent.adapter = viewPager
        binding.intoContent.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.progress.customProgressBar.setCurrentStep(position)
            }
        })
        binding.progress.customProgressBar.setCurrentStep(binding.intoContent.currentItem)
        val adapter = LanguageAdapter(this, languages)
        binding.languageDropdown.setDropDownBackgroundResource(R.drawable.bg_dropdown_popup)
        val isEmpty = binding.languageDropdown.text.isEmpty()
        if (isEmpty) {
            binding.languageDropdown.setText(languages.first().languageName)
        }
        binding.languageDropdown.setAdapter(adapter)
        binding.languageDropdown.dropDownVerticalOffset = 16

        binding.languageDropdownLayout.setOnClickListener {
            if (!binding.languageDropdown.isPopupShowing) binding.languageDropdown.showDropDown()
            else binding.languageDropdown.dismissDropDown()
        }

        binding.languageDropdown.setOnItemClickListener { _, _, position, _ ->
            binding.languageDropdown.dismissDropDown()
        }

        onLogin()
    }

    override fun getViewModel() = RegisterViewModel::class.java

    override fun getActivityBinding(): ActivityDashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)

    override fun getRepository() = AppRepository(remoteDataSource.buildApi(this, ApiInterface::class.java))
    private fun onLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        binding.btnLogin.setOnClickListener { startActivity(intent) }
    }
}

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val fragments = mutableListOf<Fragment>()
    private val fragmentTitles = mutableListOf<String>()

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        fragmentTitles.add(title)
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}


data class LanguageItem(val flagResId: Int, val languageName: String) {
    override fun toString(): String {
        return languageName
    }
}

class LanguageAdapter(context: Context, private val items: List<LanguageItem>) : ArrayAdapter<LanguageItem>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_language_dropdown, parent, false)
        var binding = ItemLanguageDropdownBinding.bind(view)
        val item = items[position]
        binding.tvLanguage.apply {
            text = item.languageName
            setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(context, item.flagResId), null, null, null
            )
        }
        return binding.root
    }
}

