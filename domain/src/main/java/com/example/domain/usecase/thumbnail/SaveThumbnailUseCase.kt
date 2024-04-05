package com.example.domain.usecase.thumbnail

import android.content.Context
import com.example.data.datasource.stream.ImageStreamRepository
import com.example.shared.di.IoDispatcher
import com.example.shared.interaction.SuspendingUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import org.apache.commons.io.FilenameUtils
import javax.inject.Inject

class SaveThumbnailUseCase @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
    private val repository: ImageStreamRepository
) : SuspendingUseCase<SaveThumbnailUseCase.Params, Unit>(dispatcher) {

    override suspend fun execute(params: Params) {
        val url = params.url
        requireNotNull(url)
        repository.download(
            context = context,
            url = url,
            dir = "/MARVEL",
            fileName = getFileName(url)
        )
    }

    private fun getFileName(path: String) =
        "${PREFIX}_${System.currentTimeMillis()}.${FilenameUtils.getExtension(path)}"

    class Params(val url: String?)

    companion object {
        const val PREFIX = "marvel"
    }
}