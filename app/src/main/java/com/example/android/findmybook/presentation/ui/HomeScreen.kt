package com.example.android.findmybook.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.android.findmybook.R
import com.example.android.findmybook.presentation.ui.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {
    Button(onClick = {
        navigator.navigate(SearchScreenDestination)
    }) {
        Image(painter = painterResource(id = R.drawable.ic_baseline_search_24), contentDescription = "")
    }
}