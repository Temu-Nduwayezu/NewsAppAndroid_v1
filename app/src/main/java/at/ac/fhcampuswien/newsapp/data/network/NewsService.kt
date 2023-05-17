package at.ac.fhcampuswien.newsapp.data.network


import at.ac.fhcampuswien.newsapp.data.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {

    @GET("top-headlines")
    suspend fun getTopArticles(@Query("country") country:String): NewsResponse


    @GET("everything")
    suspend fun searchArticles(@Query("q") country:String):NewsResponse


    @GET("everything")
    suspend fun getArticlesBySources(@Query("sources") source:String):NewsResponse

}