package com.example.shared.extensions

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.plus

suspend fun newSupervisorJob(
    dispatcher: CoroutineDispatcher,
    block: suspend CoroutineScope.() -> Job
): Job {
    val job = kotlin.coroutines.coroutineContext[Job]
    return newSupervisorScope(dispatcher).block().also {
        job?.invokeOnCompletion { job.cancel() }
    }
}

suspend fun newSupervisorScope(
    dispatcher: CoroutineDispatcher
) = coroutineScope { (this + SupervisorJob() + dispatcher) }