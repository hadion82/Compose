package com.example.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.datasource.local.BookmarkLocalDataSource
import com.example.data.datasource.local.BookmarkLocalDataSourceImpl
import com.example.data.datasource.remote.CharacterRemoteDataSource
import com.example.data.datasource.remote.CharacterRemoteDataSourceImpl
import com.example.data.mapper.PagingCharacterMapper
import com.example.database.model.BookmarkEntity

internal class CharacterPagingSource internal constructor(
    remoteDataSource: CharacterRemoteDataSourceImpl,
    localDataSource: BookmarkLocalDataSourceImpl,
    private val mapper: PagingCharacterMapper
) : PagingSource<Int, BookmarkEntity>(),
    CharacterRemoteDataSource by remoteDataSource,
    BookmarkLocalDataSource by localDataSource {


    override fun getRefreshKey(state: PagingState<Int, BookmarkEntity>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(PAGE_LIMIT)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(PAGE_LIMIT)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookmarkEntity> {
        try {
            val key: Int = params.key ?: 0
            if (key < 0) return emptyPrevResult()
            val response = getCharacters(key, limit = PAGE_LIMIT)
            val results = response.data?.results
            requireNotNull(results)
            val total = response.data?.total ?: Int.MAX_VALUE
            val nextKey = key.plus(PAGE_LIMIT)
            val characters = results.map(mapper)
            update(characters)
            val data = getBookmarksById(characters.map { it.id })
            return LoadResult.Page(
                data = data,
                prevKey = key.minus(PAGE_LIMIT),
                nextKey = if (nextKey < total) nextKey else null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private fun emptyPrevResult() =
        LoadResult.Page(
            data = emptyList<BookmarkEntity>(),
            prevKey = null,
            nextKey = 0
        )

    companion object {
        const val PAGE_LIMIT = 20
    }
}