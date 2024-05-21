package com.example.data.datasource.stream

import android.content.Context

interface ImageStreamRepository {
    fun download(context: Context, url: String, dir: String, fileName: String)
}