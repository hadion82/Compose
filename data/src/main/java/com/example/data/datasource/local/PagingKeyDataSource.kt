package com.example.data.datasource.local

import com.example.data.entity.PagingkKeyEntity

interface PagingKeyDataSource {

    suspend fun insertKey(
        value: PagingkKeyEntity
    )

    suspend fun getKey(value: String): PagingkKeyEntity?
}