package at.ac.fhcampuswien.newsapp.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.navigation.*
import androidx.navigation.compose.rememberNavController
import at.ac.fhcampuswien.newsapp.viewmodels.MainViewModel
import at.ac.fhcampuswien.newsapp.components.Menu
import at.ac.fhcampuswien.newsapp.navigation.Navigation


@Composable
fun NewsApp(mainViewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()

    MainScreen(navController, scrollState, mainViewModel)
}

@Composable
fun MainScreen(
    navController: NavHostController,
    scrollState: ScrollState,
    mainViewModel: MainViewModel
) {
    Scaffold(bottomBar = {
        Menu(navController)
    }) {
        Navigation(navController, scrollState, paddingValues = it, mainViewModel)
    }
}
