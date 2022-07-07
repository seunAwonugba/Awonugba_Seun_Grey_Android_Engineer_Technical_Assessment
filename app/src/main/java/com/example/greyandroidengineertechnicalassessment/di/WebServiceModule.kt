package com.example.greyandroidengineertechnicalassessment.di

import com.example.greyandroidengineertechnicalassessment.BuildConfig
import com.example.greyandroidengineertechnicalassessment.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WebServiceModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(makeLoggingInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


//    @Singleton
//    @Provides
//    fun provideWebService(): GitHubWebService {
//
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level= HttpLoggingInterceptor.Level.BODY
//        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
//
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//            .create(GitHubWebService::class.java)
//
//    }

//    @Singleton
//    @Provides
//    fun provideGitHubRepoDataBase(
//        @ApplicationContext context: Context
//    ) = Room.databaseBuilder(
//        context,
//        GitHubRepoDataBase::class.java,
//        GITHUB_REPO_DATABASE_NAME
//    ).build()
}