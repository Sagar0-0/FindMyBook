package com.example.android.findmybook.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.android.findmybook.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class BookDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: BookDatabase
    private lateinit var dao: BookDao

    @Before
    fun setup() {
        hiltRule.inject()
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