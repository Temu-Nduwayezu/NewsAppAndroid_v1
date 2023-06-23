package at.ac.fhcampuswien.newsapp.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
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
import androidx.navigation.NavController
import at.ac.fhcampuswien.newsapp.R
import at.ac.fhcampuswien.newsapp.components.ArticleContent
import at.ac.fhcampuswien.newsapp.data.models.Article
import at.ac.fhcampuswien.newsapp.viewmodels.MainViewModel
import com.skydoves.landscapist.coil.CoilImage


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(article: Article, scrollState: ScrollState, navController: NavController,  viewModel:MainViewModel) {
    val context = LocalContext.current //we pass it to 81. as a parameter so that there is access to MainActivity
    //LocalContext refers to the context from which we can start the Activity
    //We can call LocalContext only from the @Composable element.






    Scaffold(topBar = {
        DetailTopAppBar(
            onSharePressed = { share(context, article) },
            onBackPressed = { navController.popBackStack() })

    }) {
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

@Composable
fun DetailTopAppBar(onSharePressed: () -> Unit, onBackPressed: () -> Unit = {}) {
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
