package com.example.lazertag79.di;

import com.example.comon.server.data.ServerRepository;
import com.example.comon.server.domain.repository.ServerRepositoryInterface;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent::class)
abstract class ServerModule {

    @Binds
    @Singleton
    abstract fun bindServerRepository(
        ipml: ServerRepository
    ): ServerRepositoryInterface
}