package com.example.myapplication.router

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.screen.DetailUseScreen
import com.example.myapplication.screen.FavoriteScreen
import com.example.myapplication.screen.HomeScreen
import com.example.myapplication.screen.component.DestinationInputScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
){
    NavHost(navController, startDestination = Screen.Home.route){
        composable(Screen.Home.route) {
            HomeScreen(paddingValues)
        }
        composable(Screen.UseDetail.route) {
            DetailUseScreen(paddingValues)
        }
        composable(Screen.Favorite.route) {
            FavoriteScreen(paddingValues)
        }
        composable<DestinationInputRoute> {
            DestinationInputScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onVoiceClick = {

                },
                onManualClick = {

                }
            )
        }
    }
}