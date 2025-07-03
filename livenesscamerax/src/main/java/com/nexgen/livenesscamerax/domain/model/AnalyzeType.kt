package com.nexgen.livenesscamerax.domain.model

import com.nexgen.domain.model.AnalyzeTypeDomain

enum class AnalyzeType {
    FACE_PROCESSOR,
    LUMINOSITY,
    COMPLETE,
}

fun AnalyzeType.toDomain(): AnalyzeTypeDomain {
    return when (this) {
        AnalyzeType.FACE_PROCESSOR -> AnalyzeTypeDomain.FACE_PROCESSOR
        AnalyzeType.LUMINOSITY -> AnalyzeTypeDomain.LUMINOSITY
        AnalyzeType.COMPLETE -> AnalyzeTypeDomain.COMPLETE
    }
}
