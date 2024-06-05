package com.example.data.repository

import com.example.data.datasource.local.CharacterLocalDataSource
import com.example.data.datasource.remote.CharacterRemoteDataSource
import com.example.data.mapper.CharacterEntityMapper
import com.example.data.mapper.CharacterUpdatingEntityMapper
import com.example.datastore.preferences.PagingPreferencesDatastore
import com.example.network.model.Character
import timber.log.Timber
import javax.inject.Inject

class SyncRepositoryImpl @Inject internal constructor(
    localDataSource: CharacterLocalDataSource,
    remoteDataSource: CharacterRemoteDataSource,
    private val characterEntityMapper: CharacterEntityMapper,
    private val updatingEntityMapper: CharacterUpdatingEntityMapper
) : SyncRepository,
    CharacterLocalDataSource by localDataSource,
    CharacterRemoteDataSource by remoteDataSource {

    override suspend fun syncCharacters(results: List<Character>) {
        Timber.d("Mediator data : $results")
        val responseIds = results.mapNotNull { it.id }
        val savedIds = getIds(responseIds)
        val (saved, new) = results.filter { it.id != null }.partition { savedIds.contains(it.id) }
        update(saved.map(updatingEntityMapper))
        insert(*new.map(characterEntityMapper).toTypedArray())
    }
}