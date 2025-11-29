package com.example.myapplication.screen

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var isTtsReady by remember { mutableStateOf(false) }

    val tts = remember {
        TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isTtsReady = true
            }
        }
    }

    LaunchedEffect(isTtsReady) {
        if (isTtsReady) {
            tts.speak("$placeName 이 맞나요?", TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF3))
    ) {

        // 🔹 뒤로가기 — 화면 최상단 왼쪽 고정
        Text(
            text = "뒤로가기",
            fontSize = 14.sp,
            modifier = Modifier
                .padding(start = 24.dp, top = 16.dp)
                .clickable { onBackClick() }
                .align(Alignment.TopStart)
        )

        // 🔹 중앙 영역 전체 UI
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "이 장소가 맞나요?",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(28.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFFFCC46))
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = if (placeName == "장소 없음") {
                            "서울역"
                        } else {
                            placeName
                        },
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (address == "장소 없음"){
                            "주소 : 서울역 중구"
                        } else{
                            "주소 : $address"
                        },
                        fontSize = 24.sp,
                        lineHeight = 20.sp,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .width(280.dp)
                    .height(52.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0F233A))
                    .clickable { onConfirmClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "맞아요",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .width(280.dp)
                    .height(52.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(2.dp, Color(0xFF0F233A), RoundedCornerShape(12.dp))
                    .clickable { onListClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "목록에서 고르기",
                    color = Color(0xFF0F233A),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
