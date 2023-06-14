package at.ac.fhcampuswien.newsapp.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.newsapp.R
import at.ac.fhcampuswien.newsapp.components.ArticleContent
import at.ac.fhcampuswien.newsapp.data.models.getAllArticleCategory
import at.ac.fhcampuswien.newsapp.viewmodels.MainViewModel


@Composable
fun CategoriesScreen(
    onFetchCategory: (String) -> Unit = {},
    viewModel: MainViewModel,
    navController: NavController
) {
    val tabsItems = getAllArticleCategory()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val fetchedArticles by viewModel.getArticleByCategory.collectAsState()

    LaunchedEffect(key1 = selectedCategory) {
        selectedCategory?.let { category ->
            viewModel.getArticlesByCategory(category.categoryName)
        }
    }

    Column {
        LazyRow {
            items(tabsItems.size) {
                val category = tabsItems[it]
                CategoryTab(
                    category = category.categoryName,
                    onFetchCategory = onFetchCategory,
                    isSelected = selectedCategory == category
                )
            }
        }

        ArticleContent(
            articles = fetchedArticles.articles.orEmpty(),
            onArticleClick = { article ->
                val index = fetchedArticles.articles?.indexOf(article) ?: -1
                if (index != -1) {
                    navController.navigate(Screen.Detail.withId(index))
                } else {
                    Log.e("CategoriesScreen", "Selected article not found in fetched list")
                }
            }
        )
    }
}



@Composable
fun CategoryTab(
    category: String,
    isSelected: Boolean = false,
    onFetchCategory: (String) -> Unit
) {


    val background =
        if (isSelected) colorResource(id = R.color.purple_200) else colorResource(id = R.color.purple_700)
    Surface(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 16.dp)
            .clickable {
                onFetchCategory(category)
            },
        shape = MaterialTheme.shapes.small,
        color = background
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
        )

    }
}
