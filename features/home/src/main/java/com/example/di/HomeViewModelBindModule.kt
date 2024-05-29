package com.example.di

import com.example.home.HomeActionReducer
import com.example.home.HomeActionReducerImpl
import com.example.home.HomeIntentProcessor
import com.example.home.HomeProcessor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class HomeViewModelBindModule {

    @Binds
    abstract fun bindHomeProcessor(
        intentProcessor: HomeIntentProcessor
    ): HomeProcessor

    @Binds
    abstract fun bindHomeActionReducer(
        actionReducer: HomeActionReducerImpl
    ): HomeActionReducer
}