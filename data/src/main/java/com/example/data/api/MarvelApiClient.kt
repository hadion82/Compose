package com.example.data.api

import javax.inject.Inject

internal class MarvelApiClient @Inject constructor(
    private val retrofit: MarvelRetrofit
) {
    operator fun invoke(): MarvelApi =
        retrofit().create(MarvelApi::class.java)
}