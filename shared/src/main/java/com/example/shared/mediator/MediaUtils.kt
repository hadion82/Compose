package com.example.shared.mediator

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment.*
import android.os.FileUtils
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

interface SdkDelegate {
    @Throws(IOException::class, FileNotFoundException::class)
    fun copyToDownload(context: Context, inputFile: File, dir: String, fileName: String)
}

object MediaUtils : SdkDelegate {

    private fun getMimeType(fileName: String): String? =
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(
            MimeTypeMap.getFileExtensionFromUrl(fileName)
        )

    override fun copyToDownload(context: Context, inputFile: File, dir: String, fileName: String) {
        (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) Q else P)
            .copyToDownload(context, inputFile, dir, fileName)
    }

    object Q : SdkDelegate {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun copyToDownload(
            context: Context,
            inputFile: File,
            dir: String,
            fileName: String
        ) {
            val mimeType = getMimeType(fileName)
            val values = getValues(dir, fileName, mimeType ?: "image/*")
            val collection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL)
            with(context.contentResolver) {
                insert(collection, values)?.let {
                    openFileDescriptor(it, "w", null)?.use { fd ->
                        FileUtils.copy(FileInputStream(inputFile), FileOutputStream(fd.fileDescriptor))
                    }
                    values.clear()
                    values.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    update(it, values, null, null)
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.Q)
        private fun getValues(dir: String, fileName: String, mimeType: String) =
            ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    DIRECTORY_DOWNLOADS + dir
                )
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
    }

    object P : SdkDelegate {
        override fun copyToDownload(
            context: Context,
            inputFile: File,
            dir: String,
            fileName: String
        ) {
            val outputFile = File(externalDownloadsDir(dir), fileName)
            FileOutputStream(outputFile).use { fos ->
                FileInputStream(inputFile).use {fis ->
                    fis.copyTo(fos)
                }
            }
        }

        private fun externalDownloadsDir(dir: String) =
            File(getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), dir)
                .apply { if (exists().not()) mkdir() }
    }
}