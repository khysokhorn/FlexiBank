package com.nexgen.camera.domain.mapper

import androidx.camera.core.CameraSelector
import com.nexgen.domain.model.CameraLensDomain

internal fun CameraLensDomain.toCameraSelector(): CameraSelector {
    return when (this) {
        CameraLensDomain.DEFAULT_BACK_CAMERA -> CameraSelector.DEFAULT_BACK_CAMERA
        CameraLensDomain.DEFAULT_FRONT_CAMERA -> CameraSelector.DEFAULT_FRONT_CAMERA
    }
}
