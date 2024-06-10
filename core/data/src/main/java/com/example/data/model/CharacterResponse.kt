package com.example.data.model

import com.example.model.CharacterData


class CharacterResponse(
    val code: Int,
    val offset: Int,
    val limit: Int,
    val total: Int,
    val results: List<CharacterData>
)