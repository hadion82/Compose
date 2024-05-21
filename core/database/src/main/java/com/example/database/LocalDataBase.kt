package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.BookmarkDao
import com.example.database.dao.PagingKeyDao
import com.example.database.model.BookmarkEntity
import com.example.database.model.PagingKeyEntity

@Database(
    entities = [BookmarkEntity::class, PagingKeyEntity::class],
    version = 1
)
internal abstract class LocalDataBase : RoomDatabase() {
    internal abstract fun bookmarkDao(): BookmarkDao
    internal abstract fun pagingKeyDao(): PagingKeyDao
}