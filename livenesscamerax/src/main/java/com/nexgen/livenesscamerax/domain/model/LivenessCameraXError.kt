package com.nexgen.livenesscamerax.domain.model

data class LivenessCameraXError(
    val message: String,
    val cause: String,
    val exception: Exception
)
