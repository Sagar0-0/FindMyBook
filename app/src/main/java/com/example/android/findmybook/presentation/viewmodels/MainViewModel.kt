package com.example.android.findmybook.presentation.viewmodels


import androidx.lifecycle.*
import com.example.android.findmybook.data.local.BookEntity
import com.example.android.findmybook.data.remote.model.BookSearchResponse
import com.example.android.findmybook.others.Constants
import com.example.android.findmybook.others.Event
import com.example.android.findmybook.others.Resource
import com.example.android.findmybook.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: BooksRepository
) : ViewModel() {

    val savedBooksList = repository.observeAllSavedBook()

    val searchTitle = savedStateHandle.getStateFlow("searchTitle", "")

    private val _books = MutableLiveData<Event<Resource<BookSearchResponse>>>()
    val books: LiveData<Event<Resource<BookSearchResponse>>> = _books

    fun removeBookFromDB(bookEntity: BookEntity) = viewModelScope.launch {
        repository.removeBookItem(bookEntity)
    }

    fun addBookIntoDB(bookEntity: BookEntity) = viewModelScope.launch {
        repository.saveBookItem(bookEntity)
    }


    fun searchBookByTitle(bookName: String) {
        if(bookName.isEmpty()){
            _books.postValue(Event(Resource.error("The title must not be empty",null)))
            return
        }else if(bookName.length>Constants.MAX_BOOK_TITLE_LENGTH){
            _books.postValue(Event(Resource.error("The title length must not exceed ${Constants.MAX_BOOK_TITLE_LENGTH} size",null)))
            return
        }else{
            _books.value = Event(Resource.loading(null))
            savedStateHandle["searchTitle"] = bookName
            viewModelScope.launch {
                val response = repository.searchBookByTitle(bookName)
                _books.value = Event(response)
            }
        }

    }
}