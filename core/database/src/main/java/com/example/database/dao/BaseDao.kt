package com.example.database.dao

import androidx.room.*


@Dao
interface BaseDao<in T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(vararg values: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrReplace(vararg values: T)

    @Delete
    suspend fun delete(vararg values: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(values: List<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrReplace(values: List<T>)

    @Delete
    suspend fun delete(values: List<T>)
}