package com.example.fio.media

import android.content.Context
import java.io.File

interface ImageDownloader {
    suspend fun download(context: Context, url: String): File
}