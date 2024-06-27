package com.example.data.repository

import com.example.data.datasource.local.CharacterLocalDataSource
import com.example.data.datasource.remote.CharacterRemoteDataSource
import com.example.data.mapper.DataToEntityMapper
import com.example.data.mapper.DataToUpdatingEntityMapper
import com.example.model.CharacterData
import timber.log.Timber
import javax.inject.Inject

class SyncRepositoryImpl @Inject internal constructor(
    localDataSource: CharacterLocalDataSource,
    remoteDataSource: CharacterRemoteDataSource,
    private val characterEntityMapper: DataToEntityMapper,
    private val updatingEntityMapper: DataToUpdatingEntityMapper
) : SyncRepository,
    CharacterLocalDataSource by localDataSource,
    CharacterRemoteDataSource by remoteDataSource {

    override suspend fun updateCharacters(results: List<CharacterData>) {
        val responseIds = results.map { it.id }
        val savedIds = getIds(responseIds)
        val (saved, new) = results.partition { savedIds.contains(it.id) }
        update(saved.map(updatingEntityMapper))
        insert(*new.map(characterEntityMapper).toTypedArray())
    }
}