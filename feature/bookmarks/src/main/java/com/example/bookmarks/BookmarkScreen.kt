package com.example.bookmarks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.graph.ProfileGraph
import com.example.model.MarvelCharacter
import com.example.ui.common.CharacterContent
import com.example.ui.common.showSnackBarMessage
import com.example.design.theme.ComposeTheme
import com.example.design.theme.DefaultSurface
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

@Composable
internal fun BookmarkRoute(navHostController: NavController, viewModel: BookmarkViewModel = hiltViewModel()) {
    BookmarkScreen(
        ComposeBookmarkUiState(viewModel),
        ComposeBookmarkPresenter(viewModel, navHostController)
    )
}

@Composable
internal fun TitleBar() {
    Box(Modifier.fillMaxWidth().testTag("bookmark_bar")) {
        Text(
            text = stringResource(id = R.string.label_bookmarks),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(4.dp, 2.dp)
        )
    }
}

@Composable
internal fun BookmarkContents(
    pagingDataState: State<Flow<PagingData<MarvelCharacter>>?>,
    onThumbnailClick: (url: String?) -> Unit,
    onBookmarkClick: (item: MarvelCharacter) -> Unit,
    onDescriptionClick: (id: Int) -> Unit
) {
    val pagingDataStateValue by pagingDataState
    val pagingItems = pagingDataStateValue?.collectAsLazyPagingItems() ?: return
    LazyColumn(
        contentPadding = PaddingValues(16.dp, 8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
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

@Composable
internal fun SnackBarMessage(
    messageState: State<Action.Message?>,
    snackBarHostState: SnackbarHostState
) {
    snackBarHostState.showSnackBarMessage(messageState) { stateValue ->
        stringResource(
            when (stateValue) {
                is Action.Message.FailedToLoadData -> com.example.ui.R.string.failed_to_load_data
                is Action.Message.FailedToRemoveBookmark -> R.string.failed_to_delete_bookmark
                is Action.Message.SuccessToSaveImage -> R.string.image_saved_successfully
                is Action.Message.FailedToSaveImage -> R.string.failed_to_save_image
            }
        )
    }
}

@Composable
internal fun BookmarkScreen(
    uiState: BookmarkComposableUiState,
    presenter: BookmarkPresenter
) {
    Timber.d("BookmarkScreen")
    ComposeTheme {
        DefaultSurface {
            Scaffold(snackbarHost = {
                SnackbarHost(uiState.snackBarHostState)
            }) { paddingValues ->
                Column(
                    Modifier.fillMaxSize().statusBarsPadding()
                        .padding(
                            start = paddingValues.calculateTopPadding(),
                            top = 0.dp,
                            end = 0.dp,
                            bottom = paddingValues.calculateBottomPadding()
                        )
                ) {
                    TitleBar()
                    BookmarkContents(
                        uiState.pagingData,
                        presenter::onThumbnailClick,
                        presenter::onBookmarkClick,
                        presenter::onDescriptionClick
                    )
                    SnackBarMessage(uiState.message, uiState.snackBarHostState)
                }
            }
        }
    }
}

@Composable
internal fun Navigation(navigation: State<Action.Navigation?>, navController: NavController) {
    val navValue = navigation.value ?: return
    when(navValue) {
        is Action.Navigation.MoveToDetail -> {
            val route = ProfileGraph.Route.makeRoute(navValue.id)
            navController.navigate(route, navOptions = null)
        }
    }
}