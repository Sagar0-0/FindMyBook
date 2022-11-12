package com.example.android.findmybook.di

import com.example.android.findmybook.network.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)//will decide how long these depen.. will live
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkService():NetworkService{
        return Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .build()
            .create(NetworkService::class.java)
    }
}