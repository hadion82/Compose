package com.example.test.fio

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.example.fio.media.MediaStore
import java.io.File

class FakeMediaStore: MediaStore {
    override fun copyToDownload(
        context: Context,
        inputFile: File,
        dir: String,
        fileName: String
    ): Uri = "downloadFile".toUri()
}