package com.nexgen.livenesscamerax.domain.model

import com.nexgen.domain.model.StorageTypeDomain

enum class StorageType {
    INTERNAL,
    EXTERNAL
}

fun StorageType.toDomain(): StorageTypeDomain {
    return when (this) {
        StorageType.INTERNAL -> StorageTypeDomain.INTERNAL
        StorageType.EXTERNAL -> StorageTypeDomain.EXTERNAL
    }
}
