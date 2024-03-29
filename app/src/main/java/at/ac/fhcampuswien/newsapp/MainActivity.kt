package at.ac.fhcampuswien.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import at.ac.fhcampuswien.newsapp.ui.NewsApp
import at.ac.fhcampuswien.newsapp.ui.theme.NewsAppTheme
import at.ac.fhcampuswien.newsapp.viewmodels.MainViewModel
import at.ac.fhcampuswien.newsapp.workmanager.PushNotificationWorkScheduler


class MainActivity : ComponentActivity() {
    private val pushNotificationWorkScheduler = PushNotificationWorkScheduler()
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pushNotificationWorkScheduler.scheduleNotificationWork(application)
        viewModel.getTopArticles()
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NewsApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewsAppTheme {

    }
}