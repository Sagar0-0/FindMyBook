package com.example.android.findmybook.di

import android.content.Context
import androidx.room.Room
import com.example.android.findmybook.data.local.BookDatabase
import com.example.android.findmybook.others.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideBooksDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, BookDatabase::class.java, Constants.DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideBooksDao(
        database: BookDatabase
    ) = database.booksDao()

}