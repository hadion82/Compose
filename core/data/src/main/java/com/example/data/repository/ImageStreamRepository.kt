package com.example.data.repository

import android.content.Context

interface ImageStreamRepository {
    fun download(context: Context, url: String, dir: String, fileName: String)
}