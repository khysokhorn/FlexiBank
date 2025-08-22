package com.nexgen.flexiBank.di

import android.content.Context
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource() = RemoteDataSource()

    @Provides
    @Singleton
    fun provideApiService(
        @ApplicationContext context: Context,
        remoteDataSource: RemoteDataSource
    ): ApiInterface {
        return remoteDataSource.buildApi(context, ApiInterface::class.java)
    }
}
