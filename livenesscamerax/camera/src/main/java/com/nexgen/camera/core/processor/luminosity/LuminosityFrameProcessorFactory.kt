package com.nexgen.camera.core.processor.luminosity

import com.nexgen.core.factory.Factory

internal object LuminosityFrameProcessorFactory : Factory<LuminosityFrameProcessor> {
    override fun create(): LuminosityFrameProcessor {
        return LuminosityFrameProcessorImpl()
    }
}
