package com.nexgen.flexiBank.module.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nexgen.flexiBank.network.RemoteDataSource
import com.nexgen.flexiBank.repository.BaseRepository
import com.nexgen.flexiBank.utils.theme.FlexiBankTheme
import com.nexgen.flexiBank.viewmodel.ViewModelFactory

abstract class BaseComposeFragment<VM : ViewModel, R : BaseRepository> : Fragment() {

    protected lateinit var viewModel: VM
    protected val remoteDataSource = RemoteDataSource()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = ViewModelFactory(getRepository())
        viewModel = ViewModelProvider(this, factory)[getViewModel()]

        return ComposeView(requireContext()).apply {
            setContent {
                FlexiBankTheme {
                    ComposeContent()
                }
            }
        }
    }

    @Composable
    abstract fun ComposeContent()

    abstract fun getViewModel(): Class<VM>

    abstract fun getRepository(): R
}