package com.example.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.profile.GetCharacterProfileUseCase
import com.example.model.MarvelCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val getCharacterProfileUseCase: GetCharacterProfileUseCase
) : ViewModel(), UiState, ProfilePresenter {

    private val _profileData: MutableStateFlow<MarvelCharacter?> =
        MutableStateFlow(null)
    override val profileData: StateFlow<MarvelCharacter?>
        get() = _profileData

    private val _message: MutableStateFlow<Action.Message?> = MutableStateFlow(null)
    override val message: StateFlow<Action.Message?>
        get() = _message

    override fun getCharacterProfile(id: Int) {
        viewModelScope.launch {
            getCharacterProfileUseCase(GetCharacterProfileUseCase.Params(id))
                .onSuccess { _profileData.emit(it) }
                .onFailure { _message.emit(Action.Message.FailedToLoadData) }
        }
    }
}