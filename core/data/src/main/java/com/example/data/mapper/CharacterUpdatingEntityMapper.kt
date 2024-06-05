package com.example.data.mapper

import com.example.database.model.CharacterUpdatingEntity
import com.example.network.model.Character
import com.example.shared.mapper.Mapper
import javax.inject.Inject

class CharacterUpdatingEntityMapper @Inject constructor(): Mapper<Character, CharacterUpdatingEntity> {
    override fun invoke(data: Character): CharacterUpdatingEntity =
        CharacterUpdatingEntity(
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