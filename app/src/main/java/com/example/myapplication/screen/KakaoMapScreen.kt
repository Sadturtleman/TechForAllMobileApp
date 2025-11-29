package com.example.myapplication.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.data.model.SearchResult
import com.example.myapplication.ui.viewmodel.SearchViewModel
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.MapView
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.*
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.Label
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelTextBuilder
import com.kakao.vectormap.label.LabelTextStyle
import com.example.myapplication.R
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KakaoMapScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onSelectItem: (SearchResult) -> Unit
) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val resultList by searchViewModel.results.collectAsState()
    val sheetState = rememberBottomSheetScaffoldState()

    // 선택된 index 상태 저장
    val selectedIndex = remember { mutableStateOf<Int?>(null) }

    val labelRefs = remember { mutableListOf<Label>() }
    var kakaoMapInstance: KakaoMap? = remember { null }

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetPeekHeight = 220.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(resultList.indices.toList()) { index ->
                    val item = resultList[index]

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (selectedIndex.value == index) Color(0xFFE3F2FD) else Color.White
                            )
                            .clickable {
                                selectedIndex.value = index
                                onSelectItem(item)
                            }
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(item.placeName, fontWeight = FontWeight.Bold, fontSize = 32.sp)
                            Text(item.address, fontSize = 24.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    ) { padding ->

        AndroidView(
            factory = {
                mapView.apply {
                    start(
                        object : MapLifeCycleCallback() {
                            override fun onMapDestroy() {}
                            override fun onMapError(error: Exception) {}
                        },
                        object : KakaoMapReadyCallback() {
                            override fun onMapReady(map: KakaoMap) {

                                kakaoMapInstance = map
                                labelRefs.clear()

                                val labelLayer = map.labelManager?.layer

                                resultList.forEachIndexed { index, item ->
                                    val label = labelLayer?.addLabel(
                                        LabelOptions.from(
                                            LatLng.from(item.latitude, item.longitude)
                                        ).apply {

                                            // 숫자 설정
                                            setTexts(LabelTextBuilder().setTexts("${index + 1}"))

                                            // 아이콘 + 텍스트 스타일
                                            setStyles(
                                                LabelStyle.from(R.drawable.outline_bookmark_24)
                                                    .setTextStyles(
                                                        LabelTextStyle.from(
                                                            26,
                                                            android.graphics.Color.BLACK // 숫자 색
                                                        )
                                                    )
                                                    .setAnchorPoint(0.5f, 1.0f) // 아이콘 하단 기준
                                            )
                                        }
                                    )

                                    label?.apply {
                                        tag = index
                                        labelRefs.add(this)
                                    }
                                }

                                // 첫 위치로 카메라 이동
                                val first = resultList.first()
                                map.moveCamera(
                                    CameraUpdateFactory.newCenterPosition(
                                        LatLng.from(first.latitude, first.longitude),
                                        16
                                    )
                                )
                            }
                        }
                    )
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        )
    }

    DisposableEffect(Unit) {
        mapView.resume()
        onDispose {
            mapView.pause()
            mapView.finish()
        }
    }
}

