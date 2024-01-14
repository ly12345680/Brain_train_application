package com.example.braintrainapp.ui

import android.content.Context
import android.media.MediaPlayer
import com.example.braintrainapp.R

fun PlayClickSound(context: Context) {

    val mediaPlayer = MediaPlayer.create(context, R.raw.click)
    mediaPlayer.start()
}