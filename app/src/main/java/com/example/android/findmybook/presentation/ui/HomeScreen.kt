package com.example.android.findmybook.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.android.findmybook.R
import com.example.android.findmybook.others.Constants.SEARCH_SCREEN

@Composable
fun HomeScreen(
    navController: NavController
) {
    Button(onClick = {
        navController.navigate(SEARCH_SCREEN)
    }) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_search_24),
            contentDescription = ""
        )
    }
}