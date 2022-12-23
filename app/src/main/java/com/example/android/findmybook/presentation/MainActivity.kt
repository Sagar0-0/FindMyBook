package com.example.android.findmybook.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android.findmybook.others.Constants.HOME_SCREEN
import com.example.android.findmybook.others.Constants.SEARCH_SCREEN
import com.example.android.findmybook.presentation.ui.HomeScreen
import com.example.android.findmybook.presentation.ui.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController, // the navController created above
                startDestination = HOME_SCREEN // start destination variable needs to match one of the composable screen routes below
            ) {
                composable(route = HOME_SCREEN) { HomeScreen(navController) }
                composable(route = SEARCH_SCREEN) { SearchScreen(navController) }
            }
        }
    }

}