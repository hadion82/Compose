package com.example.data.datasource.local

import com.example.database.model.PagingKeyEntity


internal interface PagingKeyDataSource {

    suspend fun insertKey(
        value: PagingKeyEntity
    )

    suspend fun getKey(value: String): PagingKeyEntity?
}