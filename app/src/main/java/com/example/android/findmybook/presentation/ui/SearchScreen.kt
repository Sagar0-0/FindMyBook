package com.example.android.findmybook.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.findmybook.others.Status
import com.example.android.findmybook.presentation.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val focusManager: FocusManager = LocalFocusManager.current
    var text by rememberSaveable {
        mutableStateOf(viewModel.searchTitle.value)
    }
    var isInvalidInput by rememberSaveable {
        mutableStateOf(false)
    }
    val resource by viewModel.books.observeAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = text,
            isError = isInvalidInput,
            supportingText = {
                if (isInvalidInput) {
                    resource?.message?.let { Text(text = it) }
                }
            },
            singleLine = true,
            maxLines = 1,
            shape = CircleShape,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 10.dp,
                    end = 10.dp
                ),
            onValueChange = {
                text = it
            },
            label = {
                Text("Search")
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false, radius = 18.dp)
                    ) {
                        focusManager.clearFocus()
                        onExecuteSearch(viewModel, text)
                        isInvalidInput = false
                        text=text.trim()
                    }
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(onSearch = {
                focusManager.clearFocus()
                isInvalidInput = false
                onExecuteSearch(viewModel, text)
                text=text.trim()
            }),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface)
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (resource?.status) {
            Status.SUCCESS -> {
                val books = resource?.data?.items
                if (books.isNullOrEmpty()) {
                    Text(text = "No data found")
                } else {
                    LazyColumn(
                        state = rememberLazyListState(),
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(
                                start = 10.dp,
                                end = 10.dp
                            )
                    ) {
                        items(items = books, itemContent = { item ->
                            BookItem(item)
                            Spacer(modifier = Modifier.padding(10.dp))
                        })
                    }
                }
            }
            Status.LOADING -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoadingAnimation()
                }
            }
            Status.ERROR -> {
                isInvalidInput = true
            }
            else -> {
                Text(text = "Search for your favourite books here")
            }
        }
    }
}

fun onExecuteSearch(viewModel: MainViewModel, text: String) {
    viewModel.searchBookByTitle(text.trim())
}


