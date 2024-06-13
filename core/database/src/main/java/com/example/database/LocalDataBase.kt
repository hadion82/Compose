package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.CharacterDao
import com.example.database.model.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1
)
internal abstract class LocalDataBase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}