package com.example.myapplication.screen

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.myapplication.ui.viewmodel.VoiceViewModel

@Composable
fun VoiceListeningScreen(
    text: String,
    isListening: Boolean,
    onStopClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF3))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 뒤로가기
        Text(
            text = "뒤로가기",
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .clickable { onBackClick() }
        )

        Spacer(modifier = Modifier.height(40.dp))

        // 듣는 중 큰 마이크 버튼
        PulseMic(isListening)

        Spacer(modifier = Modifier.height(24.dp))

        // 듣는 중
        Text(
            text = if (isListening) "듣는 중입니다…" else "처리 중…",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 인식된 텍스트
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = text.ifBlank { "말씀하시는 내용을 듣고 있어요…" },
                fontSize = 18.sp,
                lineHeight = 24.sp,
                color = Color.Black
            )

        }

        Spacer(modifier = Modifier.height(40.dp))

        // 종료 버튼
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF0F233A))
                .clickable { onStopClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "말하기 종료",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PulseMic(active: Boolean) {
    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (active) 1.15f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .size(140.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(RoundedCornerShape(100.dp))
            .background(Color(0xFF0F233A)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.microphone),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(56.dp)
        )
    }
}
