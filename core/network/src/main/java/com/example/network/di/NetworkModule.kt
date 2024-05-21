package com.example.network.di

import com.example.network.BuildConfig
import com.example.network.interceptor.AuthInterceptor
import com.example.network.qualifier.MarvelOkHttpClient
import com.example.network.qualifier.MarvelRetrofit
import com.example.network.service.MarvelService
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
internal object NetworkModule {

    @Singleton
    @Provides
    @MarvelOkHttpClient
    fun provideMarvelOkHttpClient() = OkHttpClient.Builder().apply {
        readTimeout(10, TimeUnit.SECONDS)
        writeTimeout(10, TimeUnit.SECONDS)
        connectTimeout(10, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
        addInterceptor(AuthInterceptor())
        if (BuildConfig.DEBUG) {
            addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }
    }.build()

    @Singleton
    @Provides
    @MarvelRetrofit
    fun provideRetrofit(@MarvelOkHttpClient okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideMarvelService(@MarvelRetrofit retrofit: Retrofit): MarvelService =
        retrofit.create(MarvelService::class.java)
}