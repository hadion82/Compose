package com.example.domain.mapper

import com.example.data.model.CharacterData
import com.example.model.MarvelCharacter
import com.example.shared.mapper.Mapper
import javax.inject.Inject

class MarvelCharacterMapper @Inject constructor() : Mapper<CharacterData, MarvelCharacter> {
    override fun invoke(data: CharacterData): MarvelCharacter =
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