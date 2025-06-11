package com.example.lazertag79.di

import com.example.comon.game.data.GameRepository
import com.example.comon.game.domain.GameRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GameModule {
    @Binds
    abstract fun bindGameRepository(
        impl: GameRepository
    ): GameRepositoryInterface
}