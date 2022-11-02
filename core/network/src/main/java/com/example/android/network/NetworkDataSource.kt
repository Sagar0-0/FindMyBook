package com.example.android.network

import com.example.android.findmybook.model.Book


interface NetworkDataSource {

    suspend fun getBooks(ids: List<String>? = null): List<Book>
}