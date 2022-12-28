package com.example.android.findmybook.presentation


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.android.findmybook.data.local.BookEntity
import com.example.android.findmybook.data.remote.model.Item
import com.example.android.findmybook.others.Constants
import com.example.android.findmybook.others.Constants.STATE_KEY_PAGE
import com.example.android.findmybook.others.Constants.STATE_KEY_QUERY
import com.example.android.findmybook.others.Result
import com.example.android.findmybook.presentation.BookSearchEvent.*
import com.example.android.findmybook.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: BooksRepository
) : ViewModel() {

    val savedBooksList = repository.observeAllSavedBook()

    val query: MutableState<String> = mutableStateOf("")
    val pageIndex: MutableState<Int> = mutableStateOf(0)
    val noMoreData : MutableState<Boolean> = mutableStateOf(false)

    private val _books = MutableLiveData<Result<List<Item>?>>()
    val books: LiveData<Result<List<Item>?>> = _books

    fun removeBookFromDB(bookEntity: BookEntity) = viewModelScope.launch {
        repository.removeBookItem(bookEntity)
    }

    fun addBookIntoDB(bookEntity: BookEntity) = viewModelScope.launch {
        repository.saveBookItem(bookEntity)
    }

    fun onQueryChange(query: String) {
        this.query.value = query
        savedStateHandle[STATE_KEY_QUERY] = query
    }

    fun onTriggerEvent(event : BookSearchEvent){
        viewModelScope.launch {
            when(event){
                NewSearch -> {
                    resetEverything()
                    searchNewBook()
                }
                NextPageSearch -> {
                    getNextPageData()
                }
                RestoreSearchData -> {
                    restoreData()
                }
            }
        }
    }

    private suspend fun searchNewBook() {
        if (query.value.isEmpty()) {
            _books.postValue(
                Result.error(
                    "The title must not be empty", listOf()
                )
            )
        } else if (query.value.length > Constants.MAX_BOOK_TITLE_LENGTH) {
            _books.postValue(
                Result.error(
                    "The title length must not exceed ${Constants.MAX_BOOK_TITLE_LENGTH} letters.",
                    listOf()
                )
            )
        } else {
            _books.value = Result.loading(listOf())
            viewModelScope.launch {
                val result = repository.searchBookByTitle(query.value, pageIndex.value)
                if(result.data.isNullOrEmpty()){
                    Result.error("No data found", listOf<Item>())
                }else{
                    _books.value = result
                }
            }
        }
    }

    private suspend fun getNextPageData(){
        delay(1000)
        if(!noMoreData.value){
            increasePageIndex()
            viewModelScope.launch {
                appendBooks(repository.searchBookByTitle(query.value, pageIndex.value).data)
            }
        }
    }

    private suspend fun restoreData() {
        TODO("Not yet implemented")
    }

    private fun resetEverything() {
        pageIndex.value = 0
        noMoreData.value = false
        _books.value = Result.success(listOf())
    }

    private fun increasePageIndex() {
        pageIndex.value = pageIndex.value + 1
        savedStateHandle[STATE_KEY_PAGE] = pageIndex
    }

    private fun appendBooks(newBooks: List<Item>?) {
        if(newBooks.isNullOrEmpty()){
            noMoreData.value = true
        }else{
            val current = ArrayList(this.books.value!!.data!!)
            current.addAll(newBooks)
            this._books.value = Result.success(current)
        }
    }
}