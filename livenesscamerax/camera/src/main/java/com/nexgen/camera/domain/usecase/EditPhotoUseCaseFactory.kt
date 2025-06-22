package com.nexgen.camera.domain.usecase

import android.content.Context
import com.nexgen.camera.di.CameraModule.container
import com.nexgen.core.factory.Factory
import com.nexgen.domain.EditPhotoUseCase

internal object EditPhotoUseCaseFactory : Factory<EditPhotoUseCase> {

    private val context: Context by lazy { container.provideContext() }

    override fun create(): EditPhotoUseCase {
        return EditPhotoUseCaseImpl(context)
    }
}
