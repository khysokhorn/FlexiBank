package com.nexgen.flexiBank.module.view.home

import android.os.Bundle
import android.os.PersistableBundle
import com.nexgen.flexiBank.databinding.ActivityHomeBinding
import com.nexgen.flexiBank.module.view.base.BaseMainActivity
import com.nexgen.flexiBank.module.view.home.viewModel.HomeViewModel
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository

class HomeActivity : BaseMainActivity<HomeViewModel, ActivityHomeBinding, AppRepository>() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }
    override fun getViewModel(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getActivityBinding(): ActivityHomeBinding =
        ActivityHomeBinding.inflate(layoutInflater)

    override fun getRepository(): AppRepository =
        AppRepository(remoteDataSource.buildApi(this, ApiInterface::class.java))
}