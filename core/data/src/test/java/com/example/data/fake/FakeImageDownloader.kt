package com.example.data.fake

import android.content.Context
import com.example.data.media.ImageDownloader
import java.io.File

class FakeImageDownloader: ImageDownloader {
    override suspend fun download(context: Context, url: String): File =
        File("testFile")
}