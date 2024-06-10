package com.example.data.mapper

import com.example.model.CharacterData
import com.example.database.model.CharacterUpdatingEntity
import com.example.shared.mapper.Mapper
import javax.inject.Inject

class DataToUpdatingEntityMapper @Inject constructor(): Mapper<com.example.model.CharacterData, CharacterUpdatingEntity> {
    override fun invoke(data: com.example.model.CharacterData): CharacterUpdatingEntity =
        CharacterUpdatingEntity(
            id = data.id,
            name = data.name,
            description = data.description,
            thumbnail = data.thumbnail,
            urlCount = data.urlCount,
            comicCount = data.comicCount,
            storyCount = data.storyCount,
            eventCount = data.eventCount,
            seriesCount = data.seriesCount
        )
}