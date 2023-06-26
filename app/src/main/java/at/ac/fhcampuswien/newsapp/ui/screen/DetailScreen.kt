package at.ac.fhcampuswien.newsapp.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import at.ac.fhcampuswien.newsapp.R
import at.ac.fhcampuswien.newsapp.components.ArticleContent
import at.ac.fhcampuswien.newsapp.data.models.Article
import at.ac.fhcampuswien.newsapp.data.models.ScreenType
import at.ac.fhcampuswien.newsapp.viewmodels.MainViewModel
import com.skydoves.landscapist.coil.CoilImage


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    articleId: String?,
    currentScreen: ScreenType
    //url: String?,
) {
    Log.d("DetailsScreen", "Received article ID: $articleId")


    val newsResponse by mainViewModel.newsResponse.collectAsState()
    val isLoading by mainViewModel.isLoading.collectAsState()
    val searchedNewsResponse by mainViewModel.searchedNewsResponse.collectAsState()
    val getArticleByCategory by mainViewModel.getArticleByCategory.collectAsState()

/*
    val article = index?.let { newsResponse.articles?.getOrNull(it) }
    var article= index?.let  {searchedNewsResponse.articles?.getOrNull((it))}
     var article= index?.let  {getArticleByCategory.articles?.getOrNull((it))}*/

    val article = when (currentScreen) {
        ScreenType.TOP_NEWS -> articleId?.let { id ->
            newsResponse.articles?.find { it.id == id }
        }
        ScreenType.CATEGORY_NEWS -> articleId?.let { id ->
            getArticleByCategory.articles?.find { it.id == id }
        }
        ScreenType.SEARCH_NEWS -> articleId?.let { id ->
            searchedNewsResponse.articles?.find { it.id == id }
        }
        else -> null

    }
        article?.let {
            val scrollState = rememberScrollState()
            val context = LocalContext.current

            Scaffold(
                topBar = {
                    DetailTopAppBar(
                        onSharePressed = { share(context, article) },
                        onBackPressed = { navController.popBackStack() }
                    )
                }
            ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CoilImage(
                    imageModel = article.urlToImage,
                    contentScale = ContentScale.Crop,
                    error = ImageBitmap.imageResource(R.drawable.news),
                    placeHolder = ImageBitmap.imageResource(R.drawable.news)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InfoWithIcon(Icons.Default.Edit, info = article.author ?: "Not Available")
                }
                Text(text = article.title ?: "Not Available", fontWeight = FontWeight.Bold)
                Text(
                    text = article.description ?: "Not Available",
                    modifier = Modifier.padding(top = 16.dp)
                )

            }
        }

    }


    }

@Composable
fun DetailTopAppBar(onSharePressed: () -> Unit, onBackPressed: () -> Unit = {})
{
    val navController = rememberNavController()
    TopAppBar(title = { Text(text = Screen.Detail.title, fontWeight = FontWeight.SemiBold) },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back")
            }
        },
        actions = {
            IconButton(onClick = { onSharePressed() }) {
                Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
            }
        }
    )
}

//@Composable is not accepted, 39.gives an error->because onSharePressed = { share(context, article) }
//accepts only NOT @Composable function, but ordinary ones. onSharePressed function is a normal function.
fun share(context: Context, article: Article) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_SUBJECT, article.title)
    intent.putExtra(Intent.EXTRA_TEXT, article.url)

    //create a choose to chooser app from which shared
    val chooser = Intent.createChooser(intent, "Share using...")
    context.startActivity(chooser) //the functionality is sewn, the task of which is to share
}


@Composable
fun InfoWithIcon(icon: ImageVector, info: String) {
    Row {
        Icon(
            icon,
            contentDescription = "Author",
            modifier = Modifier.padding(end = 8.dp),
            colorResource(
                id = R.color.purple_500
            )
        )
        Text(text = info)
    }
}
