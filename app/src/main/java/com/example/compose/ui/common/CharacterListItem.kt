package com.example.compose.ui.common

import android.Manifest
import android.graphics.drawable.Drawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.data.model.CharacterItem
import com.example.compose.R
import com.example.shared.extensions.pixel
import com.google.accompanist.permissions.*

@Composable
fun CharacterContent(
    character: CharacterItem,
    onThumbnailClick: (String?) -> Unit,
    onBookmarkClick: (CharacterItem) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CharacterThumbnail(
            character = character,
            onClick = onThumbnailClick
        )
        CharacterInformation(item = character, onBookmarkClick = onBookmarkClick)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterThumbnail(
    character: CharacterItem, onClick: (String?) -> Unit
) = with(character) {
    val thumbnailSize = dimensionResource(id = R.dimen.thumbnail_size)
    val requestBuilder: RequestBuilder<Drawable> = Glide.with(LocalContext.current)
        .asDrawable().override(thumbnailSize.value.pixel).sizeMultiplier(.1f)

    val noImage = ImageBitmap.imageResource(id = R.drawable.no_image)
    GlideImage(
        model = thumbnail,
        contentDescription = description,
        modifier = Modifier
            .size(thumbnailSize)
            .padding(10.dp)
            .clickable {
                onClick(thumbnail)
            },
    ) {
        it.thumbnail(requestBuilder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(noImage)
    }
}

@Composable
fun CharacterInformation(item: CharacterItem, onBookmarkClick: (CharacterItem) -> Unit) =
    with(item) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(text = name ?: stringResource(id = R.string.label_unknown))
                Text(text = stringResource(id = R.string.url_count, urlCount))
                Text(text = stringResource(id = R.string.comic_count, comicCount))
                Text(text = stringResource(id = R.string.story_count, storyCount))
                Text(text = stringResource(id = R.string.event_count, eventCount))
                Text(text = stringResource(id = R.string.series_count, seriesCount))
            }
            IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = {
                    onBookmarkClick(this@with.copy(mark = !mark))
                }) {
                val resId =
                    if (mark) R.drawable.bookmark_activate else R.drawable.bookmark_deactivate
                Icon(
                    painter = painterResource(id = resId),
                    contentDescription = stringResource(id = R.string.label_bookmark)
                )
            }
        }
    }

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckPermission(onGranted: () -> Unit) {
    val externalStoragePermissionState =
        rememberPermissionState(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    if(externalStoragePermissionState.status.isGranted) {
        onGranted()
    } else {
        Column {
            Text(stringResource(id = R.string.request_external_storage_permission))
            Button(onClick = { externalStoragePermissionState.launchPermissionRequest() }) {
                Text("Request permission")
            }
        }
    }
}