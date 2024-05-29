package com.example.di

import com.example.graph.HomeGraph
import com.example.home.HomeActionReducer
import com.example.home.HomeActionReducerImpl
import com.example.home.HomeGraphImpl
import com.example.home.HomeIntentProcessor
import com.example.home.HomeProcessor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HomeGraphBindModule {

    @Binds
    abstract fun bindHomeGraph(
        impl: HomeGraphImpl
    ): HomeGraph
}