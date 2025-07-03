package com.nexgen.livenesscamerax.presentation.viewmodel

import com.nexgen.core.viewmodel.UIState

internal data class LivenessViewState(
    val messageLiveness: String = "",
    val isButtonEnabled: Boolean = false
) : UIState {
    fun livenessMessage(message: String) = this.copy(messageLiveness = message)

    fun livenessError(message: String) = this.copy(
        messageLiveness = message,
        isButtonEnabled = false
    )

    fun stepsCompleted(message: String) = this.copy(
        messageLiveness = message,
        isButtonEnabled = true
    )
}
