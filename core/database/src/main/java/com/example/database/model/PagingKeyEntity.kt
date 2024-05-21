package com.example.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "paging_key")
data class PagingKeyEntity(
    @PrimaryKey
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "key")
    val key: Int,
)