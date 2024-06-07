package com.example.data.datasource.local

import androidx.paging.PagingSource
import com.example.database.dao.CharacterDao
import com.example.database.model.CharacterEntity
import com.example.database.model.CharacterUpdatingEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class CharacterLocalDataSourceImpl @Inject constructor(
    private val characterDao: CharacterDao
) : CharacterLocalDataSource {
    override suspend fun insert(vararg values: CharacterEntity) =
        characterDao.insertOrReplace(*values)

    override suspend fun update(values: List<CharacterUpdatingEntity>) =
        characterDao.update(values)

    override suspend fun getCharactersById(ids: List<Int>): List<CharacterEntity> =
        characterDao.getBookMarksById(ids)

    override suspend fun getCharactersById(id: Int): CharacterEntity? =
        characterDao.getBookMarksById(id)

    override suspend fun getIds(ids: List<Int>): List<Int> =
        characterDao.getIds(ids)

    override fun loadIds(): Flow<List<Int>> = characterDao.loadIds()

    override fun loadPagingMarked(): PagingSource<Int, CharacterEntity> =
        characterDao.loadPagingSource()

    override fun loadPagingAll(): PagingSource<Int, CharacterEntity> =
        characterDao.loadPagingAll()

    override suspend fun addBookmark(id: Int) =
        characterDao.updateBookmark(id, true)

    override suspend fun removeBookmark(id: Int) =
        characterDao.updateBookmark(id, false)
}