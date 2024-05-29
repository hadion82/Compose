package com.example.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.data.datasource.local.BookmarkLocalDataSource
import com.example.data.datasource.local.BookmarkLocalDataSourceImpl
import com.example.data.datasource.local.PagingKeyDataSource
import com.example.data.datasource.local.PagingKeyDataSourceImpl
import com.example.data.datasource.remote.CharacterRemoteDataSource
import com.example.data.datasource.remote.CharacterRemoteDataSourceImpl
import com.example.data.mapper.BookMarkEntityMapper
import com.example.data.mapper.CharacterEntityMapper
import com.example.database.model.BookmarkEntity
import com.example.datastore.preferences.PagingPreferencesDatastore
import com.example.network.model.Character
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
internal class CharacterMediator @Inject internal constructor(
    remoteDataSource: CharacterRemoteDataSourceImpl,
    localDataSource: BookmarkLocalDataSourceImpl,
    bookmarkKeyDataSource: PagingKeyDataSourceImpl,
    private val bookmarkMapper: BookMarkEntityMapper,
    private val characterMapper: CharacterEntityMapper,
    private val dataStore: PagingPreferencesDatastore
) : RemoteMediator<Int, BookmarkEntity>(),
    CharacterRemoteDataSource by remoteDataSource,
    BookmarkLocalDataSource by localDataSource,
    PagingKeyDataSource by bookmarkKeyDataSource {

    companion object {
        private const val START_KEY = 0
        private const val PAGE_LIMIT = 20
    }

    private var totalCount = Int.MAX_VALUE
    private var key = START_KEY

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BookmarkEntity>
    ): MediatorResult {

        val savedKey = dataStore.pagingKey.get()
        key = when (loadType) {
            LoadType.PREPEND -> {
                Timber.d("Mediator PREPEND")
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                Timber.d("Mediator APPEND")
                if (savedKey < totalCount) savedKey else
                    return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.REFRESH -> {
                Timber.d("Mediator REFRESH")
                START_KEY
            }
        }
        Timber.d("Mediator key : $key")
        val response = getCharacters(key, PAGE_LIMIT)

        return try {
            val results = response.data?.results
            requireNotNull(results)
            totalCount = response.data?.total ?: Int.MAX_VALUE
            val nextKey = key.plus(PAGE_LIMIT)
            Timber.d("Mediator nextKey : $nextKey")
            val characters = results.filter { it.id != null}
            val responseIds = characters.mapNotNull { it.id }
            val savedIds = getIds(responseIds)
            update(nextKey, characters.partition { savedIds.contains(it.id) })
            val reached = nextKey >= totalCount
            Timber.d("Mediator reached : $reached")
            MediatorResult.Success(
                endOfPaginationReached = reached
            )
        } catch (e: Exception) {
            Timber.d("Mediator e : $e")
            MediatorResult.Error(e)
        }
    }

    private suspend fun update(key: Int, data: Pair<List<Character>, List<Character>>) {
        Timber.d("Mediator data : $data")
        dataStore.pagingKey.set(key)
        update(data.first.map(characterMapper))
        insert(*data.second.map(bookmarkMapper).toTypedArray())
    }
}