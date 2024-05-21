package com.example.domain.mapper

import com.example.data.model.BookmarkData
import com.example.model.MarvelCharacter
import com.example.shared.mapper.Mapper
import javax.inject.Inject

class MarvelCharacterMapper @Inject constructor() : Mapper<BookmarkData, MarvelCharacter> {
    override fun invoke(data: BookmarkData): MarvelCharacter =
        MarvelCharacter(
            id = data.id,
            name = data.name,
            description = data.description,
            thumbnail = data.thumbnail,
            urlCount = data.urlCount,
            comicCount = data.comicCount,
            storyCount = data.storyCount,
            eventCount = data.eventCount,
            seriesCount = data.seriesCount,
            mark = data.mark
        )
}