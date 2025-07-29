package com.nexgen.camera.core.processor.face

import android.annotation.SuppressLint
import android.media.Image
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.face.Face
import com.nexgen.camera.core.detector.VisionFaceDetector
import com.nexgen.camera.domain.mapper.toFaceResult
import com.nexgen.camera.domain.model.FaceResult
import com.nexgen.core.extensions.getLuminosity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

internal class FaceFrameProcessorImpl(
    private val coroutineScope: CoroutineScope,
    private val detector: VisionFaceDetector,
) : FaceFrameProcessor {
    private val facesBroadcastChannel = MutableSharedFlow<List<FaceResult>>()

    override fun observeFaceList(): Flow<List<FaceResult>> = facesBroadcastChannel.asSharedFlow()

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override suspend fun onFrameCaptured(imageProxy: ImageProxy) {
        detector.detect(imageProxy) {
            coroutineScope.launch {
                facesBroadcastChannel.emit(
                    prepareResultWithLuminosity(it, imageProxy)
                )
            }
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun prepareResultWithLuminosity(
        listFace: List<Face>,
        imageProxy: ImageProxy
    ): List<FaceResult> = listFace.map { face ->
        addLuminosity(face.toFaceResult(), imageProxy.image)
    }

    private fun addLuminosity(faceResult: FaceResult, image: Image?): FaceResult {
        return image?.let { faceResult.copy(luminosity = image.getLuminosity()) } ?: faceResult
    }
}
