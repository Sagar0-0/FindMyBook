package com.example.android.findmybook.data.remote

import com.example.android.findmybook.data.remote.model.BookSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("volumes")
    suspend fun searchBookByTitle(
        @Query("q") bookName: String,
        @Query("startIndex") startIndex : Int
    ): BookSearchResponse
}