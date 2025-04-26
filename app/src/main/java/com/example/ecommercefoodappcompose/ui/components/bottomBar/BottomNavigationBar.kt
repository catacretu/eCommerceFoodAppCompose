package com.example.ecommercefoodappcompose.ui.components.bottomBar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val screens = listOf(
        BottomNavItem.Home,
        BottomNavItem.Cart,
        BottomNavItem.Favourite,
        BottomNavItem.More
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same screen
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected screen
                        restoreState = true
                    }
                },
                label = { Text(screen.title) },
                icon = {
                    Icon(
                        painterResource(id = screen.icon),
                        contentDescription = screen.title
                    )
                },
                // colors = NavigationBarItemDefaults.colors(...)
                alwaysShowLabel = true // Sau false dacă vrei să arăți eticheta doar la selectare
            )
        }
    }
}
