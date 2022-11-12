package com.example.android.findmybook.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.findmybook.domain.model.Book
import com.example.android.findmybook.repository.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: NetworkRepository
) : ViewModel() {

    val searchTitle = savedStateHandle.getStateFlow("searchTitle", "")
    private val _books = MutableStateFlow<List<Book>>(value = listOf())
    val books = _books.asStateFlow()

    fun getBooks(title: String) {
        savedStateHandle["searchTitle"] = title
        viewModelScope.launch {
            getBooksByTitle()
        }
    }

    private suspend fun getBooksByTitle() {
        _books.value = repository.searchBookByTitle(searchTitle.value)
    }
}