package com.example.android.findmybook.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.android.findmybook.MainCoroutineRule
import com.example.android.findmybook.data.local.BookEntity
import com.example.android.findmybook.getOrAwaitValueTest
import com.example.android.findmybook.others.Constants
import com.example.android.findmybook.others.Status
import com.example.android.findmybook.repository.FakeBooksRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel(SavedStateHandle(), FakeBooksRepository())
    }

    @Test
    fun `search book with empty title_return error`() {
        viewModel.searchBookByTitle("")
        val value = viewModel.books.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `search book with long title_return error`() {
        val string = buildString {
            for (i in 1..Constants.MAX_BOOK_TITLE_LENGTH + 1) {
                append(i)
            }
        }
        viewModel.searchBookByTitle(bookName = string)
        val value = viewModel.books.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `search valid book name_return success`() {
        viewModel.searchBookByTitle(bookName = "Github")
        val value = viewModel.books.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun addToDb(){
        val data = BookEntity("","",1f,"",0)
        viewModel.addBookIntoDB(data)

        val books = viewModel.savedBooksList.getOrAwaitValueTest()
        assertThat(books).contains(data)
    }

    @Test
    fun removeFromDb(){
        val data = BookEntity("","",1f,"",0)
        viewModel.addBookIntoDB(data)
        viewModel.removeBookFromDB(data)

        val books = viewModel.savedBooksList.getOrAwaitValueTest()
        assertThat(books).doesNotContain(data)
    }

}