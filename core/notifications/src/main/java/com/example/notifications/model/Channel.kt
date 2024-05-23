package com.example.notifications.model

import android.app.NotificationManager

data class Channel(
    val id: String,
    val name: CharSequence,
    val importance: Importance,
    val description: String = ""
)

sealed class Importance(val value: Int) {
    data object None: Importance(NotificationManager.IMPORTANCE_HIGH)
    data object Min: Importance(NotificationManager.IMPORTANCE_MIN)
    data object Low: Importance(NotificationManager.IMPORTANCE_LOW)
    data object High: Importance(NotificationManager.IMPORTANCE_HIGH)
    data object Max: Importance(NotificationManager.IMPORTANCE_MAX)
}