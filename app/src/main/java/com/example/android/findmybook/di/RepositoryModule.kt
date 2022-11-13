package com.example.android.findmybook.di

import com.example.android.findmybook.repository.network.NetworkRepo_Impl
import com.example.android.findmybook.repository.network.NetworkRepository
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
    abstract fun bindMyRepo(
        networkrepoImpl: NetworkRepo_Impl
    ): NetworkRepository
}