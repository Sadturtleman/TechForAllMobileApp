package com.example.myapplication.router

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myapplication.R
import kotlinx.serialization.Serializable

sealed class Screen(
    val route: @Serializable Any,
    val routeString: String,
    val title: String,
    val icon: Int
) {
    data object Home : Screen(HomeRoute, HomeRoute::class.qualifiedName!!, "홈", R.drawable.home)
    data object Favorite : Screen(FavoriteRoute, FavoriteRoute::class.qualifiedName!!, "즐겨찾기", R.drawable.favorite)
    data object UseDetail : Screen(UseDetailRoute, UseDetailRoute::class.qualifiedName!!, "이용 내역", R.drawable.calendar)
}