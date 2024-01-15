package com.example.braintrainapp.ui.memory

import android.os.CountDownTimer
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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.braintrainapp.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.sqrt

@Composable
fun SearchDialog4(onDismiss: () -> Unit, onSearch: () -> Unit) {
//    var playerName by remember { mutableStateOf(TextFieldValue()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Game Instructions")
        },
        text = {
            Column {
                Text("Step 1: The game will provide pairs of different pictures.")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Step 2: The player's task is to choose the exact pictures into a pair .")
                Spacer(modifier = Modifier.height(8.dp))
                Text("GoodLuck and try get higher score!!!")
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSearch()
                    onDismiss()
                }
            ) {
                Text("Got it!")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PairImage(navController: NavController) {

    val timer = remember { MutableStateFlow(60) } // Timer starts with 60 seconds
    val baseImages: List<Int> = listOf(
        R.drawable.camel, R.drawable.coala, R.drawable.fox, R.drawable.lion,
        R.drawable.monkey, R.drawable.wolf, R.drawable.ari, R.drawable.ordek,
        R.drawable.kedi, R.drawable.tavuk, R.drawable.balik, R.drawable.fil
    ).let { it + it }
    var level by remember { mutableStateOf(1) }
    var images = generateImagesForLevel(baseImages, level)

    var shuffledImages by remember { mutableStateOf(images.shuffled()) }
    var openCards by remember { mutableStateOf(List(images.size) { false }) }
    var lastOpenedCardIndex by remember { mutableStateOf(-1) }
    var matchedCards by remember { mutableStateOf(0) }
    var gameOver by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(1f) }
    val coroutineScope = rememberCoroutineScope()
    var score by remember { mutableStateOf(0) }
    val maxLevel = 5
    val maxTime = 30
    var isWin by remember { mutableStateOf(false) }
    var islose by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableStateOf(maxTime) }
    var isStop by remember { mutableStateOf(false) }
    var stageTime by remember { mutableStateOf(maxTime) }
    LaunchedEffect(key1 = isStop) {
        while (timeLeft > 0 && isStop ) {
            delay(1000)
            timeLeft--
        }
        isStop = true
        if (gameOver == false && timeLeft == 0){
            islose = true
            gameOver = true
        }
        if (level > 1) {
            stageTime +=5
            timeLeft = stageTime
        }
        else {

            timeLeft = stageTime
        }
    }
    if (gameOver) {
        if (isWin) {
            AlertDialog(
                onDismissRequest = {
                    level = 1
                    score = 0
                    images = generateImagesForLevel(baseImages, level)
                    shuffledImages = images.shuffled()
                    openCards = List(images.size) { false }
                    lastOpenedCardIndex = -1
                    matchedCards = 0
                    gameOver = false
                    isStop = false
                },
                title = { Text("You Win") },
                text = { Text("You have completed all levels! Your Score: $score") },
                confirmButton = {
                    Button(onClick = {
                        // Reset game
                        level = 1
                        score = 0
                        images = generateImagesForLevel(baseImages, level)
                        shuffledImages = images.shuffled()
                        openCards = List(images.size) { false }
                        lastOpenedCardIndex = -1
                        matchedCards = 0
                        gameOver = false
                        isStop = false
                    }) {
                        Text("Restart")
                    }
                }
            )
        } else if(!islose) {
            AlertDialog(
                onDismissRequest = {
                    level++
                    images = generateImagesForLevel(baseImages, level)
                    shuffledImages = images.shuffled()
                    openCards = List(images.size) { false }
                    lastOpenedCardIndex = -1
                    matchedCards = 0
                    gameOver = false
                    isStop = false},
                title = { Text("Great Work!") },
                text = { Text("You have matched all cards! Your Score: $score") },
                confirmButton = {
                    Button(onClick = {
                        // Reset game
                        level++
                        images = generateImagesForLevel(baseImages, level)
                        shuffledImages = images.shuffled()
                        openCards = List(images.size) { false }
                        lastOpenedCardIndex = -1
                        matchedCards = 0
                        gameOver = false
                        isStop = false
                    }) {
                        Text("Continue")
                    }
                }
            )
        } else {
            AlertDialog(
                onDismissRequest = {
                    level = 1
                    score = 0
                    images = generateImagesForLevel(baseImages, level)
                    shuffledImages = images.shuffled()
                    openCards = List(images.size) { false }
                    lastOpenedCardIndex = -1
                    matchedCards = 0
                    gameOver = false
                    islose = false
                    isStop = false
                                   },
                title = { Text("Game Over") },
                text = { Text("OUT OF TIME! Your Score: $score") },
                confirmButton = {
                    Button(onClick = {
                        // Reset game
                        level = 1
                        score = 0
                        images = generateImagesForLevel(baseImages, level)
                        shuffledImages = images.shuffled()
                        openCards = List(images.size) { false }
                        lastOpenedCardIndex = -1
                        matchedCards = 0
                        gameOver = false
                        islose = false
                        isStop = false
                    }) {
                        Text("Restart")
                    }
                }
            )
        }
    }
    var isDialogVisible by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pair Image") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Menu",
                        modifier = Modifier
                            .padding(12.dp)
                            .clickable {
                                isStop = false
                                navController.popBackStack()
                            }
                    )
                },
                actions = {
                    Text(
                        text = "Level: ${level}",
                        modifier = Modifier.padding(end = 16.dp),
                        style = TextStyle(fontSize = 20.sp, color = Color.Blue, fontWeight = FontWeight.Bold),
                    )
                    IconButton(onClick = {
                        isDialogVisible = true
                    }) {
                        Icon(Icons.Filled.Info, contentDescription = "Search")
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color(204, 255, 255),
                ),
            )
            // Kiểm tra nếu Dialog nên hiển thị
            if (isDialogVisible) {
                SearchDialog4(
                    onDismiss = {
                        // Khi nhấn Cancel, ẩn Dialog
                        isDialogVisible = false
                    },
                    onSearch = {
                        // Xử lý hành động tìm kiếm, sau đó ẩn Dialog
                        isDialogVisible = false
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(204, 255, 255))
                .padding(16.dp),
        ) {

            Column(modifier = Modifier
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Row(modifier = Modifier
                    .padding(10.dp)
                    .size(180.dp, 50.dp)
                    .background(color = Color.White, shape = MaterialTheme.shapes.extraLarge)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = "Score",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(5.dp)
                    )
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Score: " + score.toString(),
                            modifier = androidx.compose.ui.Modifier
                                .fillMaxWidth(),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            color = androidx.compose.material3.contentColorFor(Color.White),
                            style = TextStyle(fontFamily = FontFamily(Font(R.font.simonetta_regular)), fontWeight = FontWeight.Bold),
                            fontSize = 25.sp
                        )
                    }
                }
                LinearProgressIndicator(
                    progress = timeLeft.toFloat() / stageTime,
                    color = Color.Yellow,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp)
                        .background(Color.White, MaterialTheme.shapes.extraLarge)
                        .border(0.dp, Color.Black, MaterialTheme.shapes.extraLarge)
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {




                LazyVerticalGrid(
                    columns = GridCells.Fixed(sqrt(images.size.toDouble()).toInt()),
                    contentPadding = paddingValues
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
                                                score +=10

                                                if (matchedCards == images.size/2) {
                                                    gameOver = true
                                                    timeLeft += 5
                                                    if (level == maxLevel) {
                                                        isWin = true
                                                    }

                                                }
                                            } else {
                                                delay(300) // wait a second before closing the cards
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
            .background(Color.Transparent, CircleShape)
    ) {
        if (isOpen) {
            imageCheck()
        } else {
            Image(painterResource(id = imageId), contentDescription = null, modifier = Modifier.size(64.dp))
        }
    }
}

@Composable
fun imageCheck() {
    Image(painterResource(id = R.drawable.check2), contentDescription = null, modifier = Modifier.size(64.dp))
}
fun generateImagesForLevel(baseImages: List<Int>, level: Int): List<Int> {
    val numberOfPairs = 6 + (level - 1) * 2
    return List(numberOfPairs) { baseImages[it % baseImages.size] }.let { it + it }
}

@Preview
@Composable
fun ImageGanmePreview() {
//    CountdownTimer(6000, onTick = {}, onFinish = {})
    PairImage(navController = rememberNavController())
//    CountdownProgressBar(maxTime = 30)
}
