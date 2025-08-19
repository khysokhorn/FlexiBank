package com.nexgen.flexiBank.module.view.bakongQRCode.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexgen.flexiBank.module.view.bakongQRCode.model.AccountModel
import com.nexgen.flexiBank.repository.BaseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class KhQrInputAmountViewModel(private val repository: BaseRepository) : ViewModel() {
    private val _amount = MutableStateFlow("0")
    val amount = _amount.asStateFlow()

    private val _selectedAccount = mutableStateOf<AccountModel>(
        AccountModel(
            accountName = "Saving Account",
            accountNumber = "001 751 517",
            currency = "USD", 
            balance = 72392.10,
            isDefault = true,
            hasVisa = true
        )
    )
    val selectedAccount: State<AccountModel> = _selectedAccount

    private val _isAccountSelectionVisible = mutableStateOf(false)
    val isAccountSelectionVisible: State<Boolean> = _isAccountSelectionVisible

    private val _isRemarkDialogVisible = mutableStateOf(false)
    val isRemarkDialogVisible: State<Boolean> = _isRemarkDialogVisible

    private val _remark = MutableStateFlow("")
    val remark = _remark.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    val mockAccounts = listOf(
        AccountModel(
            accountName = "Saving Account",
            accountNumber = "001 751 517",
            currency = "USD",
            balance = 72392.10,
            isDefault = true,
            hasVisa = true
        ),
        AccountModel(
            accountName = "Future Plan",
            accountNumber = "001 222 333",
            currency = "USD",
            balance = 2392.68,
            isDefault = false
        )
    )

    private val maxAmountLength = 10 // Set a reasonable limit for the amount

    fun addDigit(digit: String) {
        val currentAmount = _amount.value
        if (currentAmount.length < maxAmountLength) {
            if (digit == "." && currentAmount.contains(".")) {
                return // Prevent multiple decimal points
            }
            if (digit == "." && currentAmount.isEmpty()) {
                _amount.value = "0."
                return
            }
            _amount.value = if (currentAmount == "0" && digit != ".") digit else currentAmount + digit
        }
    }

    fun clearAmount() {
        _amount.value = "0"
    }

    fun deleteLastDigit() {
        val currentAmount = _amount.value
        if (currentAmount.isNotEmpty()) {
            _amount.value = if (currentAmount.length == 1) "0" else currentAmount.dropLast(1)
        }
    }

    fun showAccountSelection() {
        _isAccountSelectionVisible.value = true
    }

    fun hideAccountSelection() {
        _isAccountSelectionVisible.value = false
    }

    fun showRemarkDialog() {
        _isRemarkDialogVisible.value = true
    }

    fun hideRemarkDialog() {
        _isRemarkDialogVisible.value = false
    }

    fun selectAccount(account: AccountModel) {
        _selectedAccount.value = account
        hideAccountSelection()
    }

    fun setRemark(remark: String) {
        _remark.value = remark
        hideRemarkDialog()
    }

    fun sendMoney() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                if (_amount.value.isEmpty() || _amount.value == "0") {
                    _error.value = "Please enter a valid amount"
                    return@launch
                }

                val amount = _amount.value.toDoubleOrNull()
                if (amount == null || amount <= 0) {
                    _error.value = "Invalid amount"
                    return@launch
                }

                // TODO: Implement the actual money transfer logic here
                // val result = repository.sendMoney(
                //     amount = amount,
                //     fromAccount = _selectedAccount.value,
                //     remark = _remark.value
                // )

                // For now, simulate a delay
                delay(1000)

                // Navigate to success screen or show error
                // _isNavigateToNext.value = true
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }
}