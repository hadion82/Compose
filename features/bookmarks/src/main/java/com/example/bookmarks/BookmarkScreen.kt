package com.example.bookmarks

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.ui.common.CharacterContent
import com.example.ui.theme.ComposeTheme
import com.example.ui.theme.DefaultSurface

@Composable
fun TitleBar() {
    Box(Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.label_bookmarks),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(4.dp, 2.dp)
        )
    }
}

@Composable
fun BookmarkContents(
    presenter: BookmarkPresenter
) {
    val pagingData by presenter.pagingData
    val pagingItems = pagingData?.collectAsLazyPagingItems() ?: return
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
                    onThumbnailClick = presenter::onThumbnailClick,
                    onBookmarkClick = presenter::onBookmarkClick
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun ToastMessage(
    state: State<Action.Message?>
) = with(LocalContext.current) {
    state.value?.let { message ->
        Toast.makeText(
            this,
            when (message) {
                is Action.Message.FailedToLoadData -> R.string.failed_to_load_data
                is Action.Message.FailedToRemoveBookmark -> R.string.failed_to_delete_bookmark
                is Action.Message.SuccessToSaveImage -> R.string.image_saved_successfully
                is Action.Message.FailedToSaveImage -> R.string.failed_to_save_image
            }, Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
fun BookmarkScreen(
    presenter: BookmarkPresenter
) {
    ComposeTheme {
        DefaultSurface {
            Column(
                Modifier.fillMaxSize()
            ) {
                TitleBar()
                BookmarkContents(presenter)
                ToastMessage(presenter.message)
            }
        }
    }
}