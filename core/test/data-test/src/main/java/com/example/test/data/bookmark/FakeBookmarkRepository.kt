package com.example.test.data.bookmark

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.repository.BookmarkRepository
import com.example.data.repository.CharacterRepository
import com.example.model.CharacterData
import com.example.test.data.cached.CachedCharacterData
import com.example.test.data.paging.FakeCharacterPagingSource
import kotlinx.coroutines.flow.Flow

class FakeBookmarkRepository(
    private var cachedBookmarkData: CachedCharacterData = CachedCharacterData()
): BookmarkRepository {
    override suspend fun addBookmark(id: Int) {
        cachedBookmarkData.setBookmark(true)
    }

    override suspend fun removeBookmark(id: Int) {
        cachedBookmarkData.setBookmark(false)
    }

    override fun loadPagingBookmarks(): Flow<PagingData<CharacterData>> =
        Pager(
            config = PagingConfig(
                pageSize = CharacterRepository.PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = CharacterRepository.PREFETCH_DISTANCE
            ),
            pagingSourceFactory = {
                FakeCharacterPagingSource(cachedBookmarkData.getData())
            }
        ).flow
}