package at.ac.fhcampuswien.newsapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.newsapp.R
import at.ac.fhcampuswien.newsapp.components.SearchBar
import at.ac.fhcampuswien.newsapp.data.models.Article
import at.ac.fhcampuswien.newsapp.data.models.ScreenType
import at.ac.fhcampuswien.newsapp.viewmodels.MainViewModel
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun TopNews(
    navController: NavController,
    viewModel: MainViewModel
) {
  // val articles = mutableListOf<Article>()

    val topArticles by viewModel.newsResponse.collectAsState()
    val searchedArticles by viewModel.searchedNewsResponse.collectAsState()
    val query: MutableState<String> = remember { mutableStateOf("") }
    val displayedArticles = remember { mutableStateListOf<Article>() }

    //val currentScreen by viewModel.currentScreen.collectAsState()


    Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally) {

        SearchBar(query = query, viewModel = viewModel)
        val searchedText = query.value
        //If the search query is not empty,
        // it filters the articles based on the search query using the viewModel.searchedNewsResponse LiveData.
         if (searchedText != "" && query.value.isNotEmpty() ) {
            //collect searchedNewsResponse from viwModel
             displayedArticles.clear()
             displayedArticles.addAll(searchedArticles.articles ?: emptyList())
         }  else {
             displayedArticles.clear()
             displayedArticles.addAll(topArticles.articles ?: emptyList())
         }
        LazyColumn {
            itemsIndexed(displayedArticles ?: emptyList()) { _, article ->
                TopNewsItem(
                    article = article,
                    onNewsClick = {
                        // Store the selected article ID in the rememberSaveable variable
                       // val url = article.url ?: "" // Provide a default non-null value
                       // val encodedUrl = URLEncoder.encode(article.url, StandardCharsets.UTF_8.toString())
                        navController.navigate(Screen.Detail.withUrlId(article.id))
                    }
                )

            }
        }

        }
    }





@Composable
fun TopNewsItem(article: Article, onNewsClick: () -> Unit = {}) {
    Box(modifier = Modifier
        .height(200.dp)
        .padding(8.dp)
        .clickable {

              onNewsClick()

        }) {
        //CoilImage allows  to easily load and display images from various sources, such as URLs, resources,
        // and more, directly in your Jetpack Compose UI.
        // It handles caching, request cancelling,
        // and other common tasks related to image loading.
        CoilImage(
            imageModel = article.urlToImage,
            // Crop, Fit, Inside, FillHeight, FillWidth, None
            contentScale = ContentScale.Crop,
            error = ImageBitmap.imageResource(R.drawable.news),
            // shows a placeholder ImageBitmap when loading.
            placeHolder = ImageBitmap.imageResource(R.drawable.news)
        )
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 16.dp, start = 16.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {

            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = article.title ?: "Not Available",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

