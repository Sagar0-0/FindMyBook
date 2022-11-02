package com.example.android.findmybook.datastore

import kotlinx.coroutines.flow.Flow

class BookmarksRepository(private val localDataSource: LocalDataSource) {
    val bookmarksStream: Flow<List<String>> = localDataSource.bookmarksStream

    suspend fun toggleBookmark(bookId: String, isBookMarked: Boolean) {
        localDataSource.toggleBookmark(bookId, isBookMarked)
    }
}