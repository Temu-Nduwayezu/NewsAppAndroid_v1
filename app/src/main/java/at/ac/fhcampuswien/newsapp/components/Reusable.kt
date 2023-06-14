package at.ac.fhcampuswien.newsapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import at.ac.fhcampuswien.newsapp.R
import at.ac.fhcampuswien.newsapp.data.models.Article
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun ArticleContent(
    articles: List<Article>,
    onArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn{
       items(articles){article->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onArticleClick(article) },
                elevation = 2.dp,
                border = BorderStroke(2.dp,
                    color = colorResource(id = R.color.purple_500))) {
                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                    CoilImage(imageModel = article.urlToImage,
                            modifier = Modifier.size(100.dp),placeHolder = painterResource(
                        id = R.drawable.news),error = painterResource(
                        id = R.drawable.news) )
                    Column(modifier.padding(8.dp) ) {
                        Text(text = article.title ?: "Not Available", fontWeight = FontWeight.Bold,
                            maxLines = 3,overflow = TextOverflow.Ellipsis)

                    }
                }
            }
        }
    }
}

