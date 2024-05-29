package com.example.bookmarks

import android.app.Activity
import android.content.Intent
import com.example.navigator.BookmarksNavigator
import com.example.startActivityWithAnimation
import javax.inject.Inject

internal class BookmarkNavigatorImpl @Inject constructor(): BookmarksNavigator {
    override fun navigate(
        activity: Activity,
        intentBuilder: Intent.() -> Intent,
        withFinish: Boolean
    ) {
        activity.startActivityWithAnimation<BookmarkActivity>(
            intentBuilder = intentBuilder,
            withFinish = withFinish
        )
    }
}
