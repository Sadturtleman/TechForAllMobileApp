package com.example.myapplication.router

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.myapplication.screen.DestinationConfirmScreen
import com.example.myapplication.screen.DetailUseScreen
import com.example.myapplication.screen.FavoriteScreen
import com.example.myapplication.screen.HomeScreen
import com.example.myapplication.screen.DestinationInputScreen
import com.example.myapplication.screen.ManualInputScreen
import com.example.myapplication.screen.TaxiAssignedScreen
import com.example.myapplication.screen.TaxiFinishedScreen
import com.example.myapplication.screen.TaxiSearchingScreen
import com.example.myapplication.screen.VoiceListeningScreen
import com.example.myapplication.ui.viewmodel.VoiceViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController, startDestination = Screen.Home.route) {
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
            val viewModel: VoiceViewModel = hiltViewModel()
            DestinationInputScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onVoiceClick = {
                    viewModel.startListening()
                    navController.navigate(VoiceListeningRoute)
                },
                onManualClick = {
                    navController.navigate(ManualInputRoute)
                }
            )
        }

        composable<ManualInputRoute> { entry ->
            ManualInputScreen(
                onBackClick = { navController.popBackStack() },
                onComplete = { inputText ->
                    navController.navigate(
                        DestinationConfirmRoute(
                            placeName = inputText,
                            address = "주소 검색 예정" // 나중에 geocoder 붙이면 자동 변환
                        )
                    )
                }
            )
        }


        composable<DestinationConfirmRoute> { entry ->
            val args = entry.toRoute<DestinationConfirmRoute>()

            DestinationConfirmScreen(
                placeName = args.placeName,
                address = args.address,
                onBackClick = { navController.popBackStack() },
                onConfirmClick = { /* 택시 호출 */ },
                onListClick = { navController.navigate(Screen.Favorite.route) }
            )
        }




        // ----------------------
        // 택시 호출 플로우
        // ----------------------
        composable<TaxiSearchingRoute> {
            TaxiSearchingScreen(
                onChangeAddress = {
                    navController.navigate(DestinationInputRoute)
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }

        composable<TaxiAssignedRoute> {
            TaxiAssignedScreen(
                onCall = { /* TODO */ },
                onCancel = { navController.popBackStack() }
            )
        }

        composable<TaxiFinishedRoute> {
            TaxiFinishedScreen(
                onCall = { /* TODO */ },
                onSaveFavorite = {
                    navController.navigate(Screen.Favorite.route)
                }
            )
        }

        composable<VoiceListeningRoute> {
            val viewModel: VoiceViewModel = hiltViewModel()
            val text by viewModel.text.collectAsState()
            val isListening by viewModel.isListening.collectAsState()

            VoiceListeningScreen(
                text = text,
                isListening = isListening,
                onBackClick = { navController.popBackStack() },
                onStopClick = {
                    viewModel.stopListening()

                    val result = viewModel.text.value   // 예: “서울역”
                    val placeName = result
                    val address = "주소를 찾는 중입니다…"    // 직접 구현 or Naver API

                    navController.navigate(
                        DestinationConfirmRoute(
                            placeName = placeName,
                            address = address
                        )
                    )
                }

            )
        }

    }
}