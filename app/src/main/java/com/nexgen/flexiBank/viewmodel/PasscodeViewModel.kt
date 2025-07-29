package com.nexgen.flexiBank.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexgen.flexiBank.repository.BaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PasscodeViewModel(private val repository: BaseRepository) : ViewModel() {

    private val _pinCode = MutableStateFlow("")
    val pinCode = _pinCode.asStateFlow()

    private val _confirmPinCode = MutableStateFlow("")
    val confirmPinCode = _confirmPinCode.asStateFlow()

    private val _isPinValid = MutableStateFlow(false)
    val isPinValid = _isPinValid.asStateFlow()

    private val _isConfirmPin = MutableStateFlow(false)
    val isConfirmPin = _isConfirmPin.asStateFlow()

    private val _pinMatchError = MutableStateFlow(false)
    val pinMatchError = _pinMatchError.asStateFlow()

    private val _isNavigateToNext = MutableStateFlow(false)
    val isNavigateToNext = _isNavigateToNext.asStateFlow()

    // Verification completed flow
    private val _verificationCompleted = MutableStateFlow(false)
    val verificationCompleted = _verificationCompleted.asStateFlow()

    // Stored pin used to pass between fragments
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

    fun resetNavigation() {
        _isNavigateToNext.value = false
    }

    fun resetToCreatePin() {
        _isConfirmPin.value = false
        _pinCode.value = ""
        _confirmPinCode.value = ""
        _pinMatchError.value = false
        // Don't reset _storedPin here as we need it for confirmation
    }

    fun setConfirmMode(isConfirm: Boolean) {
        _isConfirmPin.value = isConfirm
    }

    // Store the PIN to be used between fragments
    fun storePin(pin: String) {
        _storedPin.value = pin
    }

    // Get the stored PIN
    fun getStoredPin(): String {
        return _storedPin.value
    }

    fun getMaxPinLength(): Int {
        return maxPinLength
    }

    fun onVerificationComplete() {
        _verificationCompleted.value = true
    }

    fun resetVerificationStatus() {
        _verificationCompleted.value = false
    }
}