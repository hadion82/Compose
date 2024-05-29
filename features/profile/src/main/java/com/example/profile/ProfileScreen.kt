package com.example.profile

import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.model.MarvelCharacter
import com.example.shared.extensions.pixel
import com.example.ui.common.showSnackBarMessage
import com.example.ui.theme.ComposeTheme
import com.example.ui.theme.DefaultSurface

@Composable
internal fun ProfileRoute(id: Int, viewModel: ProfileViewModel = hiltViewModel()) {
    viewModel.getCharacterProfile(id)
    ProfileScreen(ComposeProfileUiState(viewModel))
}

@Composable
internal fun TitleBar() {
    Box(Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.label_profile),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(4.dp, 2.dp)
        )
    }
}

@Composable
fun CharacterContent(
    characterDataState: State<MarvelCharacter?>
) {
    val pagingDataStateValue = characterDataState.value ?: return
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CharacterThumbnail(
            character = pagingDataStateValue
        )
        Spacer(Modifier.fillMaxWidth().height(20.dp))
        CharacterInformation(item = pagingDataStateValue)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterThumbnail(
    character: MarvelCharacter
) = with(character) {
    val imageSize = dimensionResource(id = com.example.ui.R.dimen.image_size)
    val requestBuilder: RequestBuilder<Drawable> = Glide.with(LocalContext.current)
        .asDrawable().override(imageSize.value.pixel).sizeMultiplier(.1f)

    val noImage = ImageBitmap.imageResource(id = com.example.ui.R.drawable.no_image)
    GlideImage(
        model = thumbnail,
        contentDescription = description,
        modifier = Modifier
            .size(imageSize)
            .padding(10.dp),
    ) {
        it.thumbnail(requestBuilder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(noImage)
    }
}

@Composable
fun CharacterInformation(item: MarvelCharacter) =
    with(item) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                val unknownString = stringResource(com.example.ui.R.string.label_unknown)
                val name = if(name.isNullOrBlank()) unknownString else name!!
                val description = if(description.isNullOrBlank()) unknownString else description!!
                Text(text = stringResource(id = com.example.ui.R.string.variant_name, name))
                Text(text = stringResource(id = com.example.ui.R.string.variant_description, description))
                Text(text = stringResource(id = com.example.ui.R.string.url_count, urlCount))
                Text(text = stringResource(id = com.example.ui.R.string.comic_count, comicCount))
                Text(text = stringResource(id = com.example.ui.R.string.story_count, storyCount))
                Text(text = stringResource(id = com.example.ui.R.string.event_count, eventCount))
                Text(text = stringResource(id = com.example.ui.R.string.series_count, seriesCount))
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
            }
        )
    }
}

@Composable
internal fun ProfileScreen(
    uiState: ProfileComposableUiState
) {
    ComposeTheme {
        DefaultSurface {
            Scaffold(snackbarHost = {
                SnackbarHost(uiState.snackBarHostState)
            }) { paddingValues ->
                Column(
                    Modifier.fillMaxSize().statusBarsPadding()
                        .padding(paddingValues)
                ) {
                    TitleBar()
                    CharacterContent(
                        uiState.characterData
                    )
                    SnackBarMessage(uiState.message, uiState.snackBarHostState)
                }
            }
        }
    }
}