package com.example.notifications.model

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat

class Resource(
    val id: Int,
    val title: String,
    val content: String,
    @DrawableRes val smallIcon: Int,
    val group: String,
    val autoCancel: Boolean = false,
    val intent: NotificationIntent,
    val priority: Int = NotificationCompat.PRIORITY_DEFAULT
)

class NotificationIntent(
    val uri: Uri,
    val clazz: Class<*>,
)