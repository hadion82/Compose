package com.example.data.datasource.local

import androidx.paging.PagingSource
import com.example.database.model.BookmarkEntity
import com.example.database.dao.BookmarkDao
import com.example.database.model.CharacterEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class BookmarkLocalDataSourceImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookmarkLocalDataSource {

    override suspend fun insert(vararg values: BookmarkEntity) =
        bookmarkDao.insertOrReplace(*values)

    override suspend fun update(values: List<CharacterEntity>) =
        bookmarkDao.update(values)

    override suspend fun getBookmarksById(ids: List<Int>): List<BookmarkEntity> =
        bookmarkDao.getBookMarksById(ids)

    override suspend fun getIds(ids: List<Int>): List<Int> =
        bookmarkDao.getIds(ids)

    override fun loadIds(): Flow<List<Int>> = bookmarkDao.loadIds()

    override fun loadPagingMarked(): PagingSource<Int, BookmarkEntity> =
        bookmarkDao.loadPagingSource()

    override fun loadPagingAll(): PagingSource<Int, BookmarkEntity> =
        bookmarkDao.loadPagingAll()

    override suspend fun addBookmark(id: Int) =
        bookmarkDao.updateBookmark(id, true)

    override suspend fun removeBookmark(id: Int) =
        bookmarkDao.updateBookmark(id, false)
}