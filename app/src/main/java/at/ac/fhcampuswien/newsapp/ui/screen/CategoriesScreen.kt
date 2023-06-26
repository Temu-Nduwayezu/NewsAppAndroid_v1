package at.ac.fhcampuswien.newsapp.ui.screen





import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.ac.fhcampuswien.newsapp.R
import at.ac.fhcampuswien.newsapp.components.ArticleContent
import at.ac.fhcampuswien.newsapp.components.SearchBar
import at.ac.fhcampuswien.newsapp.data.models.Article
import at.ac.fhcampuswien.newsapp.data.models.ScreenType
import at.ac.fhcampuswien.newsapp.data.models.getAllArticleCategory
import at.ac.fhcampuswien.newsapp.viewmodels.MainViewModel
import com.skydoves.landscapist.coil.CoilImage


@Composable
fun CategoriesScreen(
    onFetchCategory: (String) -> Unit = {},
    viewModel: MainViewModel,
    navController: NavController,
) {

    val tabsItems = getAllArticleCategory()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchedArticles by viewModel.searchedNewsResponse.collectAsState()
    val fetchedArticles by viewModel.getArticleByCategory.collectAsState()
    //  val queryState: MutableState<String> by viewModel.query.collectAsState()
    val queryState:   MutableState<String> = remember { mutableStateOf("") }
    val selectedCategoryValue by viewModel.selectedCategory.collectAsState()
// Call the getArticlesByCategory function when the selected category changes
    LaunchedEffect(selectedCategory) {
        selectedCategory?.let { category ->
            viewModel.getArticlesByCategory(category.categoryName)
        }
    }
    Column {
      //   SearchBar(query = queryState, viewModel = viewModel)
        LazyRow {
            items(tabsItems.size) { index ->
                val category = tabsItems[index]
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
                navController.navigate(Screen.Detail.withUrlId(article.id))
            }
        )
    }
}

//    val query: MutableState<String> = remember { mutableStateOf("") }



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
