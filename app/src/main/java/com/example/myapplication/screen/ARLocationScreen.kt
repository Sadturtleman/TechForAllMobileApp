package com.example.myapplication.screen

import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.screen.component.ARTopBar
import com.example.myapplication.screen.component.CameraPreview

@Composable
fun ARLocationScreen(
    controller: LifecycleCameraController,
    onBackClick: () -> Unit,
    onRecognized: () -> Unit
) {
    // 카메라 프리뷰가 Scaffold 뒤에 깔리도록 Box로 감쌉니다.
    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(controller = controller, modifier = Modifier.fillMaxSize())

        Scaffold(
            containerColor = Color.Transparent, // 배경을 투명하게 하여 카메라가 보이도록 함
            topBar = {
                // 기존 ScanningTopBar를 topBar 슬롯에 배치
                ARTopBar(onBackClick = onBackClick)
            }
        ) { innerPadding ->
            // innerPadding은 Scaffold가 예약한 영역(topBar 등)을 제외한 나머지 공간입니다.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .clickable { onRecognized() }, // 테스트용 트리거
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ScanningGuidanceCard()
                    // 가이드 시안에 따라 아래쪽으로 약간 치우치게 조절
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}