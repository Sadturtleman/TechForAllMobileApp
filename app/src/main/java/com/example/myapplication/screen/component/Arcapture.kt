package com.example.myapplication.screen

import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.myapplication.ui.theme.Roboto

// --- 1. Flow Manager (상태 관리자) ---
@Composable
fun ARFlowManager() {
    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            setEnabledUseCases(LifecycleCameraController.IMAGE_ANALYSIS or LifecycleCameraController.IMAGE_CAPTURE)
        }
    }

    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isRecognized by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (!isRecognized) {
            // 스캐닝 화면: 인식 로직(onRecognized)을 전달합니다.
            ARLocationScreen(
                controller = controller,
                onBackClick = { /* 뒤로가기 로직 */ },
                onRecognized = {
                    captureSnapshot(controller, context) { bitmap ->
                        capturedBitmap = bitmap
                        isRecognized = true
                    }
                }
            )
        } else {
            // 확인 화면: 캡처된 배경과 함께 표시합니다.
            ARLocationConfirmationScreen(
                capturedBitmap = capturedBitmap,
                placeName = "00 마트",
                onBackClick = { isRecognized = false },
                onNoClick = {
                    capturedBitmap = null
                    isRecognized = false
                }
            )
        }
    }
}

// --- 공통 컴포넌트 및 유틸리티 ---

@Composable
fun ScanningGuidanceCard() {
    Column(
        modifier = Modifier.width(320.dp).padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp)).background(Color.White).padding(vertical = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("위치를 인식하고\n있습니다.", fontSize = 40.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center, fontFamily = Roboto, lineHeight = 48.sp)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Surface(color = Color(0xFFFFC933), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth(), shadowElevation = 4.dp) {
            Text("카메라로 주변을 보여주세요.", modifier = Modifier.padding(vertical = 16.dp), fontSize = 24.sp, fontWeight = FontWeight.Medium, fontFamily = Roboto, color = Color(0xFF1B2A41), textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun ConfirmationContent(placeName: String, onYesClick: () -> Unit, onNoClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = 32.dp)) {
        Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp)).background(Color.White).padding(vertical = 40.dp), contentAlignment = Alignment.Center) {
            Text("${placeName}가\n보이시나요?", fontSize = 40.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center, fontFamily = Roboto, lineHeight = 48.sp)
        }
        Spacer(modifier = Modifier.weight(0.6f))
        Button(onClick = onYesClick, modifier = Modifier.width(280.dp).height(90.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B2A41)), shape = RoundedCornerShape(16.dp)) {
            Text("네", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White, fontFamily = Roboto)
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = onNoClick, modifier = Modifier.width(280.dp).height(90.dp), border = BorderStroke(5.dp, Color(0xFF1B2A41)), shape = RoundedCornerShape(16.dp)) {
            Text("아니요", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B2A41), fontFamily = Roboto)
        }
    }
}

// 스냅샷 캡처 함수 (ImageProxy 확장 함수 생략 시 버전 호환성 주의)
fun captureSnapshot(
    controller: LifecycleCameraController,
    context: android.content.Context,
    onBitmapCaptured: (Bitmap) -> Unit
) {
    try {
        // 카메라가 초기화되지 않았을 때를 대비한 방어 로직
        val mainExecutor = ContextCompat.getMainExecutor(context)

        controller.takePicture(mainExecutor, object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                val bitmap = image.toBitmap()
                onBitmapCaptured(bitmap)
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraX", "캡처 실패: ${exception.message}")
            }
        })
    } catch (e: IllegalStateException) {
        // "Camera not initialized" 에러가 여기서 잡힙니다.
        Log.e("CameraX", "카메라가 아직 준비되지 않았습니다: ${e.message}")
    }
}