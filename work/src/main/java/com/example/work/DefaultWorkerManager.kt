package com.example.work

import android.content.Context
import androidx.work.Operation
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkQuery
import androidx.work.WorkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import java.util.UUID

open class DefaultWorkerManager(
    private val context: Context,
) : WorkerManager {

    private val _uuid: MutableSharedFlow<UUID> = MutableSharedFlow()
    private lateinit var uuid: UUID

    override val isRunning: Flow<Boolean> =
        _uuid.flatMapMerge { uuid ->
            WorkManager.getInstance(context).getWorkInfosFlow(
                WorkQuery.fromIds(uuid)
            )
                .map(List<WorkInfo>::anyRunning)
                .conflate()
        }

    override fun enqueue(workRequest: WorkRequest): Operation {
        val workManager = WorkManager.getInstance(context)
        uuid = workRequest.id
        _uuid.tryEmit(workRequest.id)
        return workManager.enqueue(workRequest)
    }

    override fun cancel() {
        if(::uuid.isInitialized) {
            WorkManager.getInstance(context).cancelWorkById(uuid)
        }
    }
}