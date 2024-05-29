package com.example.datastore.proto.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.datastore.proto.CharacterProto
import com.example.datastore.proto.CharacterProtoSerializer
import com.example.shared.di.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object ProtoDataStoreModule {

    @Provides
    @Singleton
    internal fun provideCharacterProtoDataStore(
        @ApplicationContext context: Context,
        @IoDispatcher dispatcher: CoroutineDispatcher,
        characterProtoSerializer: CharacterProtoSerializer
    ): DataStore<CharacterProto> =
        DataStoreFactory.create(
            serializer = characterProtoSerializer,
            migrations = emptyList(),
            scope = CoroutineScope(SupervisorJob() + dispatcher)
        ) {
            context.dataStoreFile("character_proto.pb")
        }
}