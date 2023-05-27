package at.ac.fhcampuswien.newsapp.ui.screen

//sealed class-does not allow it to be inherited from it. We can have certain successors of this class,
//we can't do anything with it, we only have the objects we created and nothing else
sealed class Screen(val route: String, val title: String) {
    object Splash : Screen("splash", "Splash Screen")
    object Detail : Screen("detail", "Detail Screen")
}