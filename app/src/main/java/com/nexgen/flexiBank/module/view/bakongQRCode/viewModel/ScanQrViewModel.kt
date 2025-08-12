package com.nexgen.flexiBank.module.view.bakongQRCode.viewModel

import androidx.lifecycle.ViewModel
import com.nexgen.flexiBank.repository.BaseRepository

class ScanQrViewModel(private val repository: BaseRepository) : ViewModel() {
    val STORAGE_PERMISSION_CODE = 1000;

}