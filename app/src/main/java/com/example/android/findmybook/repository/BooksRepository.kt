package com.example.android.findmybook.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.findmybook.model.BooksDTO
import com.example.android.findmybook.network.NetworkDataSource

class BooksRepository(private val networkDataSource: NetworkDataSource) {

    private val booksLiveData= MutableLiveData<BooksDTO>()

    val books:LiveData<BooksDTO>
    get()=booksLiveData
    suspend fun getQuotes(word: String) {
        val result = networkDataSource.getBooks(word)
        if (result?.body() != null) {
            booksLiveData.postValue(result.body())
        }
    }
}