package at.ac.fhcampuswien.newsapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.navigation.NavController
import at.ac.fhcampuswien.newsapp.components.ArticleContent
import at.ac.fhcampuswien.newsapp.viewmodels.MainViewModel


@Composable
@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterialScaffoldPaddingParameter")
fun Sources(navController: NavController, mainViewModel: MainViewModel) {
    val items = listOf(
        "Reuters" to "reuters",
        "Bloomberg" to "bloomberg",
        "Business Insider" to "business-insider",
        "Reuters" to "reuters"

    )
    val sourceName by mainViewModel.sourceName.collectAsState()
    // Fetch articles for the selected source

    // Fetch articles for the selected source whenever the sourceName changes
    LaunchedEffect(sourceName) {
        mainViewModel.getArticleBySource()
    }


    val articles = mainViewModel.getArticleBySource.collectAsState().value.articles ?: emptyList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "$sourceName Source") },
                actions = {
                    var menuExpanded by remember { mutableStateOf(false) }

                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                    ) {
                        items.forEach { (displayName, source) ->
                            DropdownMenuItem(
                                onClick = {
                                    mainViewModel.updateSourceName(source)
                                    menuExpanded = false
                                }
                            ) {
                                Text(text = displayName)
                            }
                        }
                    }
                }
            )
        }
    ) {
        ArticleContent(
            articles = articles,
            onArticleClick = { article ->
                navController.navigate(Screen.Detail.withUrlId(article.id))
            }
        )
    }
}