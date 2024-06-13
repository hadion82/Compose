package com.example.database

import android.content.Context
import androidx.room.Room
import com.example.database.dao.CharacterDao
import org.jetbrains.annotations.TestOnly

@TestOnly
open class MemoryDatabase internal constructor(
    private val delegate: LocalDataBase
) {
    fun characterDao(): CharacterDao = delegate.characterDao()

    fun close() = delegate.close()
    companion object {
        fun create(context: Context) =
            MemoryDatabase(
                Room.inMemoryDatabaseBuilder(
                    context, LocalDataBase::class.java
                ).build()
            )
    }
}