package com.example.data.mapper

import com.example.data.model.CharacterData
import com.example.data.model.MarvelCharacter
import com.example.shared.mapper.Mapper
import javax.inject.Inject

class PagingCharacterMapper @Inject constructor(): Mapper<CharacterData, MarvelCharacter> {
    override fun invoke(data: CharacterData): MarvelCharacter =
        MarvelCharacter(
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
            seriesCount = data.series?.items?.size ?: 0
        )

    private fun String.toHttpsFormat() =
        if (this.contains("https")) this else
            replace("http", "https")
}