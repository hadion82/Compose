package com.example.test.data.cached

import com.example.model.CharacterData
import com.example.test.data.constant.TestConstant

class CachedCharacterData(
    private var cachedBookmarkData: CharacterData = CharacterData(
        id = TestConstant.TEST_ID,
        name = "name1",
        description = "description1",
        thumbnail = null,
        urlCount = 0,
        comicCount = 0,
        storyCount = 0,
        eventCount = 0,
        seriesCount = 0,
        mark = false
    )
) {
    fun setBookmark(value: Boolean) {
        cachedBookmarkData = cachedBookmarkData.copy(mark = value)
    }

    fun getData() = cachedBookmarkData
}