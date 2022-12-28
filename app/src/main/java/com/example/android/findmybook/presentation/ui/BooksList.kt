package com.example.android.findmybook.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android.findmybook.others.Constants.PAGE_SIZE
import com.example.android.findmybook.others.Status.*
import com.example.android.findmybook.presentation.BookSearchEvent.NextPageSearch
import com.example.android.findmybook.presentation.MainViewModel

@Composable
fun BooksList(
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
    onUpdateError: (String) -> Unit
) {
    val booksList = viewModel.books.observeAsState().value
    val pageIndex = viewModel.pageIndex.value

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
        when (booksList?.status) {
            SUCCESS -> {
                LazyColumn(
                    state = rememberLazyListState(),
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(
                            start = 10.dp,
                            end = 10.dp
                        )
                ) {
                    itemsIndexed(items = booksList.data!!) { index, item ->
                        if ((index + 1) >= (pageIndex + 1 * PAGE_SIZE) && !viewModel.noMoreData.value) {
                            viewModel.onTriggerEvent(NextPageSearch)
                        }
                        BookItem(book = item)
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                }
            }
            LOADING -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoadingAnimation()
                }
            }
            ERROR -> {
                booksList.message?.let { onUpdateError(it) }
            }
            else -> {
                Text(text = "Search for your favourite books here")
            }
        }
    }
}