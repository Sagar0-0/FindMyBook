package com.example.android.findmybook.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookItem(bookEntity: BookEntity)

    @Delete
    suspend fun deleteBookItem(bookEntity: BookEntity)

    @Query("SELECT * FROM book_table")
    fun observeAllBookItems(): LiveData<List<BookEntity>>

}