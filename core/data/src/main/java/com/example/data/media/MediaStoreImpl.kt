package com.example.data.media

import android.content.Context
import android.net.Uri
import com.example.shared.mediator.MediaUtils
import java.io.File
import javax.inject.Inject

class MediaStoreImpl @Inject constructor(): MediaStore {
    override fun copyToDownload(
        context: Context,
        inputFile: File,
        dir: String,
        fileName: String
    ): Uri? =
        MediaUtils.copyToDownload(context, inputFile, dir, fileName)
}