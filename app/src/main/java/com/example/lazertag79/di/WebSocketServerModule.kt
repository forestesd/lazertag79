package com.example.lazertag79.di

import com.example.comon.game.data.WebSocketServer
import com.example.comon.game.domain.use_cases.GameLogsUpdateUseCase
import com.example.comon.server.domain.useCases.ConnectTaggerUseCase
import com.example.comon.server.domain.useCases.TaggerInfoGameResMapperUseCase
import com.example.comon.server.domain.useCases.TaggersInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WebSocketServerModule {

    @Provides
    @Singleton
    fun provideWebSocketServer(
        connectTaggerUseCaseProvider: Provider<ConnectTaggerUseCase>,
        taggerInfoGameResMapperUseCase: Provider<TaggerInfoGameResMapperUseCase>,
        taggerInfoUseCase: Provider<TaggersInfoUseCase>,
        gameLogsUpdateUseCase: Provider<GameLogsUpdateUseCase>
        ): WebSocketServer {
        return WebSocketServer(
            port = 8080,
            connectTaggerUseCaseProvider = connectTaggerUseCaseProvider,
            taggerInfoGameResMapperUseCase = taggerInfoGameResMapperUseCase,
            taggerInfoUseCase = taggerInfoUseCase,
            gameLogsUpdateUseCase = gameLogsUpdateUseCase,
        )
    }
}