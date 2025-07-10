package com.example.lazertag79.di

import com.example.mainscreen.data.repository.TeamsRepository
import com.example.comon.Teams.domain.domain.repository.TeamsRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainScreenModule {
    @Binds
    @Singleton
    abstract fun bindTeamsRepository(
        impl: TeamsRepository
    ): TeamsRepositoryInterface
}
