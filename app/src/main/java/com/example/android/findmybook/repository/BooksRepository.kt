package com.example.android.findmybook.repository

import androidx.lifecycle.LiveData
import com.example.android.findmybook.data.local.BookEntity
import com.example.android.findmybook.data.remote.model.Item
import com.example.android.findmybook.others.Result


interface BooksRepository {

    suspend fun saveBookItem(book: BookEntity)
    suspend fun removeBookItem(book: BookEntity)
    fun observeAllSavedBook(): LiveData<List<BookEntity>>

    suspend fun searchBookByTitle(title: String,startIndex:Int): Result<List<Item>?>
}