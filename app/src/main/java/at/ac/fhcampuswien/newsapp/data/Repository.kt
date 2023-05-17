import at.ac.fhcampuswien.newsapp.data.network.NewsController

/**
 * The Repository class serves as a higher-level abstraction over the NewsController. the repository that encapsulates the data layer,
 * allowing the ViewModel or other components to interact with the data without knowing the underlying implementation details
 */
class Repository(private val newsController: NewsController) {

    suspend fun getArticles() = newsController.getArticles("us")

    suspend fun getArticlesBySource(source:String) = newsController.getArticleBySource(source = source)

    suspend fun getSearchedArticles(query:String) = newsController.getSearchedArticles(query = query)

}