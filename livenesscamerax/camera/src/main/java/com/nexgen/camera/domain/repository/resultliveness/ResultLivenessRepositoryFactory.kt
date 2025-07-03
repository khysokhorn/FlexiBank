package com.nexgen.camera.domain.repository.resultliveness

import com.nexgen.core.factory.Factory
import com.nexgen.domain.model.LivenessCameraXResultDomain
import com.nexgen.domain.model.PhotoResultDomain
import com.nexgen.domain.repository.ResultLivenessRepository

object ResultLivenessRepositoryFactory : Factory<ResultLivenessRepository<PhotoResultDomain>> {

    var resultCallback: (LivenessCameraXResultDomain) -> Unit = { }

    override fun create(): ResultLivenessRepository<PhotoResultDomain> {
        return ResultLivenessRepositoryImpl(resultCallback)
    }
}
