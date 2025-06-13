package com.nexgen.flexiBank.module.view.dashboard

import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.ActivityDashboardBinding
import com.nexgen.flexiBank.module.view.base.BaseMainActivity
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.viewmodel.RegisterViewModel

class DashboardActivity : BaseMainActivity<RegisterViewModel, ActivityDashboardBinding, AppRepository>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val savingTitle = resources.getString(R.string.start_saving_the_smart_way_with_flexibank)

        val viewPager = ViewPagerAdapter(this);
        viewPager.addFragment(
            IntroContentFragment(
                title = savingTitle,
                image = R.drawable.saving_img
            ), savingTitle
        )
        viewPager.addFragment(
            IntroContentFragment(
                title = savingTitle,
                image = R.drawable.saving_img
            ), savingTitle
        )
        viewPager.addFragment(
            IntroContentFragment(
                title = savingTitle,
                image = R.drawable.saving_img
            ), savingTitle
        )
        binding.intoContent.adapter = viewPager
    }

    override fun getViewModel() = RegisterViewModel::class.java

    override fun getActivityBinding(): ActivityDashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)

    override fun getRepository() = AppRepository(remoteDataSource.buildApi(this, ApiInterface::class.java))
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