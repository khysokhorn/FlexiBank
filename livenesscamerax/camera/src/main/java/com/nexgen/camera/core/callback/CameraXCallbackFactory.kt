package com.nexgen.camera.core.callback

import com.nexgen.core.factory.Factory
import com.nexgen.domain.model.PhotoResultDomain

object CameraXCallbackFactory : Factory<CameraXCallback> {

    var onImageSavedAction: (PhotoResultDomain, Boolean) -> Unit = { _, _ -> }
    var onErrorAction: (Exception) -> Unit = {}

    override fun create(): CameraXCallback {
        return CameraXCallbackImpl(onImageSavedAction, onErrorAction)
    }
}
