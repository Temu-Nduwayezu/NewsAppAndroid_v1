package at.ac.fhcampuswien.newsapp.ui.screen

import at.ac.fhcampuswien.newsapp.utils.getArticleIdFromUrl
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

//sealed class-does not allow it to be inherited from it. We can have certain successors of this class,
//we can't do anything with it, we only have the objects we created and nothing else

const val DETAIL_ARGUMENT_KEY = "articleId"

sealed class Screen(val route: String, val title: String) {
    object Splash : Screen("splash", "Splash Screen")
    object Detail : Screen("detail/{$DETAIL_ARGUMENT_KEY}", "Detail Screen") {
        fun withUrlId(id: String): String {
            val articleId = getArticleIdFromUrl(id?: "")  // we have convert our article url to id
            return this.route.replace("{$DETAIL_ARGUMENT_KEY}", articleId)
            // return Screen.Detail.withId(articleId) // the do the same thing
        }
    }
}