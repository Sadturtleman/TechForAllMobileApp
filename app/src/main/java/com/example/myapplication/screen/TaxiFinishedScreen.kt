package com.example.myapplication.screen

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.rememberTTS
import com.example.myapplication.screen.component.PrimaryButton
import com.example.myapplication.screen.component.WhiteButton

@Composable
fun TaxiFinishedScreen(
    payment: String = "25,300원",
    place: String = "서울역",
    time: String = "오후 2시 14분",
    onCall: () -> Unit = {},
    onSaveFavorite: () -> Unit = {}
) {
    val context = LocalContext.current
    val tts = rememberTTS(context)

    LaunchedEffect(Unit) {
        tts.speak("하차 완료")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFBF3))
            .padding(24.dp)
    ) {

        Text("하차 완료", fontSize = 26.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFFFCC46))
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "결제 금액",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = payment,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("도착장소 : $place", fontSize = 16.sp)
        Text("도착시간 : $time", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(40.dp))

        PrimaryButton(text = "전화 걸기", onClick = onCall)

        Spacer(modifier = Modifier.height(12.dp))

        WhiteButton(text = "즐겨찾기 등록", onClick = onSaveFavorite)
    }
}
