package com.example.testing.database

import android.content.Context
import com.example.database.test.MemoryLocalDatabase

class FakeLocalDatabase (
    memoryLocalDatabase: MemoryLocalDatabase
) : MemoryLocalDatabase by memoryLocalDatabase {

    companion object {
        fun create(context: Context) =
            FakeLocalDatabase(
                MemoryLocalDatabase.create(context)
            )
    }
}