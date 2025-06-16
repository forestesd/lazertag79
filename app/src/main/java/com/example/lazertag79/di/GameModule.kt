package com.example.lazertag79.di

import com.example.comon.game.data.GameRepository
import com.example.comon.game.domain.GameRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindGameRepository(
        impl: GameRepository
    ): GameRepositoryInterface
}