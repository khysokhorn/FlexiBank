package com.nexgen.flexiBank.module.view.verifyDocument

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.ActivityVerifyDocumentBinding

class VerifyDocumentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerifyDocumentBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVerifyDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_verify_document)
        appBarConfiguration = AppBarConfiguration(navController.graph)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_verify_document)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}