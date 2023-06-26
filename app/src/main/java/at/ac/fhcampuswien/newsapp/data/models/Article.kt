package at.ac.fhcampuswien.newsapp.data.models

import at.ac.fhcampuswien.newsapp.utils.getArticleIdFromUrl


data class Article(
    val source : Source? = null,
    val author : String? = null,
    val title : String? = null,
    val description : String? = null,
    val url : String? = null,
    val urlToImage : String? = null,
    val publishedAt : String? = null,
    val content : String? = null
)
{
    val id: String
        get() = url?.let { getArticleIdFromUrl(it) } ?: ""
}

