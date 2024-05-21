package com.example.di

import com.example.home.HomeActionDispatcher
import com.example.home.HomeActionDispatcherImpl
import com.example.home.HomeViewModelDelegate
import com.example.home.HomeViewModelDelegateImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Singleton
    @Provides
    fun bindMainViewModelDelegate(
        homeViewModelDelegate: HomeViewModelDelegateImpl
    ): HomeViewModelDelegate = homeViewModelDelegate

    @Singleton
    @Provides
    fun bindMainActionDispatcher(
        homeActionDispatcher: HomeActionDispatcherImpl
    ): HomeActionDispatcher = homeActionDispatcher
}