package com.example.compose.di

import com.example.data.BuildConfig
import com.example.data.api.MarvelApiOkHttpClient
import com.example.data.api.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    @MarvelApiOkHttpClient
    fun provideMarvelHttpClient() = OkHttpClient.Builder().apply {
        readTimeout(10, TimeUnit.SECONDS)
        writeTimeout(10, TimeUnit.SECONDS)
        connectTimeout(10, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
        addInterceptor(AuthInterceptor())
        if(BuildConfig.DEBUG) {
            addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }
    }.build()
}