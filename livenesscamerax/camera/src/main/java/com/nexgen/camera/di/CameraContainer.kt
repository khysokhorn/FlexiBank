package com.nexgen.camera.di

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.nexgen.camera.CameraX
import com.nexgen.camera.CameraXImpl
import com.nexgen.camera.core.callback.CameraXCallback
import com.nexgen.camera.core.detector.VisionFaceDetector
import com.nexgen.camera.core.processor.face.FaceFrameProcessor
import com.nexgen.camera.core.processor.face.FaceFrameProcessorFactory
import com.nexgen.camera.core.processor.luminosity.LuminosityFrameProcessor
import com.nexgen.camera.core.processor.luminosity.LuminosityFrameProcessorFactory
import com.nexgen.camera.di.CameraModule.application
import com.nexgen.camera.domain.repository.file.FileRepositoryFactory
import com.nexgen.camera.domain.usecase.EditPhotoUseCaseFactory
import com.nexgen.domain.EditPhotoUseCase
import com.nexgen.domain.model.CameraSettingsDomain
import com.nexgen.domain.model.StorageTypeDomain
import com.nexgen.domain.repository.FileRepository
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

internal class CameraContainer {
    fun provideContext(): Context {
        return application.applicationContext
    }

    fun provideCoroutineScope(): CoroutineScope {
        return CameraModule.lifecycleOwner.lifecycleScope
    }

    fun provideExecutorService(): ExecutorService {
        return Executors.newSingleThreadExecutor()
    }

    fun provideLuminosityFrameProcessor(): LuminosityFrameProcessor {
        return LuminosityFrameProcessorFactory.create()
    }

    fun provideFaceFrameProcessor(): FaceFrameProcessor {
        return FaceFrameProcessorFactory.create()
    }

    fun provideVisionFaceDetector(): VisionFaceDetector {
        return VisionFaceDetector()
    }

    fun provideEditPhotoUseCase(): EditPhotoUseCase {
        return EditPhotoUseCaseFactory.create()
    }

    private fun provideFileRepository(storageType: StorageTypeDomain): FileRepository {
        return FileRepositoryFactory.apply { this.storageType = storageType }.create()
    }

    fun provideCameraX(
        cameraSettings: CameraSettingsDomain,
        cameraXCallback: CameraXCallback,
        lifecycleOwner: LifecycleOwner
    ): CameraX {
        return CameraXImpl(
            settings = cameraSettings,
            cameraXCallback = cameraXCallback,
            lifecycleOwner = lifecycleOwner,
            fileRepository = provideFileRepository(cameraSettings.storageType)
        )
    }
}
