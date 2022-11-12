package com.example.android.findmybook.network

import com.example.android.findmybook.network.response.BookSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("volumes")
    suspend fun searchBookByTitle(
        @Query("q") bookName:String
    ):BookSearchResponse
}