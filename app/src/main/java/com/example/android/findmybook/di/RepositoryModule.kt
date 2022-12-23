package com.example.android.findmybook.di

import com.example.android.findmybook.repository.BooksRepo_Impl
import com.example.android.findmybook.repository.BooksRepository
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
        networkrepoImpl: BooksRepo_Impl
    ): BooksRepository
}