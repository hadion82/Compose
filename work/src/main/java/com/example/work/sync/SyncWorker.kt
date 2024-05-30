package com.example.work.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.Operation
import androidx.work.WorkerParameters
import com.example.domain.usecase.character.SyncCharacterUseCase
import com.example.work.DefaultUniqueWorkerManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val syncCharacterUseCase: SyncCharacterUseCase
) : CoroutineWorker(context, workerParameters) {

    companion object {
        const val OFFSET_KEY = "offset_key"
    }

    override suspend fun doWork(): Result =
        inputData.getInt(OFFSET_KEY, 0).let { offset ->
            if (syncCharacterUseCase(
                    SyncCharacterUseCase.Params(offset)
                ).isSuccess
            ) Result.success()
            else Result.failure()
        }
}