package at.ac.fhcampuswien.newsapp.utils

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun getArticleIdFromUrl(articleUrl: String): String {
    val encodedUrl = URLEncoder.encode(articleUrl, StandardCharsets.UTF_8.toString())
    val segments = encodedUrl.split("/")
    return segments.lastOrNull()?.removeSuffix("/") ?: ""
}