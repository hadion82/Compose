package com.example.domain.usecase.bookmark

import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.repository.BookmarkRepository
import com.example.domain.mapper.MarvelCharacterMapper
import com.example.model.MarvelCharacter
import com.example.shared.interaction.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadBookmarkUseCase @Inject constructor(
    private val repository: BookmarkRepository,
    private val mapper: MarvelCharacterMapper
) : UseCase<Unit, Flow<PagingData<MarvelCharacter>>> {

    override fun execute(params: Unit): Flow<PagingData<MarvelCharacter>> =
        repository.loadPagingBookmarks().map { it.map(mapper) }
}