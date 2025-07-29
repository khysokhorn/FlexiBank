package com.nexgen.livenesscamerax.domain.mapper

import com.nexgen.domain.model.LivenessCameraXResultDomain
import com.nexgen.livenesscamerax.domain.model.LivenessCameraXResult

fun LivenessCameraXResultDomain.toPresentation(): LivenessCameraXResult {
    return LivenessCameraXResult(
        createdByUser = this.createdByUser?.toPresentation(),
        createdBySteps = this.createdBySteps?.map { it.toPresentation() },
        error = this.error?.toPresentation()
    )
}
