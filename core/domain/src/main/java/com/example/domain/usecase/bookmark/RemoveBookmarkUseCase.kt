package com.example.domain.usecase.bookmark

import com.example.data.repository.BookmarkRepository
import com.example.shared.di.IoDispatcher
import com.example.shared.interaction.SuspendingUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RemoveBookmarkUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val repository: BookmarkRepository
) : SuspendingUseCase<RemoveBookmarkUseCase.Params, Unit>(dispatcher) {

    override suspend fun execute(params: Params) =
        repository.removeBookmark(params.id)

    data class Params(val id: Int)
}