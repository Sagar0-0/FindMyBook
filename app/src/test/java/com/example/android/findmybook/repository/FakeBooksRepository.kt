package com.example.android.findmybook.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.findmybook.data.local.BookEntity
import com.example.android.findmybook.data.remote.model.BookSearchResponse
import com.example.android.findmybook.data.remote.model.Item
import com.example.android.findmybook.others.Result


class FakeBooksRepository : BooksRepository{
    private val booksList = mutableListOf<BookEntity>()
    private val observableBookEntity = MutableLiveData<List<BookEntity>>(booksList)

    private var shouldReturnNetworkError = false
    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData(){
        observableBookEntity.postValue(booksList)
    }
    override suspend fun saveBookItem(book: BookEntity) {
        booksList.add(book)
        refreshLiveData()
    }

    override suspend fun removeBookItem(book: BookEntity) {
        booksList.remove(book)
        refreshLiveData()
    }

    override fun observeAllSavedBook(): LiveData<List<BookEntity>> {
        return observableBookEntity
    }

    override suspend fun searchBookByTitle(title: String, startIndex: Int): Result<List<Item>> {
        return if(shouldReturnNetworkError){
            Result.error("ERROR",null)
        }else{
            Result.success(BookSearchResponse(listOf(),"",1))
        }
    }
}