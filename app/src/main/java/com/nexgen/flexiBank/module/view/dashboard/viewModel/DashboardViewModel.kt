package com.nexgen.flexiBank.module.view.dashboard.viewModel

import androidx.lifecycle.ViewModel
import com.nexgen.flexiBank.repository.AppRepository

class DashboardViewModel(private val repository: AppRepository) : ViewModel() {
    private var selectedId: Int = 0
    fun documentTypeOnClick(id: Int) {
        selectedId = id
    }
    fun getSelectedId(): Int = selectedId


}