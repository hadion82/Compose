package com.example.data.mapper

import com.example.database.model.BookmarkEntity
import com.example.database.model.MarvelCharacter
import com.example.shared.mapper.Mapper
import javax.inject.Inject

class BookMarkEntityMapper @Inject constructor(): Mapper<MarvelCharacter, BookmarkEntity> {
    override fun invoke(data: MarvelCharacter): BookmarkEntity =
        BookmarkEntity(
            id = data.id,
            name = data.name,
            description = data.description,
            thumbnail = data.thumbnail,
            urlCount = data.urlCount,
            comicCount = data.comicCount,
            storyCount = data.storyCount,
            eventCount = data.eventCount,
            seriesCount = data.seriesCount,
            mark = false
        )
}