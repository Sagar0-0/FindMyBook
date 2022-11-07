package com.example.android.findmybook.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.findmybook.model.BooksDTO
import com.example.android.findmybook.repository.BooksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: BooksRepository):ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getQuotes("linkedin")
        }
    }
    val books :LiveData<BooksDTO>
    get() = repository.books
}