package com.example.database.test

import android.content.Context
import androidx.room.Room
import com.example.database.LocalDataBase
import com.example.database.dao.CharacterDao
import org.jetbrains.annotations.TestOnly

@TestOnly
interface MemoryLocalDatabase {
    fun characterDao(): CharacterDao
    fun clearAllTables()

    companion object {
        fun create(context: Context) =
            MemoryLocalDatabaseImpl(
                Room.inMemoryDatabaseBuilder(
                    context, LocalDataBase::class.java
                ).build()
            )
    }
}