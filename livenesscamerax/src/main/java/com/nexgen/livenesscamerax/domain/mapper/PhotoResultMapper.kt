package com.nexgen.livenesscamerax.domain.mapper

import com.nexgen.domain.model.PhotoResultDomain
import com.nexgen.livenesscamerax.domain.model.PhotoResult

fun PhotoResultDomain.toPresentation(): PhotoResult {
    return PhotoResult(createdAt = this.createdAt, fileBase64 = this.fileBase64)
}
