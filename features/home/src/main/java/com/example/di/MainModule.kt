package com.example.di

import com.example.home.MainActionDispatcher
import com.example.home.MainActionDispatcherImpl
import com.example.home.MainViewModelDelegate
import com.example.home.MainViewModelDelegateImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun bindMainViewModelDelegate(
        mainViewModelDelegate: MainViewModelDelegateImpl
    ): MainViewModelDelegate = mainViewModelDelegate

    @Singleton
    @Provides
    fun bindMainActionDispatcher(
        mainActionDispatcher: MainActionDispatcherImpl
    ): MainActionDispatcher = mainActionDispatcher
}