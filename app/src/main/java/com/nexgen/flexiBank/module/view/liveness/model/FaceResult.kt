package com.nexgen.flexiBank.module.view.liveness.model

import android.graphics.Rect
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceLandmark

data class FaceResult(
    val trackingId: Int?,
    val bounds: Rect,
    val headEulerAngleX: Float,
    val headEulerAngleY: Float,
    val headEulerAngleZ: Float,
    val smilingProbability: Float?,
    val luminosity: Float? = 0F,
    val rightEyeOpenProbability: Float?,
    val leftEyeOpenProbability: Float?,
    val leftEyeLandMark: FaceLandmark?,
    val face: Face?,
    val conture: FaceContour?
)


data class FaceBoundary(
    val faceBound: List<FaceResult>,
    val imageProxy: ImageProxy? = null,
    val isHaveGlasses: Boolean =false,
    var isHaveMask: Boolean? = false,
    var eye : Float?
)
