package com.erkindilekci.newssphere.presentation.util

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.erkindilekci.newssphere.R
import com.erkindilekci.newssphere.util.Screen

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier.height(80.dp),
        contentColor = MaterialTheme.colorScheme.onBackground,

        ) {
        NavigationBarItem(
            label = {
                Text(text = "Breaking News")
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.newspaper),
                    contentDescription = null
                )
            },
            selected = currentDestination?.hierarchy?.any { destination ->
                destination.route == Screen.ListScreen.route
            } == true,
            onClick = {
                navController.navigate(Screen.ListScreen.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )

        NavigationBarItem(
            label = {
                Text(text = "Saved News")
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.save),
                    contentDescription = null
                )
            },
            selected = currentDestination?.hierarchy?.any { destination ->
                destination.route == Screen.SavedNewsScreen.route
            } == true,
            onClick = {
                navController.navigate(Screen.SavedNewsScreen.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
    }
}
