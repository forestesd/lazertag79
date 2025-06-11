package com.example.lazertag79.di

import com.example.mainscreen.data.repository.TeamsRepository
import com.example.mainscreen.domain.repository.TeamsRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MainScreenModule {
    @Binds
    abstract fun bindTeamsRepository(
        impl: TeamsRepository
    ): TeamsRepositoryInterface
}
