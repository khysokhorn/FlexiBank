package com.nexgen.flexiBank.module.view.keypass

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.ActivityKeypassBinding

class KeypassActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityKeypassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKeypassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the navigation controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }
}