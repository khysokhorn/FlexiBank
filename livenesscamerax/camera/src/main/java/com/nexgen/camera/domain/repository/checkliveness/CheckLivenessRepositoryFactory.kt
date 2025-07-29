package com.nexgen.camera.domain.repository.checkliveness

import com.nexgen.camera.domain.model.FaceResult
import com.nexgen.core.factory.Factory
import com.nexgen.domain.repository.LivenessRepository

object CheckLivenessRepositoryFactory : Factory<LivenessRepository<FaceResult>> {
    override fun create(): LivenessRepository<FaceResult> {
        return LivenessRepositoryImpl()
    }
}
