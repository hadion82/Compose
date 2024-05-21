package com.example.data.datasource.local

import com.example.database.dao.PagingKeyDao
import com.example.database.model.PagingKeyEntity
import javax.inject.Inject

internal class PagingKeyDataSourceImpl @Inject constructor(
    private val pagingKeyDao: PagingKeyDao
) : PagingKeyDataSource {

    override suspend fun insertKey(value: PagingKeyEntity) =
        pagingKeyDao.insertOrReplace(value)

    override suspend fun getKey(value: String) =
        pagingKeyDao.getKey(value)
}