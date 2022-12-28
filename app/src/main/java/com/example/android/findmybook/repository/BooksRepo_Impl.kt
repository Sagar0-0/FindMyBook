package com.example.android.findmybook.repository

import androidx.lifecycle.LiveData
import com.example.android.findmybook.data.local.BookDao
import com.example.android.findmybook.data.local.BookEntity
import com.example.android.findmybook.data.remote.NetworkService
import com.example.android.findmybook.data.remote.model.Item
import com.example.android.findmybook.others.Result
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

    override suspend fun searchBookByTitle(title: String, startIndex: Int): Result<List<Item>?> {
        return try{
            val response = booksApi.searchBookByTitle(title,startIndex)
            response.items.let {
                Result.success(it)
            }
        } catch (e:Exception){
            Result.error("No Internet", listOf())
        }
    }

}