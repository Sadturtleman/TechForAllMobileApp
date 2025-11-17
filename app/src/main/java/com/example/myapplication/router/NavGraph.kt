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
import com.example.myapplication.data.model.SearchResult
import com.example.myapplication.screen.DestinationConfirmScreen
import com.example.myapplication.screen.DetailUseScreen
import com.example.myapplication.screen.FavoriteScreen
import com.example.myapplication.screen.HomeScreen
import com.example.myapplication.screen.DestinationInputScreen
import com.example.myapplication.screen.DestinationListScreen
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
        composable<HomeRoute> {
            HomeScreen(
                paddingValues = paddingValues,
                onCallClick = {
                    navController.navigate(DestinationInputRoute)
                }
            )
        }
        composable<UseDetailRoute> {
            DetailUseScreen(paddingValues)
        }
        composable<FavoriteRoute> {
            FavoriteScreen(
                paddingValues = paddingValues,
                navController = navController
            )
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
                onConfirmClick = { navController.navigate(TaxiSearchingRoute) },
                onListClick = {
                    // 🔥 검색어 기반으로 리스트 넘기기
                    val mockResults = listOf(
                        SearchResult("서울역", "서울 중구 한강대로 405"),
                        SearchResult("서울역 버스환승센터", "서울 중구 세종대로 18길"),
                        SearchResult("서울역 1번출구", "서울 중구 청파로 378")
                    )

                    navController.navigate(
                        DestinationListRoute(
                            query = args.placeName,
                            results = mockResults
                        )
                    )
                }
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
                },
                onAutoNext = {
                    navController.navigate(TaxiAssignedRoute)
                }
            )
        }


        composable<TaxiAssignedRoute> {
            TaxiAssignedScreen(
                onCall = { /* TODO: 전화 */ },
                onCancel = { navController.popBackStack() },
                onAutoNext = {
                    navController.navigate(TaxiFinishedRoute)
                }
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

        composable<DestinationListRoute> { entry ->
            val args = entry.toRoute<DestinationListRoute>()

            DestinationListScreen(
                query = args.query,
                resultList = args.results,
                onBackClick = { navController.popBackStack() },
                onItemClick = { selected ->
                    navController.navigate(
                        DestinationConfirmRoute(
                            placeName = selected.placeName,
                            address = selected.address
                        )
                    )
                }
            )
        }
    }
}