package com.nexgen.camera.core.processor.face

import com.nexgen.camera.core.processor.FrameProcessor
import com.nexgen.camera.domain.model.FaceResult
import kotlinx.coroutines.flow.Flow

internal interface FaceFrameProcessor : FrameProcessor {
    fun observeFaceList(): Flow<List<FaceResult>>
}
