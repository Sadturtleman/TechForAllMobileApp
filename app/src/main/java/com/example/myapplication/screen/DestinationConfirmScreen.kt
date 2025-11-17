package com.example.myapplication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.rememberTTS

@Composable
fun DestinationConfirmScreen(
    placeName: String,
    address: String,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    onListClick: () -> Unit
) {

    val context = LocalContext.current
    val tts = rememberTTS(context)

    LaunchedEffect(Unit) {
        tts.speak("$placeName, $address 가 맞습니까?")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF3))
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Spacer(modifier = Modifier.height(12.dp))

        // 뒤로가기
        Text(
            text = "뒤로가기",
            fontSize = 14.sp,
            modifier = Modifier.clickable { onBackClick() }
        )

        Spacer(modifier = Modifier.height(28.dp))

        // 타이틀
        Text(
            text = "이 장소가 맞나요?",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(28.dp))

        // 장소 카드
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFFFCC46))
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = placeName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "주소 : $address",
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // 파란 버튼 — 맞아요
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF0F233A))
                .clickable { onConfirmClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "맞아요",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 흰색 버튼 — 목록에서 고르기
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(2.dp, Color(0xFF0F233A), RoundedCornerShape(12.dp))
                .clickable { onListClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "목록에서 고르기",
                color = Color(0xFF0F233A),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
