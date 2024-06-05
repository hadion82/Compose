package com.example.testing.data

import com.example.network.model.Character
import com.example.network.model.CharacterDataContainer
import com.example.network.model.CharacterDataWrapper
import kotlin.random.Random

object FakeMarvelCharacterData {

    fun createCharacterData(offset: Int, limit: Int) =
        CharacterDataWrapper(
            code = null,
            status = null,
            copyright = null,
            attributionText = null,
            attributionHTML = null,
            data = createCharacterDataContainer(offset, limit),
            etag = null
        )

    private fun createCharacterDataContainer(offset: Int, limit: Int) =
        CharacterDataContainer(
            offset = offset,
            limit = limit,
            total = limit * 2,
            count = limit,
            results = createCharacters(offset, limit)
        )

    private fun createCharacters(offset: Int, count: Int): List<Character> {
        val list = mutableListOf<Character>()
        for (index in offset until offset + count) {
            list.add(createCharacter(index))
        }
        return list
    }

    private fun createCharacter(index: Int) =
        Character(
            id = index,
            name = "name$index",
            description = "description$index",
            modified = "modification time : ${System.currentTimeMillis()}",
            resourceURI = null,
            urls = null,
            thumbnail = null,
            comics = null,
            stories = null,
            events = null,
            series = null
        )
}