package com.nexgen.flexiBank.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nexgen.flexiBank.module.view.bakongQRCode.viewModel.KhQrInputAmountViewModel
import com.nexgen.flexiBank.module.view.bakongQRCode.viewModel.ScanQrViewModel
import com.nexgen.flexiBank.module.view.home.viewModel.HomeViewModel
import com.nexgen.flexiBank.module.view.liveliness.viewModel.LivelinessViewModel
import com.nexgen.flexiBank.module.view.verifyDocument.viewModel.VerifyDocumentViewModel
import com.nexgen.flexiBank.repository.AppRepository
import com.nexgen.flexiBank.repository.BaseRepository

class ViewModelFactory(private val repository: BaseRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(
                repository as AppRepository
            ) as T

            modelClass.isAssignableFrom(VerifyDocumentViewModel::class.java) -> VerifyDocumentViewModel(
                repository as AppRepository
            ) as T

            modelClass.isAssignableFrom(LivelinessViewModel::class.java) -> LivelinessViewModel(
                repository as AppRepository
            ) as T

            modelClass.isAssignableFrom(PasscodeViewModel::class.java) -> PasscodeViewModel(
                repository as AppRepository
            ) as T

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(
                repository as AppRepository
            ) as T

            modelClass.isAssignableFrom(ScanQrViewModel::class.java) -> ScanQrViewModel(
                repository as AppRepository
            ) as T

            modelClass.isAssignableFrom(KhQrInputAmountViewModel::class.java) -> KhQrInputAmountViewModel(
                repository as AppRepository
            ) as T

            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }
}