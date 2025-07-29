package com.nexgen.camera.core.processor.luminosity

import com.nexgen.camera.core.processor.FrameProcessor
import kotlinx.coroutines.flow.Flow

internal interface LuminosityFrameProcessor : FrameProcessor {
    fun observeLuminosity(): Flow<Double>
}
