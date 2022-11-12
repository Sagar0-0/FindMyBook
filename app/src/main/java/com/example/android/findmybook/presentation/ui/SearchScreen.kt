package com.example.android.findmybook.presentation.ui

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.findmybook.viewmodels.MainViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun SearchScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel ?=null
) {

    var text by remember {
        mutableStateOf("")
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
                viewModel?.getBooks(text)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "SHOW RESULT")
        }

        val books by viewModel?.books!!.collectAsState()
        LazyColumn(modifier = Modifier.fillMaxHeight()){
            items(items = books, itemContent = {item->
                Text(text = item.title)
                Spacer(modifier = Modifier
                    .height(5.dp)
                    .background(color = Color.Red))
            })
        }
    }
}