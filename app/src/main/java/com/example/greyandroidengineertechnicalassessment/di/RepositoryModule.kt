package com.example.greyandroidengineertechnicalassessment.di

import com.example.greyandroidengineertechnicalassessment.remote.api.GitHubWebService
import com.example.greyandroidengineertechnicalassessment.repository.MainRepository
import com.example.greyandroidengineertechnicalassessment.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideSearchRepoWebService(retrofit: Retrofit) : GitHubWebService = retrofit.create()

    @Singleton
    @Provides
    fun provideRepository(gitHubWebService: GitHubWebService) : MainRepository{
        return MainRepositoryImpl(gitHubWebService = gitHubWebService)
    }

}