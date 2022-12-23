package com.example.android.findmybook.di

import android.content.Context
import androidx.room.Room
import com.example.android.findmybook.data.local.BookDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun provideDBinMemory(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(
        context,
        BookDatabase::class.java
    ).allowMainThreadQueries().build()

}