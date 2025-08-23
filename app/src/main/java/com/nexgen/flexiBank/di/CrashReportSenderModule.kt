package com.nexgen.flexiBank.di

import com.nexgen.flexiBank.module.crash.CrashReportSender
import com.nexgen.flexiBank.module.crash.DefaultCrashReportSender
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CrashReportSenderModule {
    @Binds
    abstract fun providesCrashReportSender(
        defaultCrashReportSender: DefaultCrashReportSender,
    ): CrashReportSender
}