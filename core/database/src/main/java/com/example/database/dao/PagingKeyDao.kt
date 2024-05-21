package com.example.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.database.model.PagingKeyEntity

@Dao
interface PagingKeyDao : BaseDao<PagingKeyEntity> {

    @Query("SELECT * FROM paging_key where type = :value")
    suspend fun getKey(value: String): PagingKeyEntity?
}