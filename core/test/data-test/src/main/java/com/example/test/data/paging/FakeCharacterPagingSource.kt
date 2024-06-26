package com.example.test.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.model.CharacterData
import kotlinx.coroutines.delay

class FakeCharacterPagingSource(
    private val data: CharacterData
): PagingSource<Int, CharacterData>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterData>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterData> {
        return LoadResult.Page(
            listOf(data), prevKey = null, nextKey = null
        )
    }
}