package com.example.myapplication

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.router.BottomNavBar
import com.example.myapplication.router.NavGraph
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        getHashKey(this)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavBar(navController = navController) }
                ) { paddingValues ->
                    NavGraph(navController, paddingValues)

                }
            }
        }
    }
}

fun getHashKey(context: Context) {
    try {
        val info = context.packageManager.getPackageInfo(
            context.packageName,
            PackageManager.GET_SIGNATURES
        )
        for (signature in info.signatures!!) {
            val md = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            val hash = Base64.encodeToString(md.digest(), Base64.NO_WRAP)
            Log.d("KAKAO_HASH", hash)
        }
    } catch (e: Exception) {
        Log.e("KAKAO_HASH", "Error: $e")
    }
}
