package com.example.android.findmybook.repository.network

import com.example.android.findmybook.domain.model.Book

interface NetworkRepository {
    suspend fun searchBookByTitle(title:String):List<Book>
}