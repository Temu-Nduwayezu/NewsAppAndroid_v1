package at.ac.fhcampuswien.newsapp.data.network

import at.ac.fhcampuswien.newsapp.data.models.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//The NewsController class is a wrapper around the NewsService
// that handles API calls using Kotlin coroutines
class NewsController(private val service: NewsService) {

    /**
     * The class is designed to interact with the News API by calling the methods defined in the NewsService interface.
     *  It is a suspend function that performs the API call on the IO dispatcher using the withContext(Dispatchers.IO) function.
     *  This ensures that the network call is performed on a background thread,
     *  preventing the UI from freezing
     */
    suspend fun getArticles(country:String): NewsResponse = withContext(Dispatchers.IO){
        service.getTopArticles(country)
    }

    suspend fun getArticlesByCategory(category: String): NewsResponse = withContext(Dispatchers.IO) {
        service.getArticlesByCategory(category)
    }


    suspend fun getSearchedArticles(query: String):NewsResponse = withContext(Dispatchers.IO){
        service.searchArticles(query)
    }


   suspend fun getArticleBySource(source:String):NewsResponse= withContext(Dispatchers.IO){
        service.getArticlesBySources(source)
    }

}