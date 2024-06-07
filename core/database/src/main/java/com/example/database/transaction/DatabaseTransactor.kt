package com.example.database.transaction

interface DatabaseTransactor {
    suspend fun <R> transaction(block: suspend () -> R)
}