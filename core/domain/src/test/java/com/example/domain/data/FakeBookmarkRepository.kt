package com.example.domain.data

import androidx.paging.PagingData
import com.example.data.model.CharacterData
import com.example.data.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class FakeBookmarkRepository: BookmarkRepository {
    override suspend fun addBookmark(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun removeBookmark(id: Int) {
        TODO("Not yet implemented")
    }

    override fun loadBookmarkIds(): Flow<List<Int>> {
        TODO("Not yet implemented")
    }

    override fun loadPagingBookmarks(): Flow<PagingData<CharacterData>> {
        TODO("Not yet implemented")
    }
}