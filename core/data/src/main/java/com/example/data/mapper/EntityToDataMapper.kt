package com.example.data.mapper

import com.example.model.CharacterData
import com.example.database.model.CharacterEntity
import com.example.shared.mapper.Mapper
import javax.inject.Inject

class EntityToDataMapper @Inject constructor(): Mapper<CharacterEntity, com.example.model.CharacterData> {
    override fun invoke(data: CharacterEntity): com.example.model.CharacterData =
        com.example.model.CharacterData(
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