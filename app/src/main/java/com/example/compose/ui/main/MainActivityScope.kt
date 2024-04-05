package com.example.compose.ui.main

import android.Manifest
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.compose.R
import com.example.compose.ui.bookmark.BookmarkActivity
import com.example.compose.ui.common.CharacterContent
import com.example.compose.ui.scope.ComposeScope
import com.example.compose.ui.scope.UiScope
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.DefaultSurface
import com.example.data.model.CharacterItem
import com.example.shared.dispatcher.ScopedDispatcher
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class MainScopedDispatcher(
    intent: MutableSharedFlow<Intention>,
    scope: CoroutineScope
) :
    ScopedDispatcher<Intention>(intent, scope)

interface MainActivityScope : UiScope<Intention, MainScopedDispatcher> {
    companion object {
        fun default() = object : MainActivityScope {
            override val intents: MutableSharedFlow<Intention> =
                MutableStateFlow(Intention.Effect.LoadCharacters)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScopedDispatcher.MainContents(mainState: State<Flow<PagingData<CharacterItem>>?>) {
    val pagingData by mainState
    val pagingItems = pagingData?.collectAsLazyPagingItems() ?: return
    val pullToRefreshState = rememberPullToRefreshState()
    val scaleFraction = if (pullToRefreshState.isRefreshing) 1f else
        LinearOutSlowInEasing.transform(pullToRefreshState.progress).coerceIn(0f, 1f)

    if(pullToRefreshState.isRefreshing) pagingItems.refresh()

    Box(modifier = Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)) {
        CharacterLazyColum(
            pullToRefreshState = pullToRefreshState,
            pagingItems = pagingItems
        )
        AppendIndicator(loadState = pagingItems.loadState.append)
        RefreshIndicator(
            loadState = pagingItems.loadState.refresh,
            pullToRefreshState = pullToRefreshState,
            modifier = Modifier.align(Alignment.Center)
        )
        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter)
                .graphicsLayer(scaleX = scaleFraction, scaleY = scaleFraction),
            state = pullToRefreshState
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScopedDispatcher.CharacterLazyColum(
    pullToRefreshState: PullToRefreshState,
    pagingItems: LazyPagingItems<CharacterItem>
) {
    val permissionState = PermissionState(
        this,
        rememberPermissionState(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    )
    LazyColumn(
        contentPadding = PaddingValues(16.dp, 8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        if(pullToRefreshState.isRefreshing.not()) {
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey { it.id }
            ) { position ->
                pagingItems[position]?.let { characterItem ->
                    CharacterContent(
                        characterItem,
                        onThumbnailClick = permissionState::onThumbnailClick,
                        onBookmarkClick = permissionState::onBookmarkClick
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun AppendIndicator(loadState: LoadState) {
    when (loadState) {
        is LoadState.Loading -> {/*Nothing*/ }
        is LoadState.NotLoading -> {
            if (loadState.endOfPaginationReached) {
                ShowToast(stringResource(id = R.string.end_of_list))
            }
        }
        is LoadState.Error -> ShowToast(loadState.error.message)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefreshIndicator(loadState: LoadState, pullToRefreshState: PullToRefreshState, modifier: Modifier) {
    when (loadState) {
        is LoadState.Loading -> {
            if (pullToRefreshState.isRefreshing.not()) {
                CircularProgressIndicator(
                    color = Color.Black,
                    modifier = modifier
                )
            }
        }
        is LoadState.Error -> ShowToast(loadState.error.message)
        is LoadState.NotLoading -> pullToRefreshState.endRefresh()
    }
}

@Composable
fun ShowToast(message: String?) = message?.let {
    Toast.makeText(
        LocalContext.current,
        it,
        Toast.LENGTH_SHORT
    ).show()
}

@Composable
fun ToastMessage(
    action: State<Action.Message?>
) {
    val context = LocalContext.current
    action.value?.let { message ->
        Toast.makeText(
            context,
            when (message) {
                is Action.Message.FailedToLoadData -> R.string.failed_to_load_data
                is Action.Message.FailedToBookmark -> R.string.failed_to_bookmark
                is Action.Message.FailedToDeleteBookmark -> R.string.failed_to_delete_bookmark
                is Action.Message.SuccessToSaveImage -> R.string.image_saved_successfully
                is Action.Message.FailedToSaveImage -> R.string.failed_to_save_image
            }, Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
fun TitleBar() {
    Box(Modifier.fillMaxWidth()) {
        val context = LocalContext.current
        Text(
            text = stringResource(id = R.string.marvel_characters),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(4.dp, 2.dp)
        )
        Button(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(4.dp, 2.dp),
            onClick = {
                context.startActivity(Intent(context, BookmarkActivity::class.java))
            }
        ) {
            Text(text = stringResource(id = R.string.label_bookmark))
        }
    }
}

@Composable
fun MainActivityScope.MainScreen(
    uiState: UiState
) {
    ComposeTheme {
        DefaultSurface {
            ComposeScope {
                Column(
                    Modifier.fillMaxSize()
                ) {
                    TitleBar()
                    MainContents(uiState.pagingData)
                    ToastMessage(uiState.message)
                }
            }
        }
    }
}