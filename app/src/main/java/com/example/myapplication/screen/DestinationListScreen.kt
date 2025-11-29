package com.example.myapplication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.data.model.SearchResult
import com.example.myapplication.ui.viewmodel.SearchViewModel

@Composable
fun DestinationListScreen(
    query: String,
    paddingValues: PaddingValues,
    searchViewModel: SearchViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onItemClick: (SearchResult) -> Unit,
    onMapClick: () -> Unit
) {
    val resultList = searchViewModel.results.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF3))
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = paddingValues.calculateBottomPadding() + 16.dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "뒤로가기",
            modifier = Modifier.clickable(onClick = onBackClick),
            fontSize = 14.sp
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "\"$query\" 검색 결과",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        if (resultList.isEmpty()) {
            Text("검색 결과가 없습니다.", fontSize = 16.sp, color = Color.DarkGray)

            Spacer(Modifier.weight(1f)) // 아래 버튼을 밑으로 밀기
        } else {
            // 🔽 스크롤 영역
            androidx.compose.foundation.lazy.LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(resultList) { result ->
                    ResultItem(
                        result = result,
                        onClick = { onItemClick(result) }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // 🔥 화면 하단 고정 "지도로 보기" 버튼
        Box(
            modifier = Modifier
                .width(245.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFFFC727))
                .clickable(onClick = onMapClick)
                .padding(vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "지도로 보기",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color.Black
            )
        }
    }
}


@Composable
private fun ResultItem(
    result: SearchResult,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column {
            Text(result.placeName, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text(result.address, fontSize = 24.sp, color = Color.DarkGray)
        }
    }
}
