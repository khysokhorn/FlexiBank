package com.nexgen.livenesscamerax.navigation

import android.content.Context
import android.content.Intent
import com.nexgen.domain.model.LivenessCameraXResultDomain
import com.nexgen.livenesscamerax.domain.mapper.toPresentation
import com.nexgen.livenesscamerax.domain.model.CameraSettings
import com.nexgen.livenesscamerax.domain.model.LivenessCameraXResult
import com.nexgen.livenesscamerax.presentation.LivenessCameraXActivity

internal const val EXTRAS_LIVELINESS_CAMERA_SETTINGS = "liveness_camerax_camera_settings"

object LivelinessEntryPoint {

    var callbackResult: ((LivenessCameraXResult) -> Unit) = {}

    fun startLiveliness(
        context: Context,
        cameraSettings: CameraSettings = CameraSettings(),
        callback: (LivenessCameraXResult) -> Unit
    ) {
        context.startActivity(
            Intent(context, LivenessCameraXActivity::class.java).apply {
                putExtra(EXTRAS_LIVELINESS_CAMERA_SETTINGS, cameraSettings)
            }
        )
        callbackResult = callback
    }

    internal fun postResultCallback(livelinessCameraXResult: LivenessCameraXResultDomain) {
        callbackResult.invoke(livelinessCameraXResult.toPresentation())
    }
}
