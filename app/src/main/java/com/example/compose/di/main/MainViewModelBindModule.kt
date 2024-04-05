package com.example.compose.di.main

import com.example.compose.ui.main.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MainViewModelBindModule {

    @Binds
    abstract fun bindMainIntentProcessor(
        intentProcessor: MainIntentProcessor
    ): MainProcessor

    @Binds
    abstract fun bindMainActionReducer(
        actionReducer: MainActionReducerImpl
    ): MainActionReducer
}