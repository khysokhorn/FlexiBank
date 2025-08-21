package com.nexgen.flexiBank.module.view.bakongQRCode.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexgen.flexiBank.repository.BaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class KhQrInputAmountViewModel(private val repository: BaseRepository) : ViewModel() {
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

    fun submitPayment(amount: String, accountId: String, remark: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                // Convert amount to proper format
                val amountValue = amount.toDoubleOrNull() ?: run {
                    _error.value = "Invalid amount"
                    return@launch
                }

                // Call your payment API here
                // Example:
                // val result = repository.submitKhQrPayment(amountValue, accountId, remark)
                
                // For now, simulate API call with delay
                kotlinx.coroutines.delay(1000)
                
                _paymentSuccess.value = true
                
            } catch (e: Exception) {
                _error.value = e.message ?: "Payment failed"
            } finally {
                _isLoading.value = false
            }
        }
    }
}