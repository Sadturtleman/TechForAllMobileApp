package com.example.myapplication.router

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector){
    data object Home: Screen("home", "Home", Icons.Default.Home)
    data object Favorite: Screen("favorite", "Favorite", Icons.Default.Place)
    data object UseDetail: Screen("useDetail", "UseDetail", Icons.Default.DateRange)
}