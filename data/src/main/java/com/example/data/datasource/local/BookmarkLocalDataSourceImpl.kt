package com.example.data.datasource.local

import androidx.paging.PagingSource
import com.example.data.dao.BookmarkDao
import com.example.data.database.InternalDatabase
import com.example.data.entity.BookmarkEntity
import com.example.data.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class BookmarkLocalDataSourceImpl @Inject constructor(
    database: InternalDatabase
) : BookmarkLocalDataSource {

    private val dao: BookmarkDao = database.getBookmarkDao()

    override suspend fun insert(vararg values: BookmarkEntity) =
        dao.insertOrReplace(*values)

    override suspend fun update(values: List<MarvelCharacter>) =
        dao.update(values)

    override suspend fun getBookmarksById(ids: List<Int>): List<BookmarkEntity> =
        dao.getBookMarksById(ids)

    override suspend fun getIds(ids: List<Int>): List<Int> =
        dao.getIds(ids)

    override fun loadIds(): Flow<List<Int>> = dao.loadIds()

    override fun loadPagingMakred(): PagingSource<Int, BookmarkEntity> =
        dao.loadPagingSource()

    override fun loadPagingAll(): PagingSource<Int, BookmarkEntity> =
        dao.loadPagingAll()

    override suspend fun addBookmark(id: Int) = dao.updateBookmark(id, true)

    override suspend fun removeBookmark(id: Int) = dao.updateBookmark(id, false)
}