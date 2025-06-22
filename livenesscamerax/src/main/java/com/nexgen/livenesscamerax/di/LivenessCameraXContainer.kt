package com.nexgen.livenesscamerax.di

import android.app.Application
import android.content.Context
import com.nexgen.camera.domain.model.FaceResult
import com.nexgen.camera.domain.repository.checkliveness.CheckLivenessRepositoryFactory
import com.nexgen.camera.domain.repository.resultliveness.ResultLivenessRepositoryFactory
import com.nexgen.core.resourceprovider.ResourcesProvider
import com.nexgen.core.resourceprovider.ResourcesProviderFactory
import com.nexgen.domain.model.PhotoResultDomain
import com.nexgen.domain.repository.LivenessRepository
import com.nexgen.domain.repository.ResultLivenessRepository
import com.nexgen.livenesscamerax.domain.usecase.GetStepMessageUseCase
import com.nexgen.livenesscamerax.navigation.LivelinessEntryPoint

internal class LivenessCameraXContainer(private val application: Application) {
    private val provideLivenessEntryPoint = LivelinessEntryPoint

    private fun provideContext(): Context {
        return application.applicationContext
    }

    fun provideResourceProvider(): ResourcesProvider {
        return ResourcesProviderFactory(provideContext()).create()
    }

    fun provideResultLivenessRepository(): ResultLivenessRepository<PhotoResultDomain> {
        return ResultLivenessRepositoryFactory.apply {
            resultCallback = provideLivenessEntryPoint::postResultCallback
        }.create()
    }

    fun provideLivenessRepository(): LivenessRepository<FaceResult> {
        return CheckLivenessRepositoryFactory.create()
    }

    fun provideGetStepMessagesUseCase(
        resourcesProvider: ResourcesProvider = provideResourceProvider()
    ): GetStepMessageUseCase {
        return GetStepMessageUseCase(resourcesProvider)
    }
}
