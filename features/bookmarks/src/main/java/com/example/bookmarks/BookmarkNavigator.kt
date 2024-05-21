package com.example.bookmarks

import android.app.Activity
import android.content.Intent
import android.os.Build
import com.example.navigator.BookmarksNavigator
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

inline fun <reified T : Activity> Activity.startActivityWithAnimation(
    intentBuilder: Intent.() -> Intent = { this },
    withFinish: Boolean = true,
) {
    startActivity(Intent(this, T::class.java).intentBuilder())
    if (Build.VERSION.SDK_INT >= 34) {
        overrideActivityTransition(
            Activity.OVERRIDE_TRANSITION_OPEN,
            android.R.anim.fade_in,
            android.R.anim.fade_out,
        )
    } else {
        @Suppress("DEPRECATION")
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
    if (withFinish) finish()
}