package com.example.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterItem(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "thumbnail")
    val thumbnail: String?,

    @ColumnInfo(name = "url_count")
    val urlCount: Int,

    @ColumnInfo(name = "comic_count")
    val comicCount: Int,

    @ColumnInfo(name = "story_count")
    val storyCount: Int,

    @ColumnInfo(name = "event_count")
    val eventCount: Int,

    @ColumnInfo(name = "series_count")
    val seriesCount: Int,

    @ColumnInfo(name = "mark", defaultValue = "false")
    val mark: Boolean
)