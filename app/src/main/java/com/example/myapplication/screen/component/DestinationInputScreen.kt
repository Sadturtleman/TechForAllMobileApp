package com.example.myapplication.screen.component

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun DestinationInputScreen(
    onBackClick: () -> Unit = {},
    onVoiceClick: () -> Unit = {},
    onManualClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF3))  // 배경색 (아이보리 느낌)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 🔙 뒤로가기
        Text(
            text = "뒤로가기",
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp)
                .clickable { onBackClick() }
        )

        Spacer(modifier = Modifier.height(40.dp))

        // 제목
        Text(
            text = "어디로 가시나요?",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))


        // 🎤 음성 버튼
        Box(
            modifier = Modifier
                .size(180.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(80.dp),
                    ambientColor = Color(0x40000000),
                    spotColor = Color(0x40000000)
                )
                .clip(RoundedCornerShape(80.dp))
                .background(Color(0xFF0F233A))
                .clickable { onVoiceClick() },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                // 아이콘
                Icon(
                    painter = painterResource(id = R.drawable.microphone),  // 🔥 음성 아이콘 추가 필요
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 버튼 텍스트
                Text(
                    text = "눌러서\n말하기",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 예시 텍스트
        Text(
            text = "예 : 서울대병원, 서울시청",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(80.dp))


        // 🟡 직접 입력하기
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFFFC428))
                .clickable { onManualClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "직접 입력하기",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
