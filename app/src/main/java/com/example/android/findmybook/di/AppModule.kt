package com.example.android.findmybook.di

import android.content.Context
import androidx.room.Room
import com.example.android.findmybook.BaseApplication
import com.example.android.findmybook.data.local.BookDatabase
import com.example.android.findmybook.others.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)//will decide how long these depen.. will live
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }
}