package com.example.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

internal class MarvelRetrofit @Inject constructor(
    @MarvelApiOkHttpClient private val okHttpClient: OkHttpClient
) {
    operator fun invoke(): Retrofit = Retrofit.Builder()
        .baseUrl("https://gateway.marvel.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}