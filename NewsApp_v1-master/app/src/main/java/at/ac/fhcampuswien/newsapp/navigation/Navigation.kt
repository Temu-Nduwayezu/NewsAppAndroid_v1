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
import at.ac.fhcampuswien.newsapp.ui.screen.DetailScreen
import at.ac.fhcampuswien.newsapp.ui.screen.Sources
import at.ac.fhcampuswien.newsapp.ui.screen.SplashScreen
import at.ac.fhcampuswien.newsapp.ui.screen.TopNews
import at.ac.fhcampuswien.newsapp.viewmodels.MainViewModel


@Composable
fun Navigation(navController: NavHostController, scrollState: ScrollState, paddingValues: PaddingValues, mainViewModel: MainViewModel) {
    val articles = mutableListOf(Article())
    val topArticles = mainViewModel.newsResponse.collectAsState().value.articles
    articles.addAll(topArticles ?: listOf())
    NavHost(navController = navController, startDestination = "SplashScreen",modifier = Modifier.padding(paddingValues)) {
        // create a queryState and set query value from viewmodel,pass in querystate into bottomNavigation
        val queryState = mutableStateOf(mainViewModel.query.value)
        bottomNavigation(navController = navController, articles,viewModel = mainViewModel,query = queryState)
        /**
         * The route extracts the index from the navigation arguments
         * and uses it to retrieve the corresponding article from the list of articles.
         */
        composable("SplashScreen") {
            SplashScreen(navController = navController)
        }

        composable("Detail/{index}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType }
            )) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            //Depending on the search query (queryState.value),
            // the article list is filled with either search results
            // or the original top articles.
            index?.let {

                    articles.clear()
                    articles.addAll(topArticles?: listOf())

                val article = articles[index]
                DetailScreen(article, scrollState, navController)
            }
        }

    }
}

fun NavGraphBuilder.bottomNavigation(navController: NavController, articles:List<Article>, query: MutableState<String>,
                                     viewModel: MainViewModel
) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController,articles,query,viewModel = viewModel)
    }

    composable(BottomMenuScreen.Sources.route) {
        Sources(viewModel = viewModel)
    }
}