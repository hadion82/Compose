package com.example.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.data.entity.BookmarkEntity
import com.example.data.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

@Dao
internal interface BookmarkDao : BaseDao<BookmarkEntity> {

    @Query("SELECT * FROM bookmark WHERE id IN (:ids)")
    suspend fun getBookMarksById(ids: List<Int>): List<BookmarkEntity>

    @Update(entity = BookmarkEntity::class)
    suspend fun update(values: List<MarvelCharacter>)

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