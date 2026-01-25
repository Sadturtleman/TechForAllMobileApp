package com.example.myapplication.screen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.myapplication.screen.component.ARTopBar

@Composable
fun ARLocationConfirmationScreen(
    capturedBitmap: Bitmap?,
    placeName: String,
    onBackClick: () -> Unit,
    onYesClick: () -> Unit = {},
    onNoClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 배경: 캡처된 이미지
        capturedBitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // 가독성을 위한 어두운 레이어
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.1f)))

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                ARTopBar(onBackClick = onBackClick)
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ConfirmationContent(
                        placeName = placeName,
                        onYesClick = onYesClick,
                        onNoClick = onNoClick
                    )
                    // 하단 버튼 하단 여백 확보 (Spacer weight 대신 고정 값 사용 가능)
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}