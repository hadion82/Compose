package com.example.work.sync

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.Operation
import com.example.work.DefaultUniqueWorkerManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val SYNC_UNIQUE_NAME = "SyncWorkManager"

class SyncWorkerManager @Inject constructor(
    @ApplicationContext context: Context
) : DefaultUniqueWorkerManager(context, SYNC_UNIQUE_NAME) {
    fun sync(offset: Int): Operation {
        return enqueue(
            existingWorkPolicy = ExistingWorkPolicy.REPLACE,
            oneTimeWorkRequest = OneTimeWorkRequest.Builder(SyncWorker::class.java)
                .setInputData(
                    Data.Builder().putInt(SyncWorker.OFFSET_KEY, offset)
                        .build()
                ).build()
        )
    }
}