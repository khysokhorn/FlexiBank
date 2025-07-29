package com.nexgen.camera.domain.mapper

import com.nexgen.core.extensions.encoderFilePath
import com.nexgen.core.extensions.getFileNameWithoutExtension
import com.nexgen.domain.model.PhotoResultDomain

internal fun List<String>.toPhotoResult(): List<PhotoResultDomain> {
    return this.map {
        PhotoResultDomain(
            createdAt = it.getFileNameWithoutExtension(),
            fileBase64 = it.encoderFilePath()
        )
    }
}
