package com.nexgen.livenesscamerax.domain.mapper

import com.nexgen.domain.model.LivenessCameraXErrorDomain
import com.nexgen.livenesscamerax.domain.model.LivenessCameraXError

fun LivenessCameraXErrorDomain.toPresentation(): LivenessCameraXError {
    return LivenessCameraXError(
        message = this.message,
        cause = this.cause,
        exception = this.exception
    )
}
