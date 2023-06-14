package at.ac.fhcampuswien.newsapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.newsapp.MainApp
import at.ac.fhcampuswien.newsapp.data.models.ArticleCategory
import at.ac.fhcampuswien.newsapp.data.models.NewsResponse
import at.ac.fhcampuswien.newsapp.data.models.getArticleCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = getApplication<MainApp>().repository

    private val _newsResponse = MutableStateFlow(NewsResponse())
    val newsResponse: StateFlow<NewsResponse>
        get() = _newsResponse

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getTopArticles(){
        _isLoading.value  = true
        viewModelScope.launch(Dispatchers.IO) {
            _newsResponse.value = repository.getArticles()
        }
        _isLoading.value = false
    }

    private val _selectedCategory:MutableStateFlow<ArticleCategory?> = MutableStateFlow(null)
    val  selectedCategory:StateFlow<ArticleCategory?>
        get() = _selectedCategory

    private val _getArticleByCategory = MutableStateFlow(NewsResponse())
    val getArticleByCategory: StateFlow<NewsResponse>
        get() = _getArticleByCategory

    fun getArticlesByCategory(category:String){
        _isLoading.value  = true
        viewModelScope.launch(Dispatchers.IO) {
            _getArticleByCategory.value = repository.getArticleByCategory(category =category )
        }
        _isLoading.value = false
    }

    fun onSelectedCategoryChanged(category:String){
        val newCategory = getArticleCategory(category)
        _selectedCategory.value = newCategory
    }

    //start
    val query = MutableStateFlow("")

}