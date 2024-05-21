package com.example.di

import com.example.home.MainActionReducer
import com.example.home.MainActionReducerImpl
import com.example.home.MainIntentProcessor
import com.example.home.MainProcessor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MainViewModelBindModule {

    @Binds
    abstract fun bindMainProcessor(
        intentProcessor: MainIntentProcessor
    ): MainProcessor

    @Binds
    abstract fun bindMainActionReducer(
        actionReducer: MainActionReducerImpl
    ): MainActionReducer
}