package com.example.data.repository

import android.content.Context
import android.net.Uri
import com.bumptech.glide.Glide
import com.example.fio.media.ImageDownloader
import com.example.fio.media.MediaStore
import com.example.data.repository.ImageStreamRepository
import com.example.shared.mediator.MediaUtils
import javax.inject.Inject

internal class ImageStreamRepositoryImpl @Inject constructor(
    private val imageDownloader: ImageDownloader,
    private val mediaStore: MediaStore
) : ImageStreamRepository {
    override suspend fun download(context: Context, url: String, dir: String, fileName: String): Uri? {
        val file = imageDownloader.download(context, url)
        return mediaStore.copyToDownload(context, file, dir, fileName).apply {
            file.delete()
        }
    }
}