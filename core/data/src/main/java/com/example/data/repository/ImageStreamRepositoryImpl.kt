package com.example.data.repository

import android.content.Context
import com.bumptech.glide.Glide
import com.example.data.repository.ImageStreamRepository
import com.example.shared.mediator.MediaUtils
import javax.inject.Inject

internal class ImageStreamRepositoryImpl @Inject constructor(): ImageStreamRepository {
    override fun download(context: Context, url: String, dir: String, fileName: String) {
        val file = Glide.with(context).asFile().load(url).submit().get()
        MediaUtils.copyToDownload(context, file, dir, fileName)
        file.delete()
    }
}