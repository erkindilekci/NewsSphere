package com.erkindilekci.newssphere.util

sealed class Screen(val route: String) {
    object ListScreen : Screen("list_screen")
    object DetailScreen : Screen("detail_screen")
    object SearchScreen : Screen("search_screen")
}
