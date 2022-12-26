package com.example.android.findmybook.repository

import androidx.lifecycle.LiveData
import com.example.android.findmybook.data.local.BookDao
import com.example.android.findmybook.data.local.BookEntity
import com.example.android.findmybook.data.remote.NetworkService
import com.example.android.findmybook.data.remote.mapper.BookDtoMapper
import com.example.android.findmybook.data.remote.model.BookSearchResponse
import com.example.android.findmybook.others.Resource
import javax.inject.Inject

class BooksRepo_Impl @Inject constructor(
    private val bookDao: BookDao,
    private val booksApi: NetworkService
) : BooksRepository {

    override suspend fun saveBookItem(book: BookEntity) {
        bookDao.insertBookItem(book)
    }

    override suspend fun removeBookItem(book: BookEntity) {
        bookDao.deleteBookItem(book)
    }

    override fun observeAllSavedBook(): LiveData<List<BookEntity>> {
        return bookDao.observeAllBookItems()
    }

    override suspend fun searchBookByTitle(title: String): Resource<BookSearchResponse> {
        return try{
            val response = booksApi.searchBookByTitle(title)
            if(response.isSuccessful){
                response.body()?.let {
                    Resource.success(it)
                } ?: Resource.error("Response body null",null)
            }else{
                Resource.error("Not successful response",null)
            }
        } catch (e:Exception){
            Resource.error("No Internet",null)
        }
    }

}