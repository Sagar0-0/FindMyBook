package com.example.android.findmybook.network

import com.example.android.findmybook.model.BooksDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NetworkDataSource {

    @GET(value="/volumes")
    suspend fun getBooks(@Query("q")searchWord:String): Response<BooksDTO>
    //baseURL + /volumes + ?q= + searchword


}