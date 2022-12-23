package com.example.android.findmybook.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.android.findmybook.presentation.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val focusManager: FocusManager = LocalFocusManager.current
    var text by rememberSaveable {
        mutableStateOf(viewModel.searchTitle.value)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier
                    .weight(0.9f),
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
                    onExecuteSearch(viewModel, text)
                }),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface)
            )
            Button(onClick = {
                focusManager.clearFocus()
                onExecuteSearch(viewModel, text)
            }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search"
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

//        val books by viewModel.books.collectAsState()
//        if (books.isNotEmpty()) {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .padding(
//                        start = 10.dp,
//                        end = 10.dp
//                    )
//            ) {
//                items(items = books, itemContent = { item ->
//                    BookItem(item)
//                    Spacer(modifier = Modifier.padding(10.dp))
//                })
//            }
//        } else {
//            Text(
//                text = "NO RESULT FOUND!!!",
//                fontSize = 30.sp
//            )
//        }
    }
}

fun onExecuteSearch(viewModel: MainViewModel, text: String) {
    viewModel.searchBookByTitle(text)
}


