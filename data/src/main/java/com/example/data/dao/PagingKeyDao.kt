package com.example.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.entity.PagingkKeyEntity

@Dao
internal interface PagingKeyDao : BaseDao<PagingkKeyEntity> {

    @Query("SELECT * FROM paging_key where type = :value")
    suspend fun getKey(value: String): PagingkKeyEntity?
}