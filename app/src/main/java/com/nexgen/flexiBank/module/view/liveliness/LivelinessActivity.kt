package com.nexgen.flexiBank.module.view.liveliness

import android.os.Bundle
import android.os.PersistableBundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.LivelinessActivityBinding
import com.nexgen.flexiBank.module.view.base.BaseMainActivity
import com.nexgen.flexiBank.module.view.liveliness.viewModel.LivelinessViewModel
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository

class LivelinessActivity : BaseMainActivity<LivelinessViewModel, LivelinessActivityBinding, AppRepository>() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val navController = findNavController(R.id.nav_host_fragment_liveliness)
        appBarConfiguration = AppBarConfiguration(navController.graph)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_liveliness)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun getViewModel(): Class<LivelinessViewModel> = LivelinessViewModel::class.java
    override fun getActivityBinding(): LivelinessActivityBinding = LivelinessActivityBinding.inflate(layoutInflater)
    override fun getRepository(): AppRepository = AppRepository(remoteDataSource.buildApi(this, ApiInterface::class.java))

}