package at.ac.fhcampuswien.newsapp

import Repository
import android.app.Application
import at.ac.fhcampuswien.newsapp.data.network.NewsApi
import at.ac.fhcampuswien.newsapp.data.network.NewsController

class MainApp:Application() {

    private val newsController by lazy {
        NewsController(NewsApi.retrofitService)
    }

    val repository by lazy {
        Repository(newsController)
    }


}