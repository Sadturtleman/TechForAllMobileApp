package com.example.myapplication.screen

import android.speech.tts.TextToSpeech
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.model.rememberTTS

@Composable
fun DestinationInputScreen(
    onBackClick: () -> Unit = {},
    onVoiceClick: () -> Unit = {},
    onManualClick: () -> Unit = {}
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
            tts.speak("택시 호출 화면입니다. 호출 버튼을 눌러주세요.", TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    // 🔥 전체를 감싸는 Box 사용
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF3))
    ) {

        // 🔙 뒤로가기 (상단 고정)
        Text(
            text = "뒤로가기",
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 24.dp, top = 16.dp)
                .clickable { onBackClick() }
        )


        // 🎯 중앙 콘텐츠
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .align(Alignment.Center), // 🔥 중앙 정렬
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 제목
            Text(
                text = "어디로 가시나요?",
                fontSize = 40.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(40.dp))


            // 🎤 음성 버튼
            Box(
                modifier = Modifier
                    .size(260.dp)
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

                    Icon(
                        painter = painterResource(id = R.drawable.microphone),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(60.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "눌러서\n말하기",
                        fontSize = 36.sp,
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
                fontSize = 24.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(80.dp))

            // 🟡 직접 입력하기 버튼
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
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

