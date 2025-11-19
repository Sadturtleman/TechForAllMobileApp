package com.example.myapplication.router

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.myapplication.screen.DestinationConfirmScreen
import com.example.myapplication.screen.HomeScreen
import com.example.myapplication.screen.DestinationInputScreen
import com.example.myapplication.screen.DestinationListScreen
import com.example.myapplication.screen.DetailUseScreen
import com.example.myapplication.screen.FavoriteScreen
import com.example.myapplication.screen.ManualInputScreen
import com.example.myapplication.screen.TaxiAssignedScreen
import com.example.myapplication.screen.TaxiFinishedScreen
import com.example.myapplication.screen.TaxiSearchingScreen
import com.example.myapplication.screen.VoiceListeningScreen
import com.example.myapplication.ui.viewmodel.SearchViewModel
import com.example.myapplication.ui.viewmodel.VoiceViewModel
import kotlinx.coroutines.delay

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {

        // ---------------------------
        // 홈 / 기타 화면
        // ---------------------------
        composable<HomeRoute> {
            HomeScreen(
                paddingValues = paddingValues,
                onCallClick = { navController.navigate(SearchGraphRoute) }
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
        // ---------------------------
        // 🔥 SEARCH GRAPH (SearchViewModel 공유)
        // ---------------------------
        navigation<SearchGraphRoute>(
            startDestination = DestinationInputRoute
        ) {

            // 입력 화면
            composable<DestinationInputRoute> { entry ->
                DestinationInputScreen(
                    onBackClick = { navController.popBackStack() },
                    onVoiceClick = {
                        navController.navigate(VoiceListeningRoute)
                    },
                    onManualClick = { navController.navigate(ManualInputRoute) }
                )
            }

            // 수동 입력
            composable<ManualInputRoute> { entry ->
                val parentEntry = remember(entry) {
                    navController.getBackStackEntry(SearchGraphRoute)
                }
                val vm: SearchViewModel = hiltViewModel(parentEntry)

                ManualInputScreen(
                    navController = navController,
                    searchViewModel = vm,
                    onBackClick = { navController.popBackStack() }
                )
            }

            // 리스트 화면 (검색 결과)
            composable<DestinationListRoute> { entry ->
                val args = entry.toRoute<DestinationListRoute>()

                // SearchGraphRoute 스코프 VM 가져오기
                val parentEntry = remember(entry) {
                    navController.getBackStackEntry(SearchGraphRoute)
                }
                val vm: SearchViewModel = hiltViewModel(parentEntry)

                // 리스트 화면 들어올 때마다 검색 재실행
                LaunchedEffect(args.query) {
                    vm.search(args.query)
                }

                DestinationListScreen(
                    query = args.query,
                    searchViewModel = vm,        // ⬅ 여기 중요!!!
                    onBackClick = { navController.popBackStack() },
                    onItemClick = { selected ->
                        navController.navigate(
                            DestinationConfirmRoute(
                                placeName = selected.placeName,
                            )
                        )
                    }
                )
            }


            // 목적지 최종 확인
            composable<DestinationConfirmRoute> { entry ->
                val args = entry.toRoute<DestinationConfirmRoute>()

                val parentEntry = remember(entry) {
                    navController.getBackStackEntry(SearchGraphRoute)
                }

                val vm: SearchViewModel = hiltViewModel(parentEntry)
                val result = vm.results.collectAsState()

                LaunchedEffect(args.placeName) {
                    vm.search(args.placeName)
                }
                if (result.value.isEmpty()) {
                    DestinationConfirmScreen(
                        placeName = "장소 없음",
                        address = "장소 없음",
                        onBackClick = { navController.popBackStack() },
                        onConfirmClick = {
                            navController.navigate(TaxiSearchingRoute)
                        },
                        onListClick = {
                            navController.navigate(
                                DestinationListRoute(args.placeName)
                            )
                        }
                    )
                } else {
                    DestinationConfirmScreen(
                        placeName = result.value[0].placeName,
                        address = result.value[0].address,
                        onBackClick = { navController.popBackStack() },
                        onConfirmClick = {
                            navController.navigate(TaxiSearchingRoute)
                        },
                        onListClick = {
                            navController.navigate(
                                DestinationListRoute(args.placeName)
                            )
                        }
                    )
                }

            }
        }

        // ---------------------------
        // 택시 호출 플로우
        // ---------------------------
        composable<TaxiSearchingRoute> {
            TaxiSearchingScreen(
                onChangeAddress = { navController.navigate(SearchGraphRoute) },
                onCancel = { navController.popBackStack() },
                onAutoNext = { navController.navigate(TaxiAssignedRoute) }
            )
        }

        composable<TaxiAssignedRoute> {
            TaxiAssignedScreen(
                onCall = {},
                onCancel = { navController.popBackStack() },
                onAutoNext = { navController.navigate(TaxiFinishedRoute) }
            )
        }

        composable<TaxiFinishedRoute> {
            TaxiFinishedScreen(
                onCall = {},
                onSaveFavorite = { navController.navigate(FavoriteRoute) }
            )
        }

        // ---------------------------
        // 음성 인식 화면
        // ---------------------------
        composable<VoiceListeningRoute> {

            val vm: VoiceViewModel = hiltViewModel()
            val text by vm.text.collectAsState()

            // Google 음성 입력 런처
            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                val data = result.data
                val speechText = data
                    ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    ?.firstOrNull() ?: ""
                Log.d("route", speechText)
                vm.onVoiceInput(speechText)
            }

            // 이 화면 들어오면 자동으로 음성 입력 실행
            LaunchedEffect(Unit) {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                    )
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
                }

                launcher.launch(intent)
            }

            VoiceListeningScreen(
                text = text,
                isListening = false, // Google 음성 입력은 Listening 상태가 따로 없음
                onBackClick = { navController.popBackStack() },
                onStopClick = {
                    navController.navigate(
                        DestinationConfirmRoute(
                            placeName = vm.keywords.value
                        )
                    )
                }
            )
        }




    }
}

@Composable
fun RequestRecordAudioPermission(onGranted: () -> Unit) {
    val context = LocalContext.current
    val permission = Manifest.permission.RECORD_AUDIO
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) onGranted()
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, permission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            launcher.launch(permission)
        } else {
            onGranted()
        }
    }
}
