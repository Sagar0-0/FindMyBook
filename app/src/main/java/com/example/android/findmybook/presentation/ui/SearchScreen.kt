package com.example.android.findmybook.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.getBooks(text)
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


