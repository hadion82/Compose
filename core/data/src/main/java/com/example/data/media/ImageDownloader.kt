package com.example.data.media

import android.content.Context
import java.io.File

interface ImageDownloader {
    suspend fun download(context: Context, url: String): File
}