package com.example.myapplication.data.di

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import java.io.ByteArrayOutputStream
@SuppressLint("MissingPermission")
class AudioRecorder {

    private val sampleRate = 16000
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT

    private val bufferSize = AudioRecord.getMinBufferSize(
        sampleRate, channelConfig, audioFormat
    )

    private val audioRecord = AudioRecord(
        MediaRecorder.AudioSource.MIC,
        sampleRate,
        channelConfig,
        audioFormat,
        bufferSize
    )

    fun record(durationMs: Long = 2000): ByteArray {
        val buffer = ByteArray(bufferSize)
        val output = ByteArrayOutputStream()

        audioRecord.startRecording()

        val start = System.currentTimeMillis()
        while (System.currentTimeMillis() - start < durationMs) {
            val read = audioRecord.read(buffer, 0, buffer.size)
            if (read > 0) output.write(buffer, 0, read)
        }

        audioRecord.stop()
        audioRecord.release()

        return output.toByteArray()
    }
}
