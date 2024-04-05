package com.example.data.database

import com.example.data.dao.BookmarkDao
import com.example.data.dao.PagingKeyDao
import javax.inject.Inject

internal class InternalDatabase @Inject constructor(
    private val database: LocalDataBase
){
    fun getBookmarkDao(): BookmarkDao = database.bookmarkDao()

    fun getPagingKeyDao(): PagingKeyDao = database.pagingKeyDao()
}