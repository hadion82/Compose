package com.example.database.test

import com.example.database.LocalDataBase
import com.example.database.dao.CharacterDao
import org.jetbrains.annotations.TestOnly

@TestOnly
class MemoryLocalDatabaseImpl internal constructor(
    private val database: LocalDataBase
): MemoryLocalDatabase{
    override fun characterDao(): CharacterDao = database.bookmarkDao()
    override fun clearAllTables() = database.clearAllTables()
}