package com.example.test.data.media

import android.content.Context
import android.net.Uri
import com.example.data.repository.ImageStreamRepository
import com.example.test.fio.FakeImageDownloader
import com.example.test.fio.FakeMediaStore

class FakeImageRepository: ImageStreamRepository {

    private val imageDownloader = FakeImageDownloader()
    private val mediaStore = FakeMediaStore()
    override suspend fun download(
        context: Context,
        url: String,
        dir: String,
        fileName: String
    ): Uri {
        val file = imageDownloader.download(context, url)
        return mediaStore.copyToDownload(context, file, dir, fileName)
    }
}