package com.nexgen.camera.navigation

import androidx.lifecycle.LifecycleOwner
import com.nexgen.camera.CameraX
import com.nexgen.camera.core.callback.CameraXCallback
import com.nexgen.camera.di.CameraModule
import com.nexgen.camera.di.CameraModule.container
import com.nexgen.domain.model.CameraSettingsDomain

class CameraXNavigation(private val lifecycleOwner: LifecycleOwner) {

    init { initializeModuleLifecyle(lifecycleOwner) }

    fun provideCameraXModule(
        cameraSettings: CameraSettingsDomain,
        cameraXCallback: CameraXCallback,
    ): CameraX {
        return container.provideCameraX(cameraSettings, cameraXCallback, lifecycleOwner)
    }

    private fun initializeModuleLifecyle(lifecycleOwner: LifecycleOwner) {
        CameraModule.lifecycleOwner = lifecycleOwner
    }
}
