package com.example.test.fio

import android.content.Context
import com.example.fio.media.ImageDownloader
import java.io.File

class FakeImageDownloader: ImageDownloader {
    override suspend fun download(context: Context, url: String): File =
        File("testFile")
}