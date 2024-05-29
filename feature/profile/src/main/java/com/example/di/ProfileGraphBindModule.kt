package com.example.di

import com.example.graph.ProfileGraph
import com.example.profile.ProfileGraphImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ProfileGraphBindModule {
    @Binds
    abstract fun bindProfileGraph(impl: ProfileGraphImpl): ProfileGraph
}