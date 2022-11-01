package com.example.android.findmybook.datastore

import androidx.datastore.core.DataStore
import com.example.android.datastore.UserPreferences
import com.example.android.findmybook.model.Bookmarks
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSource(private val dataStore: DataStore<Bookmarks>) {
    val bookmarksStream : Flow<List<String>> = dataStore.data.map {
        it.set.toList()
    }

    suspend fun toggleBookmark(bookId:String,isBookMarked:Boolean){
        dataStore.updateData {
            it.copy(
            if(isBookMarked){
                it.set.plusElement(bookId)
            }else{
                it.set.minusElement(bookId)
            })
        }
    }
}