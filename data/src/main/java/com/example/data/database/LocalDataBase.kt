package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.dao.BookmarkDao
import com.example.data.dao.PagingKeyDao
import com.example.data.entity.BookmarkEntity
import com.example.data.entity.PagingkKeyEntity

@Database(
    entities = [BookmarkEntity::class, PagingkKeyEntity::class],
    version = 1
)
abstract class LocalDataBase : RoomDatabase() {

    internal abstract fun bookmarkDao(): BookmarkDao

    internal abstract fun pagingKeyDao(): PagingKeyDao

    companion object {

        @Volatile private var INSTANCE: LocalDataBase? = null

        fun getInstance(context: Context): LocalDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: databaseBuilder(context)
                    .build().also {
                        INSTANCE = it
                    }
            }

        private fun databaseBuilder(context: Context) =
            Room.databaseBuilder(
                context,
                LocalDataBase::class.java,
                "database.db"
            )
    }
}