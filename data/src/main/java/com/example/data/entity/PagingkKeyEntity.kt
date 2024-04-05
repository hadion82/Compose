package com.example.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "paging_key")
data class PagingkKeyEntity(
    @PrimaryKey
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "key")
    val key: Int,
)