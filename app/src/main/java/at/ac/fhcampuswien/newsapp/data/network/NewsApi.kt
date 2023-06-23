package at.ac.fhcampuswien.newsapp.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NewsApi {

    private val BASE_URL = "https://newsapi.org/v2/"

    //used to convert Json to kotlin data class
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    //used to log the HTTP request and response data when the app is in debug mode.
    private val logging = HttpLoggingInterceptor()

    //to add the API key header to each request and the logging interceptor.
    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor{chain->
           val builder = chain.request().newBuilder()
                //TODO please change the API key to yours
               builder.header("X-Api-Key","ca107e63e79349f88f3dea1d5f588d2a")
               return@Interceptor chain.proceed(builder.build())
            }
        )

        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)

    }.build()


    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    //To use the NewsApi ,we need access the retrofitService property to interact with the News API
    // through the NewsService interface
    val retrofitService: NewsService by lazy { retrofit.create(NewsService::class.java) }
}