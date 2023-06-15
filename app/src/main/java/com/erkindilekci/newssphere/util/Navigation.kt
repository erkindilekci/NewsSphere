package com.erkindilekci.newssphere.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.erkindilekci.newssphere.presentation.detailscreen.DetailsScreen
import com.erkindilekci.newssphere.presentation.listscreen.ListScreen
import com.erkindilekci.newssphere.presentation.savednewsscreen.SavedNewsScreen
import com.erkindilekci.newssphere.presentation.searchscreen.SearchScreen

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
        composable(
            "${Screen.DetailScreen.route}/{newTitle}",
            arguments = listOf(navArgument("newTitle") { type = NavType.StringType })
        ) {
            DetailsScreen(navController = navController)
        }
        composable(Screen.SearchScreen.route) {
            SearchScreen(navController = navController)
        }
        composable(Screen.SavedNewsScreen.route) {
            SavedNewsScreen(navController = navController)
        }
    }
}
