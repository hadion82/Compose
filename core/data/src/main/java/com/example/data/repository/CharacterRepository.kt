package com.example.data.repository

import androidx.paging.PagingData
import com.example.data.model.BookmarkData
import com.example.database.model.BookmarkEntity
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    @Throws(Exception::class)
    fun loadPagingData(): Flow<PagingData<BookmarkData>>

    suspend fun getCharacterById(id: Int): BookmarkData?
}