package com.example.braintrainapp

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
     lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPlayer = MediaPlayer.create(this, R.raw.background)
        mediaPlayer.isLooping = true // Set looping
        mediaPlayer.start() // Start playing

            setContent {
                MyApp()
            }
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release() // Release the media player on destroy
    }

}
