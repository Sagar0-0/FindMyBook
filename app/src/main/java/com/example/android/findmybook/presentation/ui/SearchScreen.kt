package com.example.android.findmybook.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.findmybook.presentation.viewmodels.MainViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.getBooks(text)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "SHOW RESULT")
        }

        val books by viewModel.books.collectAsState()
        if(books.isNotEmpty()){
            LazyColumn(modifier = Modifier.fillMaxHeight()){
                items(items = books, itemContent = {item->
                    Text(text = item.volumeInfo.title)
                    Spacer(modifier = Modifier
                        .height(5.dp)
                        .background(color = Color.Red))
                })
            }
        }else{
            Text(text = "NO RESULT FOUND!!!",
            fontSize = 30.sp
            )
        }

    }
}