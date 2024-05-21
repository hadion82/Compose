package com.example.data.mapper

import com.example.database.model.BookmarkEntity
import com.example.network.model.Character
import com.example.shared.mapper.Mapper
import javax.inject.Inject

class BookMarkEntityMapper @Inject constructor(): Mapper<Character, BookmarkEntity> {
    override fun invoke(data: Character): BookmarkEntity =
        BookmarkEntity(
            id = data.id ?: -1,
            name = data.name,
            description = data.description,
            thumbnail = data.thumbnail?.let {
                "${it.path?.toHttpsFormat()}.${it.extension}"
            },
            urlCount = data.urls?.size ?: 0,
            comicCount = data.comics?.items?.size ?: 0,
            storyCount = data.stories?.items?.size ?: 0,
            eventCount = data.events?.items?.size ?: 0,
            seriesCount = data.series?.items?.size ?: 0,
            mark = false
        )

    private fun String.toHttpsFormat() =
        if (this.contains("https")) this else
            replace("http", "https")
}