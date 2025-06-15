package com.nexgen.flexiBank.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nexgen.flexiBank.module.view.verifyDocument.VerifyDocumentViewModel
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
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }
}