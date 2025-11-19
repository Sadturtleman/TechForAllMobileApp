package com.example.myapplication.data.model

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.runtime.*

class TTSManager(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech = TextToSpeech(context, this)
    private var ready = false

    override fun onInit(status: Int) {
        ready = (status == TextToSpeech.SUCCESS)
    }

    fun speak(text: String) {
        if (ready) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts_id")
        }
    }

    fun shutdown() {
        tts.stop()
        tts.shutdown()
    }
}

@Composable
fun rememberTTS(context: Context): TextToSpeech {
    val tts = remember {
        TextToSpeech(context) { status ->
            Log.d("TTS", "Ready status: $status")
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            tts.stop()
            tts.shutdown()
        }
    }
    return tts
}
