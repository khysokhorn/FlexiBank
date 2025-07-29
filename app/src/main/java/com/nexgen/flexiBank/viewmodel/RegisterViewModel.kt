package com.nexgen.flexiBank.viewmodel

import androidx.lifecycle.ViewModel
import com.nexgen.flexiBank.repository.AppRepository

class RegisterViewModel(private val repository: AppRepository) : ViewModel() {
    private var selectedId: Int = 0
    fun documentTypeOnClick(id: Int) {
        selectedId = id
    }
    fun getSelectedId(): Int = selectedId


}