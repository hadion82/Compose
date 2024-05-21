package com.example.home

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.model.MarvelCharacter
import com.example.navigator.BookmarksNavigator
import com.example.shared.dispatcher.IntentDispatcher
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.flow.MutableSharedFlow

interface MainPresenter {
    fun onTitleClick(activity: Activity)
    fun onBookmarkClick(item: MarvelCharacter)
    fun showMessage(message: String)
    fun onThumbnailClick(url: String?)
}

class MainPresenterImpl @OptIn(ExperimentalPermissionsApi::class) constructor(
    private val dispatcher: HomeScopedDispatcher,
    private val permissionState: PermissionState,
    private val bookmarksNavigator: BookmarksNavigator
) : MainPresenter, IntentDispatcher<Intention> by dispatcher {

    override fun onTitleClick(activity: Activity) {
        bookmarksNavigator.navigate(activity)
    }

    override fun onBookmarkClick(item: MarvelCharacter) {
        dispatch(
            if (item.mark) Intention.Event.AddBookmark(item.id)
            else Intention.Event.RemoveBookmark(item.id)
        )
    }

    override fun showMessage(message: String) {
        dispatch(
            Intention.Event.SnackBarMessage(message)
        )
    }
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onThumbnailClick(url: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ||
            permissionState.status.isGranted
        ) {
            dispatcher.dispatch(Intention.Effect.SaveThumbnail(url))
        } else {
            permissionState.launchPermissionRequest()
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ComposeHomePresenter (
    intents: MutableSharedFlow<Intention>,
    bookmarksNavigator: BookmarksNavigator
): MainPresenter {
    val composeScope = rememberCoroutineScope()
    val dispatcher = HomeScopedDispatcher(intents, composeScope)
    val permissionState = rememberPermissionState(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    return remember {
        MainPresenterImpl(dispatcher, permissionState, bookmarksNavigator)
    }
}