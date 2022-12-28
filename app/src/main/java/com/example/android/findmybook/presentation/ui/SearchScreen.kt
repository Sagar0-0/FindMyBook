package com.example.android.findmybook.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.findmybook.presentation.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val query = viewModel.query.value
    val focusManager: FocusManager = LocalFocusManager.current
    var isInvalidInput by rememberSaveable {
        mutableStateOf(false)
    }
    var errorMessage by rememberSaveable {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            SearchBar(
                query = query,
                isInvalidInput = isInvalidInput,
                errorMessage = errorMessage,
                onValueChange = viewModel::onQueryChange,
                focusManager = focusManager,
                onTriggerEvent = viewModel::onTriggerEvent,
                validateQuery = { isInvalidInput = false }
            )
        }
    ) {
        BooksList(
            paddingValues = it,
            viewModel = viewModel,
            onUpdateError = {
                isInvalidInput = true
                errorMessage = it
            }
        )
    }
}





