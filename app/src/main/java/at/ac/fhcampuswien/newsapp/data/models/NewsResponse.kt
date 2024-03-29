package at.ac.fhcampuswien.newsapp.data.models

data class NewsResponse(val status : String? = null,
                        val totalResults : Int? = null,
                        val articles : List<Article>? = null)
