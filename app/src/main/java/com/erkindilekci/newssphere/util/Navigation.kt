package com.erkindilekci.newssphere.util

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erkindilekci.newssphere.presentation.detailscreen.DetailsScreen
import com.erkindilekci.newssphere.presentation.listscreen.ListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.ListScreen.route
    ) {
        composable(Screen.ListScreen.route) {
            ListScreen(navController = navController)
        }
        composable("${Screen.DetailScreen.route}/{newTitle}") {
            it.arguments?.getString("newTitle")?.let { title ->
                DetailsScreen(title = title, navController = navController)
            }
        }
    }
}
