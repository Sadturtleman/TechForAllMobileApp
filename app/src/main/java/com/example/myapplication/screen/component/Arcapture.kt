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
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ARFlowManager() {
    val context = LocalContext.current

    // 1. 카메라 권한 상태 관찰
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    // 2. 권한 상태에 따른 분기 처리
    when {
        cameraPermissionState.status.isGranted -> {
            // [상태 1] 권한이 허용된 경우: 기존 카메라 로직 실행
            CameraContent(context)
        }
        cameraPermissionState.status.shouldShowRationale ||
                !cameraPermissionState.status.isGranted -> {
            // [상태 2] 권한이 없거나 거부된 경우: 가이드 UI 제공
            PermissionGuidanceScreen(
                shouldShowRationale = cameraPermissionState.status.shouldShowRationale,
                onRequestPermission = { cameraPermissionState.launchPermissionRequest() }
            )
        }
    }
}

@Composable
fun CameraContent(context: android.content.Context) {
    val controller = remember {
        LifecycleCameraController(context).apply {
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            setEnabledUseCases(LifecycleCameraController.IMAGE_ANALYSIS or LifecycleCameraController.IMAGE_CAPTURE)
        }
    }

    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isRecognized by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (!isRecognized) {
            ARLocationScreen(
                controller = controller,
                onBackClick = { /* 뒤로가기 */ },
                onRecognized = {
                    captureSnapshot(controller, context) { bitmap ->
                        capturedBitmap = bitmap
                        isRecognized = true
                    }
                }
            )
        } else {
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

@Composable
fun PermissionGuidanceScreen(
    shouldShowRationale: Boolean,
    onRequestPermission: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B2A41)) // 다크 테마 기반의 견고한 느낌
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "카메라 권한이 필요합니다",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (shouldShowRationale)
                "주변 위치를 인식하기 위해 카메라 권한이 반드시 필요합니다."
            else "AR 기능을 이용하시려면 카메라 권한을 허용해주세요.",
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onRequestPermission,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC933)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("권한 허용하기", color = Color.Black, fontWeight = FontWeight.Bold)
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