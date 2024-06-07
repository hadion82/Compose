package com.example.database.transaction

import androidx.room.RoomDatabase
import androidx.room.withTransaction
import javax.inject.Inject

class LocalDatabaseTransactor @Inject constructor(
    private val localDataBase: RoomDatabase
): DatabaseTransactor {
    override suspend fun <R> transaction(block: suspend () -> R) {
        localDataBase.withTransaction(block)
    }
}