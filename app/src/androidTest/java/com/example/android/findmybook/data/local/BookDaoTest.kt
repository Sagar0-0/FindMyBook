package com.example.android.findmybook.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.android.findmybook.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
@ExperimentalCoroutinesApi
class BookDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: BookDatabase
    private lateinit var dao: BookDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BookDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.booksDao()
    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertBookItemTest() = runTest {
        val data = BookEntity("", "", 1f, "", 1)
        dao.insertBookItem(data)

        val list = dao.observeAllBookItems().getOrAwaitValue()
        assertThat(list).contains(data)
    }

    @Test
    fun deleteBookItemTest() = runTest {
        val data = BookEntity("", "", 1f, "", 1)
        dao.insertBookItem(data)
        dao.deleteBookItem(data)

        val list = dao.observeAllBookItems().getOrAwaitValue()
        assertThat(list).doesNotContain(data)
    }
}