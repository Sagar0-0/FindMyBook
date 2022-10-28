package com.example.android.findmybook

import androidx.datastore.core.DataStore
import com.example.android.datastore.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSource(private val dataStore: DataStore<UserPreferences>) {
    val bookmarksStream : Flow<List<String>> = dataStore.data.map {
        it.bookmarkedBookIdsMap.keys.toList()
    }

    suspend fun toggleBookmark(bookResourceId:String,isBookMarked:Boolean){
        dataStore.updateData {
            if(isBookMarked){
                it.bookmarkedBookIdsMap[bookResourceId] = true
            }else{
                it.bookmarkedBookIdsMap.remove(bookResourceId)
            }
            it
        }
    }
}