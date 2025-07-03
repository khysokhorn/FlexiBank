package com.nexgen.camera.domain.repository.file

import android.content.Context
import com.nexgen.camera.di.CameraModule.container
import com.nexgen.core.factory.Factory
import com.nexgen.domain.model.StorageTypeDomain
import com.nexgen.domain.repository.FileRepository

internal object FileRepositoryFactory : Factory<FileRepository> {

    private val context: Context by lazy { container.provideContext() }
    var storageType: StorageTypeDomain = StorageTypeDomain.INTERNAL

    override fun create(): FileRepository {
        return FileRepositoryImpl(storageType, context)
    }
}
