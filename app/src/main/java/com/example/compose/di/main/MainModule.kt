package com.example.compose.di.main

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.compose.ui.main.MainActionDispatcher
import com.example.compose.ui.main.MainActionDispatcherImpl
import com.example.compose.ui.main.MainViewModelDelegate
import com.example.compose.ui.main.MainViewModelDelegateImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideMainViewModelDelegate(
        mainViewModelDelegate: MainViewModelDelegateImpl
    ): MainViewModelDelegate = mainViewModelDelegate

    @Singleton
    @Provides
    fun provideMainActionDispatcher(
        mainActionDispatcher: MainActionDispatcherImpl
    ): MainActionDispatcher = mainActionDispatcher
}