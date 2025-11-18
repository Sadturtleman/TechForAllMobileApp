package com.example.myapplication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
    searchViewModel: SearchViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onItemClick: (SearchResult) -> Unit
) {
    val resultList = searchViewModel.results.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF3))
            .padding(16.dp)
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
            Text(
                "검색 결과가 없습니다.",
                fontSize = 16.sp,
                color = Color.DarkGray
            )
        } else {
            androidx.compose.foundation.lazy.LazyColumn {
                items(resultList) { result ->
                    ResultItem(
                        result = result,
                        onClick = { onItemClick(result) }
                    )
                }
            }
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
            Text(result.placeName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(result.address, fontSize = 14.sp, color = Color.DarkGray)
        }
    }
}
