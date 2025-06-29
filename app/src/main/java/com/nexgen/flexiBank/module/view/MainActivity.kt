package com.nexgen.flexiBank.module.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.ActivityMainBinding
import com.nexgen.flexiBank.module.view.base.BaseMainActivity
import com.nexgen.flexiBank.module.view.dashboard.DashboardActivity
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.viewmodel.RegisterViewModel

class MainActivity : BaseMainActivity<RegisterViewModel, ActivityMainBinding, AppRepository>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        startFlexiBankAnimation()
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, DashboardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.fade_in, R.anim.fade_out)
            } else {
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
        }, 1000)
    }

    private fun startFlexiBankAnimation() {
        // 1. Text Fade Out Animation
        val fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.text_fade_out)
        binding.flexiBankText.startAnimation(fadeOutAnimation)
        fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                binding.flexiBankText.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    override fun getViewModel() = RegisterViewModel::class.java

    override fun getActivityBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun getRepository() = AppRepository(remoteDataSource.buildApi(this, ApiInterface::class.java))

}