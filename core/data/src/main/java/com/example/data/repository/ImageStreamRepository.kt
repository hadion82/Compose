package com.example.data.repository

import android.content.Context
import android.net.Uri

interface ImageStreamRepository {
    suspend fun download(context: Context, url: String, dir: String, fileName: String): Uri?
}