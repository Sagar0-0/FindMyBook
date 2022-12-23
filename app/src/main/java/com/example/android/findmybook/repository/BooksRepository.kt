package com.example.android.findmybook.repository

import androidx.lifecycle.LiveData
import com.example.android.findmybook.data.local.BookEntity
import com.example.android.findmybook.data.remote.model.BookSearchResponse
import com.example.android.findmybook.others.Resource


interface BooksRepository {

    suspend fun saveBookItem(book: BookEntity)
    suspend fun removeBookItem(book: BookEntity)
    fun observeAllSavedBook(): LiveData<List<BookEntity>>

    suspend fun searchBookByTitle(title: String): Resource<BookSearchResponse>
}