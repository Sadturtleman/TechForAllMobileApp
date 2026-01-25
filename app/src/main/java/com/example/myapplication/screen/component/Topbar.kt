package com.example.myapplication.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Roboto

/**
 * AR 화면 전용 상단바
 * Scaffold의 topBar 슬롯에 배치하기에 최적화되어 있습니다.
 */
@Composable
fun ARTopBar(
    title: String = "뒤로가기",
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding() // 시스템 상태바 영역만큼 패딩 자동 부여 (중요!)
            .padding(top = 16.dp, start = 16.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 뒤로가기 버튼: 반투명 배경을 넣어 시인성 확보
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .size(44.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // 상단 타이틀 텍스트
        Text(
            text = title,
            color = Color.White,
            fontSize = 24.sp, // 시안의 강조된 크기 반영
            fontWeight = FontWeight.Medium,
            fontFamily = Roboto,
            // 배경이 밝을 때를 대비한 아주 은은한 그림자 효과 (선택 사항)
            // style = TextStyle(shadow = Shadow(color = Color.Black, blurRadius = 4f))
        )
    }
}