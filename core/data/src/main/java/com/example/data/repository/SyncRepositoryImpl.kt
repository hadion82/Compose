package com.example.data.repository

import com.example.data.datasource.local.BookmarkLocalDataSource
import com.example.data.datasource.remote.CharacterRemoteDataSource
import com.example.data.mapper.BookMarkEntityMapper
import com.example.data.mapper.CharacterEntityMapper
import com.example.network.model.Character
import timber.log.Timber
import javax.inject.Inject

internal class SyncRepositoryImpl @Inject constructor(
    characterRemoteDataSource: CharacterRemoteDataSource,
    bookmarkLocalDataSource: BookmarkLocalDataSource,
    private val bookmarkMapper: BookMarkEntityMapper,
    private val characterMapper: CharacterEntityMapper
) : SyncRepository, CharacterRemoteDataSource by characterRemoteDataSource,
    BookmarkLocalDataSource by bookmarkLocalDataSource {
    override suspend fun syncCharacters(offset: Int, limit: Int) {
        val results = getCharacters(offset, limit).data?.results
        requireNotNull(results)
        updateCharacters(results)
    }

    override suspend fun updateCharacters(results: List<Character>) {
        Timber.d("Mediator data : $results")
        val responseIds = results.mapNotNull { it.id }
        val savedIds = getIds(responseIds)
        val(saved, new) = results.filter { it.id != null }.partition { savedIds.contains(it.id) }
        update(saved.map(characterMapper))
        insert(*new.map(bookmarkMapper).toTypedArray())
    }
}