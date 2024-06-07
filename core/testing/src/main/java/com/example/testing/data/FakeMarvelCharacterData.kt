package com.example.testing.data

import com.example.network.model.Character
import com.example.network.model.CharacterDataContainer
import com.example.network.model.CharacterDataWrapper
import com.example.network.model.ComicList
import com.example.network.model.ComicSummary
import com.example.network.model.EventList
import com.example.network.model.EventSummary
import com.example.network.model.Image
import com.example.network.model.SeriesList
import com.example.network.model.SeriesSummary
import com.example.network.model.StoryList
import com.example.network.model.StorySummary
import com.example.network.model.Url

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

    fun createNullCharacterData(offset: Int, limit: Int) =
        CharacterDataWrapper(
            code = null,
            status = null,
            copyright = null,
            attributionText = null,
            attributionHTML = null,
            data = null,
            etag = null
        )

    fun createTotalNullCharacterData(offset: Int, limit: Int) =
        CharacterDataWrapper(
            code = null,
            status = null,
            copyright = null,
            attributionText = null,
            attributionHTML = null,
            data = createCharacterDataContainer(offset, limit, null),
            etag = null
        )

    fun createLimitedTotalCharacterData(offset: Int, limit: Int) =
        CharacterDataWrapper(
            code = null,
            status = null,
            copyright = null,
            attributionText = null,
            attributionHTML = null,
            data = createCharacterDataContainer(offset, limit, limit * 2),
            etag = null
        )

    private fun createCharacterDataContainer(offset: Int, limit: Int, total: Int? = limit * 2) =
        CharacterDataContainer(
            offset = offset,
            limit = limit,
            total = total,
            count = limit,
            results = createCharacters(offset, limit)
        )

    private fun createCharacters(offset: Int, count: Int): List<Character> {
        val list = mutableListOf<Character>()
        list.add(createCharacter(offset))
        for (index in offset + 1 until offset + count) {
            list.add(
                createFullContentCharacter(index)
            )
        }
        return list
    }

    fun createCharacter(index: Int?) =
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

    private fun createFullContentCharacter(index: Int) =
        Character(
            id = index,
            name = "name$index",
            description = "description$index",
            modified = "modification time : ${System.currentTimeMillis()}",
            resourceURI = "resourceURI",
            urls = listOf(Url("link", "link$index")),
            thumbnail = Image(createUrl(index), "jpg"),
            comics = ComicList(
                available = index,
                returned = index,
                collectionURI = "collectionURL",
                items = createSummary(index) {
                    ComicSummary("resourceURL$index", "name$index")
                }
            ),
            stories = StoryList(
                available = index,
                returned = index,
                collectionURI = "collectionURL",
                items = createSummary(index) {
                    StorySummary("resourceURL$index", "name$index", "type$index")
                }
            ),
            events = EventList(
                available = index,
                returned = index,
                collectionURI = "collectionURL",
                items = createSummary(index) {
                    EventSummary("resourceURL$index", "name$index")
                }
            ),
            series = SeriesList(
                available = index,
                returned = index,
                collectionURI = "collectionURL",
                items = createSummary(index) {
                    SeriesSummary("resourceURL$index", "name$index")
                }
            )
        )

    private fun createUrl(index: Int) =
        if(index == 1) null else createContent(index, { "http://$index.com" }, { "https://$index.com" })

    private inline fun <T : Any> createSummary(index: Int, crossinline create: () -> T) =
        createContent(index, {
            listOf(create())
        }) { null }

    private inline fun <A : Any, B : A> createContent(
        index: Int,
        crossinline createA: () -> A,
        crossinline createB: () -> B? = { null }
    ) =
        if (isPrime(index)) createA() else createB()

    private fun isPrime(index: Int) = (index and 1) == 1
}