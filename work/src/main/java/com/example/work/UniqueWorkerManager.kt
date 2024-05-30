package com.example.work

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.Operation
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkRequest
import kotlinx.coroutines.flow.Flow

interface Runnable {
    val isRunning: Flow<Boolean>
}

interface UniqueWorkerManager: Runnable {
    fun enqueue(
        existingWorkPolicy: ExistingWorkPolicy,
        oneTimeWorkRequest: OneTimeWorkRequest
    ): Operation

    fun enqueue(
        existingPeriodicWorkPolicy: ExistingPeriodicWorkPolicy,
        periodicWorkRequest: PeriodicWorkRequest
    ): Operation

    fun cancel(): Operation
}

interface WorkerManager: Runnable {

    fun enqueue(
        workRequest: WorkRequest
    ): Operation

    fun cancel()
}