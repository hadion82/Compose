package com.example.data.datasource.local

import androidx.annotation.IntRange
import androidx.paging.PagingSource
import com.example.database.model.CharacterEntity
import com.example.database.model.CharacterUpdatingEntity
import kotlinx.coroutines.flow.Flow

internal interface CharacterLocalDataSource {

    suspend fun insert(
        vararg values: CharacterEntity
    )

    suspend fun update(
        values: List<CharacterUpdatingEntity>
    )

    suspend fun getCharactersById(ids: List<Int>): List<CharacterEntity>
    suspend fun getCharactersById(id: Int): CharacterEntity?

    suspend fun getIds(ids: List<Int>): List<Int>

    fun loadIds(): Flow<List<Int>>

    fun loadPagingMarked(): PagingSource<Int, CharacterEntity>

    fun loadPagingAll(): PagingSource<Int, CharacterEntity>

    suspend fun addBookmark(
        @IntRange(from = 0) id: Int
    )

    suspend fun removeBookmark(
        @IntRange(from = 0) id: Int
    )
}