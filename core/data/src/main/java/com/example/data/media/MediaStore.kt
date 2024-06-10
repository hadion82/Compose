package com.example.data.media

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

interface MediaStore {
    @Throws(IOException::class, FileNotFoundException::class)
    fun copyToDownload(context: Context, inputFile: File, dir: String, fileName: String): Uri?
}