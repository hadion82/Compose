package com.example.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.data.datasource.local.PagingKeyDataSource
import com.example.data.datasource.local.PagingKeyDataSourceImpl
import com.example.data.datasource.local.BookmarkLocalDataSource
import com.example.data.datasource.local.BookmarkLocalDataSourceImpl
import com.example.data.datasource.remote.CharacterRemoteDataSource
import com.example.data.datasource.remote.CharacterRemoteDataSourceImpl
import com.example.data.entity.BookmarkEntity
import com.example.data.entity.PagingkKeyEntity
import com.example.data.mapper.BookMarkEntityMapper
import com.example.data.mapper.PagingCharacterMapper
import com.example.data.model.MarvelCharacter
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterMediator @Inject internal constructor(
    remoteDataSource: CharacterRemoteDataSourceImpl,
    localDataSource: BookmarkLocalDataSourceImpl,
    bookmarkKeyDataSource: PagingKeyDataSourceImpl,
    private val bookmarkMapper: BookMarkEntityMapper,
    private val characterMapper: PagingCharacterMapper
) : RemoteMediator<Int, BookmarkEntity>(),
    CharacterRemoteDataSource by remoteDataSource,
    BookmarkLocalDataSource by localDataSource,
    PagingKeyDataSource by bookmarkKeyDataSource {

    companion object {
        private const val START_KEY = 0
        private const val PAGE_LIMIT = 20
        private const val KEY_TYPE = "bookmark"
    }

    private var totalCount = Int.MAX_VALUE
    private var key = START_KEY

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BookmarkEntity>
    ): MediatorResult {
        val savedKey = getKey(KEY_TYPE)?.key ?: START_KEY
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
            totalCount = response.data.total ?: Int.MAX_VALUE
            val nextKey = key.plus(CharacterPagingSource.PAGE_LIMIT)
            Timber.d("Mediator nextKey : $nextKey")
            val characters = results.map(characterMapper)
            val responseIds = characters.map { it.id }
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

    private suspend fun update(key: Int, data: Pair<List<MarvelCharacter>, List<MarvelCharacter>>) {
        Timber.d("Mediator data : $data")
        insertKey(PagingkKeyEntity(KEY_TYPE, key))
        update(data.first)
        insert(*data.second.map(bookmarkMapper).toTypedArray())
    }
}