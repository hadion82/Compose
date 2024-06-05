package com.example.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.database.model.CharacterEntity
import com.example.database.model.CharacterUpdatingEntity
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.TestOnly

@Dao
interface CharacterDao : BaseDao<CharacterEntity> {

    @Query("SELECT * FROM characters WHERE id IN (:ids)")
    suspend fun getBookMarksById(ids: List<Int>): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getBookMarksById(id: Int): CharacterEntity?

    @Update(entity = CharacterEntity::class)
    suspend fun update(values: List<CharacterUpdatingEntity>)

    @Query("SELECT id FROM characters WHERE id IN (:ids)")
    suspend fun getIds(ids: List<Int>): List<Int>

    @TestOnly
    @Query("SELECT * FROM characters")
    suspend fun getAll(): List<CharacterEntity>

    @Query("SELECT id FROM characters")
    fun loadIds(): Flow<List<Int>>

    @Query("SELECT * FROM characters WHERE mark = 1 ORDER BY name")
    fun loadPagingSource(): PagingSource<Int, CharacterEntity>

    @Query("SELECT * FROM characters ORDER BY name")
    fun loadPagingAll(): PagingSource<Int, CharacterEntity>

    @Query("UPDATE characters SET mark = :mark WHERE id = :id")
    suspend fun updateBookmark(id: Int, mark: Boolean)
}