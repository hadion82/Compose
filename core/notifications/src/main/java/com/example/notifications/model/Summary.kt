package com.example.notifications.model

import androidx.annotation.DrawableRes

data class Summary(
    val id: Int,
    val title: String,
    @DrawableRes val smallIcon: Int,
)