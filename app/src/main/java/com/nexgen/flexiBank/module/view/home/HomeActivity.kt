package com.nexgen.flexiBank.module.view.home

import android.os.Bundle
import com.bumptech.glide.Glide
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.ActivityHomeBinding
import com.nexgen.flexiBank.module.view.base.BaseMainActivity
import com.nexgen.flexiBank.module.view.home.adapter.QuickShareAdapter
import com.nexgen.flexiBank.module.view.home.adapter.UpComingPaymentAdapter
import com.nexgen.flexiBank.module.view.home.viewModel.HomeViewModel
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository

class HomeActivity : BaseMainActivity<HomeViewModel, ActivityHomeBinding, AppRepository>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Glide.with(this)
            .load("https://img.freepik.com/premium-psd/stylish-young-man-3d-icon-avatar-people_431668-1607.jpg")
            .circleCrop()
            .into(binding.appBar.imgProfile)

        setupQuickShareRecyclerView()
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomBar.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Handle home navigation
                    true
                }

                R.id.navigation_card -> {
                    // Handle card navigation
                    true
                }

                R.id.navigation_scan -> {
                    // Handle scan navigation
                    true
                }

                R.id.navigation_favorite -> {
                    // Handle favorite navigation
                    true
                }

                R.id.navigation_hub -> {
                    // Handle hub navigation
                    true
                }

                else -> false
            }
        }

    }

    private fun setupQuickShareRecyclerView() {
        val quickShareContacts = listOf(
            QuickShareAdapter.QuickShareContact(
                id = "1",
                name = "Shusin Jeo",
                imageUrl = "https://img.freepik.com/premium-psd/stylish-young-man-3d-icon-avatar-people_431668-1607.jpg"
            ),
            QuickShareAdapter.QuickShareContact(
                id = "2",
                name = "Alyssa Kim",
                isActive = true,
                imageUrl = "https://img.freepik.com/free-psd/3d-illustration-person-with-glasses_23-2149436191.jpg"
            ),
            QuickShareAdapter.QuickShareContact(
                id = "3",
                name = "Marcus Chen",
            ),
            QuickShareAdapter.QuickShareContact(
                id = "4",
                name = "Sarah Lee",
            ),
            QuickShareAdapter.QuickShareContact(
                id = "5",
                name = "David Wong",
            )
        )

        val quickShareAdapter = QuickShareAdapter(quickShareContacts) { contact ->
        }

        binding.rvQuickShare.adapter = quickShareAdapter
        val upcomingPayment = listOf(
            UpComingPaymentAdapter.UpComingPayment(
                name = "Canvas Pro",
                amount = 15.99,
                currency = "USD",
                createdDate = "2025-06-12",
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT11vbcvZowjk1B2MCrrSwr6cEhnI6brIdINElziegsLYnVMcfBdlVln-n9-bIQ5NmZrXM&usqp=CAU"
            ),
            UpComingPaymentAdapter.UpComingPayment(
                name = "Figma Premium",
                amount = 12.99,
                currency = "USD",
                createdDate = "2025-06-15",
                imageUrl = "https://cdn.sanity.io/images/599r6htc/regionalized/5094051dac77593d0f0978bdcbabaf79e5bb855c-1080x1080.png?w=540&h=540&q=75&fit=max&auto=format"
            ),
            UpComingPaymentAdapter.UpComingPayment(
                name = "Youtube Premium",
                amount = 11.99,
                currency = "USD",
                createdDate = "2025-06-18",
                imageUrl = "https://static.vecteezy.com/system/resources/thumbnails/023/986/480/small_2x/youtube-logo-youtube-logo-transparent-youtube-icon-transparent-free-free-png.png"
            )
        )
        val paymentAdapter = UpComingPaymentAdapter(upcomingPayment)
        {

        }

        binding.rvPayment.adapter = paymentAdapter
    }

    override fun getViewModel(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getActivityBinding(): ActivityHomeBinding =
        ActivityHomeBinding.inflate(layoutInflater)

    override fun getRepository(): AppRepository =
        AppRepository(remoteDataSource.buildApi(this, ApiInterface::class.java))
}