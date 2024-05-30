package com.example.work

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map

open class DefaultUniqueWorkerManager (
    context: Context,
    private val uniqueName: String
) : UniqueWorkerManager {

    private val workManager: WorkManager = WorkManager.getInstance(context)
    override val isRunning: Flow<Boolean> =
        workManager.getWorkInfosForUniqueWorkFlow(uniqueName)
            .map(List<WorkInfo>::anyRunning)
            .conflate()

    override fun enqueue(
        existingWorkPolicy: ExistingWorkPolicy,
        oneTimeWorkRequest: OneTimeWorkRequest
    ) =
        workManager.enqueueUniqueWork(
            uniqueName, existingWorkPolicy, oneTimeWorkRequest
        )

    override fun enqueue(
        existingPeriodicWorkPolicy: ExistingPeriodicWorkPolicy,
        periodicWorkRequest: PeriodicWorkRequest
    ) =
        workManager.enqueueUniquePeriodicWork(
            uniqueName, existingPeriodicWorkPolicy, periodicWorkRequest
        )

    override fun cancel() =
        workManager.cancelUniqueWork(uniqueName)
}