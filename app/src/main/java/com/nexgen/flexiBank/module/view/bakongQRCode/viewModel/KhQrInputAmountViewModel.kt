package com.nexgen.flexiBank.module.view.bakongQRCode.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.model.BaseModel
import com.nexgen.flexiBank.module.view.bakongQRCode.model.Account
import com.nexgen.flexiBank.module.view.bakongQRCode.model.TodoModelItem
import com.nexgen.flexiBank.network.Resource
import com.nexgen.flexiBank.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class KhQrInputAmountViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {
    val sampleAccounts = listOf(
        Account(
            id = "1",
            name = "Saving Account",
            number = "001 751 517",
            balance = "$ 72,392.10",
            isDefault = true,
            hasVisa = true
        ),
        Account(
            id = "2",
            name = "Future Plan",
            number = "001 222 333",
            balance = "$ 2,392.68",
            isDefault = false,
            hasVisa = false,
            iconRes = R.drawable.ic_bank_locker
        )
    )
    private var _transferQRCode: MutableStateFlow<Resource<BaseModel<TodoModelItem>>>? = null
    val transferQRCode get() = _transferQRCode

    private val _errorShown = MutableStateFlow(false)
    val errorShown = _errorShown.asStateFlow()

    private val _amount = MutableStateFlow("")
    val amount = _amount

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error = _error

    private val _paymentSuccess = MutableStateFlow(false)
    val paymentSuccess = _paymentSuccess

    fun addDigit(digit: String) {
        _amount.value += digit
    }

    fun clearPin() {
        _amount.value = ""
    }

    fun deleteLastDigit() {
        if (_amount.value.isNotEmpty()) {
            _amount.value = _amount.value.dropLast(1)
        }
    }

    private val _requireVerification = MutableStateFlow(false)
    val requireVerification = _requireVerification.asStateFlow()

    fun submitPayment(amount: String, accountId: String, remark: String) {
        viewModelScope.launch {
            try {
                val amountValue = amount.toDoubleOrNull() ?: run {
                    _error.value = "Invalid amount"
                    return@launch
                }
                resetErrorState()
                _transferQRCode = MutableStateFlow(Resource.Loading)
                val result = repository.submitKhQrPayment(amountValue, accountId, remark)
                _transferQRCode?.value = result
            } catch (e: Exception) {
                _error.value = e.message ?: "Payment failed"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun continueAfterVerification() {
        _paymentSuccess.value = true
    }

    fun resetVerification() {
        _requireVerification.value = false
    }

    fun markErrorAsShown() {
        _errorShown.value = true
    }

    fun resetErrorState() {
        _errorShown.value = false
        _error.value = null
    }
}