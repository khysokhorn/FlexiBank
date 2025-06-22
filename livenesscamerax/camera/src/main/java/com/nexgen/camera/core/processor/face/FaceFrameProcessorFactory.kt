package com.nexgen.camera.core.processor.face

import com.nexgen.camera.core.detector.VisionFaceDetector
import com.nexgen.camera.core.processor.face.FaceFrameProcessor
import com.nexgen.camera.di.CameraModule.container
import com.nexgen.core.factory.Factory

internal object FaceFrameProcessorFactory : Factory<FaceFrameProcessor> {

    private val detector: VisionFaceDetector = container.provideVisionFaceDetector()

    override fun create(): FaceFrameProcessor {
        return FaceFrameProcessorImpl(container.provideCoroutineScope(), detector)
    }
}
