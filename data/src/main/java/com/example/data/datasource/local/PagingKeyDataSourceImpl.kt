package com.example.data.datasource.local

import com.example.data.dao.PagingKeyDao
import com.example.data.database.InternalDatabase
import com.example.data.entity.PagingkKeyEntity
import javax.inject.Inject

internal class PagingKeyDataSourceImpl @Inject constructor(
    database: InternalDatabase
) : PagingKeyDataSource {

    private val dao: PagingKeyDao = database.getPagingKeyDao()

    override suspend fun insertKey(value: PagingkKeyEntity) =
        dao.insertOrReplace(value)

    override suspend fun getKey(value: String) =
        dao.getKey(value)
}