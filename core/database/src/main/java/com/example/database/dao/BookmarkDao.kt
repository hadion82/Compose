package com.example.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.database.model.BookmarkEntity
import com.example.database.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao : BaseDao<BookmarkEntity> {

    @Query("SELECT * FROM bookmark WHERE id IN (:ids)")
    suspend fun getBookMarksById(ids: List<Int>): List<BookmarkEntity>

    @Update(entity = BookmarkEntity::class)
    suspend fun update(values: List<CharacterEntity>)

    @Query("SELECT id FROM bookmark WHERE id IN (:ids)")
    suspend fun getIds(ids: List<Int>): List<Int>

    @Query("SELECT id FROM bookmark")
    fun loadIds(): Flow<List<Int>>

    @Query("SELECT * FROM bookmark WHERE mark = 1 ORDER BY name")
    fun loadPagingSource(): PagingSource<Int, BookmarkEntity>

    @Query("SELECT * FROM bookmark ORDER BY name")
    fun loadPagingAll(): PagingSource<Int, BookmarkEntity>

    @Query("UPDATE bookmark SET mark = :mark WHERE id = :id")
    suspend fun updateBookmark(id: Int, mark: Boolean)
}