package at.ac.fhcampuswien.newsapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import at.ac.fhcampuswien.newsapp.viewmodels.MainViewModel
import at.ac.fhcampuswien.newsapp.data.models.Article
import com.skydoves.landscapist.coil.CoilImage
import at.ac.fhcampuswien.newsapp.R

@Composable
fun TopNews(navController: NavController, articles:List<Article>, query: MutableState<String>,
            viewModel: MainViewModel
) {
    Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally) {


        //val searchedText = query.value
        val resultList = mutableListOf<Article>()
        resultList.addAll(articles)
        LazyColumn {
            items(resultList.size) { index ->
                TopNewsItem(article = resultList[index],
                    onNewsClick = { navController.navigate(Screen.Detail.route + "/$index") }
                )
            }
        }
    }
}

@Composable
fun TopNewsItem(article: Article, onNewsClick: () -> Unit = {},) {
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
        Column(modifier = Modifier
            .wrapContentHeight()
            .padding(top = 16.dp, start = 16.dp),verticalArrangement = Arrangement.SpaceBetween) {

            Spacer(modifier = Modifier.height(100.dp))
            Text(text = article.title?:"Not Available", color = Color.White, fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }}
}

