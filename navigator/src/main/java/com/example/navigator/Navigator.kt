package com.example.navigator

import android.app.Activity
import android.content.Intent

interface Navigator {
    fun navigate(
        activity: Activity,
        intentBuilder: Intent.() -> Intent = { this },
        withFinish: Boolean = false
    )
}

interface BookmarksNavigator : Navigator