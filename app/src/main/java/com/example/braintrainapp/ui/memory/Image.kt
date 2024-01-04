package com.example.braintrainapp.ui.memory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.braintrainapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@Composable
fun Image()  {
    val images: List<Int> = listOf(
        R.drawable.camel, R.drawable.coala, R.drawable.fox, R.drawable.lion,
        R.drawable.monkey, R.drawable.wolf, R.drawable.ari, R.drawable.ordek,
        R.drawable.kedi, R.drawable.tavuk, R.drawable.balik, R.drawable.fil
    ).let { it + it }

    var shuffledImages by remember { mutableStateOf(images.shuffled()) }
    var openCards by remember { mutableStateOf(List(images.size) { false }) }
    var lastOpenedCardIndex by remember { mutableStateOf(-1) }
    var matchedCards by remember { mutableStateOf(0) }
    var gameOver by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    if (gameOver) {
        AlertDialog(
            onDismissRequest = { gameOver = false },
            title = { Text("Game Over") },
            text = { Text("You have matched all cards!") },
            confirmButton = {
                Button(onClick = {
                    // Reset game
                    shuffledImages = images.shuffled()
                    openCards = List(images.size) { false }
                    lastOpenedCardIndex = -1
                    matchedCards = 0
                    gameOver = false
                }) {
                    Text("Restart")
                }
            }
        )
    }

    LazyVerticalGrid(
        columns= GridCells.Fixed(4),
        modifier = Modifier.fillMaxSize()
    ) {
        items(shuffledImages.size) { index ->
            CardView(
                imageId = shuffledImages[index],
                isOpen = openCards[index],
                onClick = {
                    if (!openCards[index]) {
                        coroutineScope.launch {
                            openCards = openCards.toMutableList().also { it[index] = true }
                            if (lastOpenedCardIndex == -1) {
                                lastOpenedCardIndex = index
                            } else {
                                if (shuffledImages[lastOpenedCardIndex] == shuffledImages[index]) {
                                    matchedCards++
                                    if (matchedCards == images.size) {
                                        gameOver = true
                                    }
                                } else {
                                    delay(1000) // wait a second before closing the cards
                                    openCards = openCards.toMutableList().also {
                                        it[index] = false
                                        it[lastOpenedCardIndex] = false
                                    }
                                }
                                lastOpenedCardIndex = -1
                            }
                        }
                    }
                }
            )
        }
    }




}
@Composable
fun CardView(imageId: Int, isOpen: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(64.dp)
            .padding(4.dp)
            .clickable(onClick = onClick)
    ) {
        if (isOpen) {
            Image(painterResource(id = imageId), contentDescription = null)
        } else {
            Image(painterResource(id = R.drawable.ic_launcher_background), contentDescription = null)
        }
    }
}
@Preview
@Composable
fun ImageGanmePreview() {
    Image()
}