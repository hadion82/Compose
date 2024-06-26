package com.example.home

import android.app.Activity
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.ui.scope.UiScope
import com.example.model.MarvelCharacter
import com.example.navigator.BookmarksNavigator
import com.example.shared.dispatcher.ScopedDispatcher
import com.example.ui.common.CharacterContent
import com.example.ui.common.showSnackBarMessage
import com.example.design.theme.ComposeTheme
import com.example.design.theme.DefaultSurface
import com.example.home.tag.HomeTag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

internal class HomeScopedDispatcher(
    intent: MutableSharedFlow<Intention>,
    scope: CoroutineScope
) :
    ScopedDispatcher<Intention>(intent, scope)

internal interface HomeActivityScope : UiScope<Intention, HomeScopedDispatcher> {
    companion object {
        fun default() = object : HomeActivityScope {
            override val intents: MutableSharedFlow<Intention> =
                MutableStateFlow(Intention.Effect.LoadCharacters)
        }
    }
}

@Composable
internal fun HomeRoute(
    navigator: BookmarksNavigator,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreen(
        ComposeHomeUiState(viewModel.state()),
        ComposeHomePresenter(viewModel.intents, navigator, navController)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeContent(
    mainState: State<Flow<PagingData<MarvelCharacter>>?>,
    presenter: MainPresenter,
    enableLoading: Boolean
) {
    val pagingItems = mainState.value?.collectAsLazyPagingItems() ?: return
    val pullToRefreshState = rememberPullToRefreshState()
    val scaleFraction = if (pullToRefreshState.isRefreshing) 1f else
        LinearOutSlowInEasing.transform(pullToRefreshState.progress).coerceIn(0f, 1f)

    if (pullToRefreshState.isRefreshing) pagingItems.refresh()

    Box(modifier = Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)) {
        CharacterLazyColum(
            pullToRefreshState = pullToRefreshState,
            pagingItems = pagingItems,
            onThumbnailClick = presenter::onThumbnailClick,
            onBookmarkClick = presenter::onBookmarkClick,
            onDescriptionClick = presenter::onDescriptionClick
        )
        if(enableLoading) {
            AppendIndicator(
                loadState = pagingItems.loadState.append,
                showMessage = presenter::showMessage
            )
            RefreshIndicator(
                loadState = pagingItems.loadState.refresh,
                pullToRefreshState = pullToRefreshState,
                modifier = Modifier.align(Alignment.Center),
                showMessage = presenter::showMessage
            )
        }
        PullToRefreshContainer(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .graphicsLayer(scaleX = scaleFraction, scaleY = scaleFraction),
            state = pullToRefreshState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharacterLazyColum(
    pullToRefreshState: PullToRefreshState,
    pagingItems: LazyPagingItems<MarvelCharacter>,
    onThumbnailClick: (String?) -> Unit,
    onBookmarkClick: (id: Int, marked: Boolean) -> Unit,
    onDescriptionClick: (id: Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp, 8.dp),
        modifier = Modifier
            .fillMaxSize()
            .testTag(
                HomeTag.Content.CHARACTER_LAZY_COLUMN
            )
    ) {
        if (pullToRefreshState.isRefreshing.not()) {
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey { it.id }
            ) { position ->
                pagingItems[position]?.let { characterItem ->
                    CharacterContent(
                        characterItem,
                        onThumbnailClick = onThumbnailClick,
                        onBookmarkClick = onBookmarkClick,
                        onDescriptionClick = onDescriptionClick
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
internal fun AppendIndicator(
    loadState: LoadState,
    showMessage: (String) -> Unit
) {
    when (loadState) {
        is LoadState.Loading -> {/*Nothing*/
        }

        is LoadState.NotLoading -> { /*Nothing*/
        }

        is LoadState.Error -> loadState.error.message?.let { showMessage(it) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RefreshIndicator(
    loadState: LoadState,
    pullToRefreshState: PullToRefreshState,
    modifier: Modifier,
    showMessage: (String) -> Unit
) {
    when (loadState) {
        is LoadState.Loading -> {
            if (pullToRefreshState.isRefreshing.not()) {
                CircularProgressIndicator(
                    color = Color.Black,
                    modifier = modifier
                )
            }
        }

        is LoadState.Error -> loadState.error.message?.let { showMessage(it) }
        is LoadState.NotLoading -> pullToRefreshState.endRefresh()
    }
}

@Composable
internal fun SnackBarMessage(
    messageState: State<Action.Message?>,
    snackBarHostState: SnackbarHostState
) {
    snackBarHostState.showSnackBarMessage(messageState) { stateValue ->
        when (stateValue) {
            is Action.Message.FailedToLoadData -> stringResource(R.string.failed_to_load_data)
            is Action.Message.FailedToBookmark -> stringResource(R.string.failed_to_bookmark)
            is Action.Message.FailedToDeleteBookmark -> stringResource(R.string.failed_to_delete_bookmark)
            is Action.Message.SuccessToSaveImage -> stringResource(R.string.image_saved_successfully)
            is Action.Message.FailedToSaveImage -> stringResource(R.string.failed_to_save_image)
            is Action.Message.ShowMessage -> stateValue.message
        }
    }
}

@Composable
internal fun TitleBar(onTitleClick: (Activity) -> Unit) {
    Box(Modifier.fillMaxWidth()) {
        val activity = LocalContext.current as? Activity ?: return
        Text(
            text = stringResource(id = R.string.marvel_characters),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(4.dp, 2.dp)
        )
        Button(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(4.dp, 2.dp)
                .testTag(HomeTag.Bar.OPEN_BOOKMARK),
            onClick = { onTitleClick(activity) }
        ) {
            Text(text = stringResource(id = R.string.label_bookmark))
        }
    }
}

@Composable
internal fun HomeScreen(
    uiState: HomeComposableUiState, presenter: MainPresenter, enableLoading: Boolean = true
) {
    ComposeTheme {
        DefaultSurface {
            Scaffold(snackbarHost = {
                SnackbarHost(uiState.snackBarHostState)
            }) { paddingValues ->
                Column(
                    Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                        .padding(paddingValues)
                ) {
                    TitleBar(presenter::onTitleClick)
                    HomeContent(uiState.pagingData, presenter, enableLoading)
                    SnackBarMessage(uiState.message, uiState.snackBarHostState)
                }
            }
        }
    }
}