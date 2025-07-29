package com.nexgen.camera.domain.repository.resultliveness

import com.nexgen.camera.domain.mapper.toPhotoResult
import com.nexgen.domain.model.LivenessCameraXResultDomain
import com.nexgen.domain.model.PhotoResultDomain
import com.nexgen.domain.repository.ResultLivenessRepository

internal class ResultLivenessRepositoryImpl(
    private val resultCallback: (LivenessCameraXResultDomain) -> Unit
) : ResultLivenessRepository<PhotoResultDomain> {

    override fun success(photoResult: PhotoResultDomain, filesPath: List<String>) {
        val livenessCameraXResult =
            LivenessCameraXResultDomain(photoResult, filesPath.toPhotoResult())
        resultCallback(livenessCameraXResult)
    }

    override fun error(exception: Exception) {
        val livenessCameraXResult = LivenessCameraXResultDomain(exception)
        resultCallback(livenessCameraXResult)
    }
}
