package com.example.fio.media

import android.content.Context
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class ImageDownloaderImpl @Inject constructor(): ImageDownloader {
    override suspend fun download(context: Context, url: String): File =
        withContext(Dispatchers.IO) {
            Glide.with(context).asFile().load(url).submit().get()
        }
}