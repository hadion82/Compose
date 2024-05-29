package com.example.shared.interaction

import kotlinx.coroutines.*
import kotlin.Exception

abstract class SuspendingUseCase<in P, out T>(
    private val coroutineDispatcher: CoroutineDispatcher
) where T : Any {

    abstract suspend fun execute(params: P): T?

    suspend operator fun invoke(
        params: P
    ) = try {
        withContext(coroutineDispatcher) {
            execute(params).let {
                Result.success(it)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Result.failure(e)
    }
}
