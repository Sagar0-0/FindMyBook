package com.example.android.findmybook.di

import com.example.android.findmybook.network.NetworkService
import com.example.android.findmybook.network.mapper.BookDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesDtoMapper(): BookDtoMapper {
        return BookDtoMapper()
    }

    @Singleton
    @Provides
    fun providesBookService():NetworkService{
        return Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkService::class.java)
    }
}