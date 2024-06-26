package com.example.test.data.character

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.model.CharacterData

@OptIn(ExperimentalPagingApi::class)
class FakeRemoteMediator: RemoteMediator<Int, CharacterData>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterData>
    ): MediatorResult {
        return MediatorResult.Success(endOfPaginationReached = true)
    }
}