package com.example.data.repository

import androidx.paging.PagingData
import com.example.data.model.CharacterData
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    @Throws(Exception::class)
    suspend fun addBookmark(id: Int)

    @Throws(Exception::class)
    suspend fun removeBookmark(id: Int)

    fun loadBookmarkIds(): Flow<List<Int>>

    fun loadPagingBookmarks(): Flow<PagingData<CharacterData>>
}