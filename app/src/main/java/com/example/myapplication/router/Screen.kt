package com.example.myapplication.router

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: Any, val title: String, val icon: ImageVector){
    data object Home: Screen(HomeRoute, "Home", Icons.Default.Home)
    data object Favorite: Screen(FavoriteRoute, "Favorite", Icons.Default.Place)
    data object UseDetail: Screen(UseDetailRoute, "UseDetail", Icons.Default.DateRange)
}