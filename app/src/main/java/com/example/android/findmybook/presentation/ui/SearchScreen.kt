package com.example.android.findmybook.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.findmybook.presentation.viewmodels.MainViewModel
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun SearchScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val focusManager: FocusManager = LocalFocusManager.current
    var text by remember {
        mutableStateOf(viewModel.searchTitle.value)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 10.dp,
                    end = 10.dp
                ),
            label = {
                Text("Search")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search"
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(onSearch = {
                focusManager.clearFocus()
                onExecuteSearch(viewModel,text)
            }),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                onExecuteSearch(viewModel,text)
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 10.dp)
        ) {
            Text(text = "SHOW RESULT")
        }

        val books by viewModel.books.collectAsState()
        if (books.isNotEmpty()) {
            LazyColumn(
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
        } else {
            Text(
                text = "NO RESULT FOUND!!!",
                fontSize = 30.sp
            )
        }
    }

}

fun onExecuteSearch(viewModel: MainViewModel,text:String) {
    viewModel.getBooks(text)
}


