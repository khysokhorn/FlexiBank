package com.nexgen.flexiBank.module.view.bakongQRCode.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexgen.flexiBank.repository.BaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class KhQrInputAmountViewModel(private val repository: BaseRepository) : ViewModel() {
    private val _pinCode = MutableStateFlow("")

    private val _confirmPinCode = MutableStateFlow("")

    private val _isPinValid = MutableStateFlow(false)

    private val _isConfirmPin = MutableStateFlow(false)

    private val _pinMatchError = MutableStateFlow(false)

    private val _isNavigateToNext = MutableStateFlow(false)

    private val _storedPin = MutableStateFlow("")

    private val maxPinLength = 4

    fun addDigit(digit: String) {
        if (_isConfirmPin.value) {
            // In confirm PIN mode
            val currentPin = _confirmPinCode.value
            if (currentPin.length < maxPinLength) {
                _confirmPinCode.value = currentPin + digit
                checkConfirmPinCompletion()
            }
        } else {
            // In create PIN mode
            val currentPin = _pinCode.value
            if (currentPin.length < maxPinLength) {
                _pinCode.value = currentPin + digit
                checkPinCompletion()
            }
        }
    }

    fun clearPin() {
        if (_isConfirmPin.value) {
            _confirmPinCode.value = ""
        } else {
            _pinCode.value = ""
        }
    }

    fun deleteLastDigit() {
        if (_isConfirmPin.value) {
            val currentPin = _confirmPinCode.value
            if (currentPin.isNotEmpty()) {
                _confirmPinCode.value = currentPin.dropLast(1)
            }
        } else {
            val currentPin = _pinCode.value
            if (currentPin.isNotEmpty()) {
                _pinCode.value = currentPin.dropLast(1)
            }
        }
    }

    private fun checkPinCompletion() {
        val currentPin = _pinCode.value
        if (currentPin.length == maxPinLength) {
            validatePin(currentPin)
        }
    }

    private fun checkConfirmPinCompletion() {
        val confirmPin = _confirmPinCode.value
        if (confirmPin.length == maxPinLength) {
            validateConfirmPin(confirmPin)
        }
    }

    private fun validatePin(pin: String) {
        viewModelScope.launch {
            _isPinValid.value = true
            _isConfirmPin.value = true
            // Store the PIN for confirmation
            _storedPin.value = pin
            // Reset any previous pin match error
            _pinMatchError.value = false
            // Clear current pin but keep stored pin
            _pinCode.value = ""
        }
    }

    private fun validateConfirmPin(confirmPin: String) {
        viewModelScope.launch {
            if (confirmPin == _storedPin.value) {
                // PINs match, proceed to next screen
                _isPinValid.value = true
                _pinMatchError.value = false
                _isNavigateToNext.value = true
            } else {
                // PINs don't match, show error
                _confirmPinCode.value = ""
                _pinMatchError.value = true
            }
        }
    }
}