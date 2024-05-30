package com.example.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.data.repository.SyncRepository
import com.example.database.model.BookmarkEntity
import com.example.datastore.preferences.PagingPreferencesDatastore
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
internal class CharacterMediator @Inject internal constructor(
    syncRepository: SyncRepository,
    private val dataStore: PagingPreferencesDatastore
) : RemoteMediator<Int, BookmarkEntity>(),
    SyncRepository by syncRepository {

    companion object {
        private const val START_KEY = 0
        private const val PAGE_LIMIT = 20
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BookmarkEntity>
    ): MediatorResult {
        val pagingKey = getKeyByLoadType(loadType) ?:
        return MediatorResult.Success(endOfPaginationReached = true)

        Timber.d("Mediator key : $pagingKey")

        return try {
            val reached = updateResource(pagingKey)
            MediatorResult.Success(
                endOfPaginationReached = reached
            )
        } catch (e: Exception) {
            Timber.d("Mediator e : $e")
            MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyByLoadType(loadType: LoadType): Int? {
        val savedKey = dataStore.pagingKey.get()
        val totalCount = dataStore.totalCount.get()
        return when (loadType) {
            LoadType.PREPEND -> return null
            LoadType.APPEND -> if (savedKey < totalCount) savedKey else null
            LoadType.REFRESH -> START_KEY
        }
    }

    private suspend fun updateResource(offset: Int): Boolean {
        val response = getCharacters(offset, PAGE_LIMIT)
        val results = response.data?.results
        requireNotNull(results)
        updateCharacters(results)
        return updateOffset(offset) >= updateTotalCount(response.data?.total)
    }

    private suspend fun updateTotalCount(total: Int?): Int {
        val totalCount = total ?: Int.MAX_VALUE
        dataStore.totalCount.set(totalCount)
        return totalCount
    }

    private suspend fun updateOffset(offset: Int): Int {
        val nextOffset = offset.plus(PAGE_LIMIT)
        dataStore.pagingKey.set(nextOffset)
        return nextOffset
    }
}