package com.example.domain.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.model.CharacterData

class FakeCharacterPagingSource(
    private val data: CharacterData
): PagingSource<Int, CharacterData>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterData>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterData> {

        println("load : ${params.key}")
        val key = params.key ?: 0
        return LoadResult.Page(
            listOf(data), prevKey = null, nextKey = null
        )
    }
}