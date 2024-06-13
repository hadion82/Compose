package com.example.test.data.bookmark

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.repository.BookmarkRepository
import com.example.data.repository.CharacterRepository
import com.example.model.CharacterData
import com.example.test.data.paging.FakeCharacterPagingSource
import kotlinx.coroutines.flow.Flow

class FakeBookmarkRepository: BookmarkRepository {

    private val testBookmarkData = CharacterData(
        id = 1,
        name = "name1",
        description = "description1",
        thumbnail = null,
        urlCount = 0,
        comicCount = 0,
        storyCount = 0,
        eventCount = 0,
        seriesCount = 0,
        mark = false
    )

    private var cachedBookmarkData = testBookmarkData
    override suspend fun addBookmark(id: Int) {
        cachedBookmarkData = testBookmarkData.copy(mark = true)
    }

    override suspend fun removeBookmark(id: Int) {
        cachedBookmarkData = testBookmarkData.copy(mark = false)
    }

    override fun loadPagingBookmarks(): Flow<PagingData<CharacterData>> =
        Pager(
            config = PagingConfig(
                pageSize = CharacterRepository.PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = CharacterRepository.PREFETCH_DISTANCE
            ),
            pagingSourceFactory = {
                FakeCharacterPagingSource(cachedBookmarkData)
            }
        ).flow
}