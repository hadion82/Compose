package com.example.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted

@HiltWorker
internal class SuspendingWorker(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,

): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return Result.success()
    }
}