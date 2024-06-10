package com.example.domain.mapper

import com.example.model.CharacterData
import com.example.model.MarvelCharacter
import com.example.shared.mapper.Mapper
import javax.inject.Inject

class MarvelToDataMapper @Inject constructor() : Mapper<MarvelCharacter, CharacterData> {
    override fun invoke(data: MarvelCharacter): CharacterData =
        CharacterData(
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