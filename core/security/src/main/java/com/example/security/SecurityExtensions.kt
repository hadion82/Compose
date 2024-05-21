package com.example.security

import java.math.BigInteger
import java.security.MessageDigest

fun String.hash(algorithm: String): String? = kotlin.runCatching {
    MessageDigest.getInstance(algorithm).let { md ->
        md.update(this.toByteArray())
        BigInteger(1, md.digest()).toString(16)
            .padStart(32, '0')
    }
}.getOrNull()