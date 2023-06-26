package at.ac.fhcampuswien.newsapp.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import at.ac.fhcampuswien.newsapp.data.models.BottomMenuScreen
import at.ac.fhcampuswien.newsapp.R

@Composable
fun Menu(navController:NavController) {
    val menuItems = listOf(
        BottomMenuScreen.TopNews,
        BottomMenuScreen.Categories,
        BottomMenuScreen.Sources
    )
    BottomNavigation(contentColor = colorResource(id = R.color.white))
    {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        //  val currentScreen = getCurrentScreen(navController)
        menuItems.forEach { menuItem ->
            val selected = currentRoute == menuItem.route
            BottomNavigationItem(
                label = { Text(text = menuItem.title) },
                alwaysShowLabel = true,
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(menuItem.route) {
                            popUpTo(navController.graph.startDestinationRoute!!) {
                                saveState = true
                                inclusive = true

                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = menuItem.icon,
                        contentDescription = menuItem.title
                    )
                },

                )

        }
    }
}
fun getCurrentScreen(navController: NavController): String? {
    return navController.currentBackStackEntry?.destination?.route
}