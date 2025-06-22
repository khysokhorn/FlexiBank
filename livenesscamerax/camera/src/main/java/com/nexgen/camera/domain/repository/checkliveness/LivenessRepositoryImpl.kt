package com.nexgen.camera.domain.repository.checkliveness

import com.nexgen.camera.domain.model.FaceResult
import com.nexgen.core.extensions.orFalse
import com.nexgen.domain.model.HeadMovement
import com.nexgen.domain.repository.LivenessRepository

private const val EYE_OPENED_PROBABILITY = 0.4F
private const val IS_SMILING_PROBABILITY = 0.3F
private const val EULER_Y_RIGHT_MOVEMENT = 35
private const val EULER_Y_LEFT_MOVEMENT = -35
private const val MAXIMUM_FACES_DETECTED = 1

internal class LivenessRepositoryImpl : LivenessRepository<FaceResult> {

    override fun isEyeOpened(eyeOpenedProbabilityValue: Float?): Boolean {
        return eyeOpenedProbabilityValue?.let { (it > EYE_OPENED_PROBABILITY) }.orFalse()
    }

    override fun validateBlinkedEyes(
        leftEyeProbability: Float?,
        rightEyeProbability: Float?,
        callbackBlinked: (Boolean) -> Unit
    ): Boolean {
        val isLeftEyeOpened = leftEyeProbability?.let { it > EYE_OPENED_PROBABILITY }.orFalse()
        val isRightEyeOpened = rightEyeProbability?.let { it > EYE_OPENED_PROBABILITY }.orFalse()

        return (isLeftEyeOpened && isRightEyeOpened).also {
            if (it.not()) callbackBlinked.invoke(true)
        }
    }

    override fun isFacesDetectedCorrect(listFaceResult: List<FaceResult>) =
        listFaceResult.isNotEmpty() && listFaceResult.size > MAXIMUM_FACES_DETECTED

    override fun checkSmile(
        smilingProbability: Float?,
        callbackSmiled: (Boolean) -> Unit
    ) {
        smilingProbability?.let { it > IS_SMILING_PROBABILITY }.orFalse().also { isSmiling ->
            if (isSmiling) callbackSmiled.invoke(isSmiling)
        }
    }

    override fun detectEulerYMovement(
        headEulerAngleY: Float
    ): HeadMovement {
        return when {
            headEulerAngleY > EULER_Y_RIGHT_MOVEMENT -> HeadMovement.RIGHT
            headEulerAngleY < EULER_Y_LEFT_MOVEMENT -> HeadMovement.LEFT
            else -> HeadMovement.CENTER
        }
    }

    override fun validateHeadMovement(
        face: FaceResult,
        headMovement: HeadMovement,
        callbackHeadMovement: (Boolean) -> Unit
    ) {
        if (detectEulerYMovement(face.headEulerAngleY) == headMovement) {
            callbackHeadMovement(true)
        }
    }
}
