package com.example.domain.mapper

import com.example.data.entity.BookmarkEntity
import com.example.data.model.CharacterItem
import com.example.shared.mapper.Mapper
import javax.inject.Inject

class CharacterItemMapper @Inject constructor() : Mapper<BookmarkEntity, CharacterItem> {
    override fun invoke(data: BookmarkEntity): CharacterItem =
        CharacterItem(
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