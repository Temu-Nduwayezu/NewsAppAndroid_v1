package at.ac.fhcampuswien.newsapp.data.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomMenuScreen(val route: String, val icon: ImageVector, val title: String) {

   object TopNews : BottomMenuScreen("top news", icon = Icons.Outlined.Home, "Top News")
    object Sources : BottomMenuScreen("sources", icon = Icons.Outlined.Info, "Sources")
}
