package com.example.database

import com.example.database.model.CharacterEntity
import kotlin.random.Random

object FakeCharacterEntityData {
    fun oneEntity() = createEntities(1).first()

    fun createEntities(count: Int): List<CharacterEntity> {
        val list = mutableListOf<CharacterEntity>()
        for (index in 1..count) {
            list.add(createEntity(index))
        }
        return list
    }

    private fun createEntity(index: Int) =
        Random(index).let { random ->
            CharacterEntity(
                id = index,
                name = "name$index",
                description = "description$index",
                thumbnail = "thumbnail$index",
                urlCount = random.nextInt(),
                comicCount = random.nextInt(),
                storyCount = random.nextInt(),
                eventCount = random.nextInt(),
                seriesCount = random.nextInt(),
                mark = false
            )
        }
}