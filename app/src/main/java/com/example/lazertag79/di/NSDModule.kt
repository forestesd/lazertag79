package com.example.lazertag79.di

import android.content.Context
import com.example.lazertag79.NSDHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NSDModule {

    @Provides
    @Singleton
    fun provideNSDHelper(
        @ApplicationContext context: Context
    ) = NSDHelper(context)
}