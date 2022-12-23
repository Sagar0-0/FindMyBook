package com.example.android.findmybook.di

import com.example.android.findmybook.data.remote.NetworkService
import com.example.android.findmybook.data.remote.mapper.BookDtoMapper
import com.example.android.findmybook.others.Constants.BASE_URL
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
    fun providesBookApi(): NetworkService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(NetworkService::class.java)
    }
}