package com.example.shared.extensions

fun <T, R> Iterable<T>.mapBy(mapper: (T) -> R) =
    this.map(mapper)