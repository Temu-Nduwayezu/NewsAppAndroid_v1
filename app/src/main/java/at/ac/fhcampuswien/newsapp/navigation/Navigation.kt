package at.ac.fhcampuswien.newsapp.navigation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import at.ac.fhcampuswien.newsapp.data.models.Article
import at.ac.fhcampuswien.newsapp.data.models.BottomMenuScreen
import at.ac.fhcampuswien.newsapp.ui.screen.*
import at.ac.fhcampuswien.newsapp.viewmodels.MainViewModel


@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel
) {
    val articles = mutableListOf(Article())
    val topArticles = mainViewModel.newsResponse.collectAsState().value.articles
    articles.addAll(topArticles ?: listOf())

    NavHost(navController, startDestination = Screen.Splash.route, Modifier.padding(paddingValues)) {
        // create a queryState and set query value from viewmodel,pass in querystate into bottomNavigation
        val queryState = mutableStateOf(mainViewModel.query.value)
        bottomNavigation(navController, articles, queryState, mainViewModel)
        //parameters in the same order as in the NavGraphBuilder.bottomNavigation function
        /**
         * The route extracts the index from the navigation arguments
         * and uses it to retrieve the corresponding article from the list of articles.
         */
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Detail.route + "/{index}",
            arguments = listOf( //ListOf refers to the arguments parameter
                navArgument("index") { type = NavType.IntType }
            )) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")

            // original top articles.
            index?.let {
                articles.clear()
                articles.addAll(topArticles ?: listOf())

                val article = articles[index]
                DetailScreen(article, scrollState, navController)
            }
        }
    }
}

//function bottomNavigation has 4 parameters in turn: navController, articles, query, viewModel
//if we want to pass the parameters without names one by one, we must find that the 1st parameter
//will correspond to the 1st, 2nd, 3rd, and 4th parameters of the function in that order

fun NavGraphBuilder.bottomNavigation(
    navController: NavController, articles: List<Article>, query: MutableState<String>,
    viewModel: MainViewModel
) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController, articles, query, viewModel)
    }

    composable(BottomMenuScreen.Sources.route) {
        Sources(viewModel)
    }
}