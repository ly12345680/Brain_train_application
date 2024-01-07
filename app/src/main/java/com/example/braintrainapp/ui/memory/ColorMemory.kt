package com.example.braintrainapp.ui.memory

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.braintrainapp.R
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight

fun checkWin(blueSquares: List<Boolean>, selectedSquares: List<Boolean>): Boolean {
    for((index, value) in blueSquares.withIndex()) {
        if (value && !selectedSquares[index]) {
            return false
        }
    }
    return true
}

@Composable
fun bar(level: Int) {

}
@Composable
fun ScoreAndLevel(score: Int) {

    val borderColor = Color ( 0xFFFBE897). copy(alpha = 0.7f)
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
                style = TextStyle(fontFamily = FontFamily(Font(R.font.simonetta_regular))),
                fontSize = 25.sp
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorMemory() {
    var gridSize by remember { mutableStateOf(2) }
    val numberOfBlueSquares = gridSize
    val squares = List(gridSize * gridSize) { false }.toMutableStateList()
    val fillBlueSquares = List(gridSize * gridSize) { false }.toMutableStateList()
    val blueSquares = List(gridSize * gridSize) { false }.toMutableStateList()
    val selectedSquares = List(gridSize * gridSize) { false }.toMutableStateList()
    var gameStarted by remember { mutableStateOf(true) }
    var gameOver by remember { mutableStateOf(false) }
    var clickabbles by remember { mutableStateOf(false) }
    var checkWins = remember { mutableStateOf(false) }
    var score = remember {
        mutableStateOf(0)
    }
    val maxLevel = 5
    var level = remember { mutableStateOf(1)}
    var timeLeft by remember { mutableStateOf(5) }



        LaunchedEffect(key1 = gameStarted) {

            val indices = squares.indices.shuffled().take(numberOfBlueSquares)
            for (index in indices) {
                blueSquares[index] = true
                fillBlueSquares[index] = true
            }

            delay(3000) // show blue squares for 3 seconds
            for (index in indices) {
                blueSquares[index] = false
            }
            clickabbles = true
            while (timeLeft > 0) {
                delay(1000) // delay for 1 second
                timeLeft--
            }
            gameOver = true

        }


    if (gameOver) {

        if (level.value == maxLevel && checkWins.value) {

            AlertDialog(onDismissRequest = {gameOver = false },
                title = { Text("Congratulations 🥳🥳🥳") },
                text = {
                    Text(
                        "You have completed all levels!"
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        gridSize = 2 // Reset grid size to the initial level if the user has lost
                        score.value = 0
                        level.value = 1
                        squares.fill(false)
                        blueSquares.fill(false)
                        selectedSquares.fill(false)
                        gameOver = false
                        gameStarted = !gameStarted
                        level.value = 1
                        timeLeft = 5
                    }) {
                        Text("Play Again")
                    }
                })
        } else {

            AlertDialog(
                onDismissRequest = { gameOver = false },
                title = { Text("Game Over") },
                text = {
                    Text(
                        if (checkWins.value)
                            "You win!"
                        else
                            "You lose!"
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        // Reset game

                        if (checkWins.value) {
                            gridSize++ // Increase grid size if the user has won
                            score.value += 10
                            level.value++
                        } else {
                            gridSize = 2 // Reset grid size to the initial level if the user has lost
                            score.value = 0
                            level.value = 1
                        }
                        squares.fill(false)
                        blueSquares.fill(false)
                        selectedSquares.fill(false)
                        gameOver = false
                        gameStarted = !gameStarted
                        timeLeft = 5

                    }) {
                        Text("Countinue")
                    }
                }
            )
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Memory Sweep") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Menu",
                        modifier = Modifier
                            .padding(12.dp)
                            .clickable { /* Handle menu click */ }
                    )
                },
                actions = {
                    Text(
                        text = "Level: ${level.value}",
                        modifier = Modifier.padding(end = 16.dp),
                        style = TextStyle(fontSize = 20.sp, color = Color.Blue, fontWeight = FontWeight.Bold),
                    )
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Guidelines",
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clickable { /* Handle guidelines click */ }
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color(204, 255, 255),
                )

            )
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .background(Color(230, 255, 255)),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally

            ){
                Spacer(
                    modifier = Modifier.size(30.dp)
                )
                ScoreAndLevel(score = score.value.toInt())
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Time left: $timeLeft",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    )
                }
                LazyColumn {
                    items(gridSize) { rowIndex ->
                        LazyRow {
                            items(gridSize) { columnIndex ->
                                val index = rowIndex * gridSize + columnIndex
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .padding(4.dp)
                                        .shadow(
                                            elevation = 5.dp,
                                            shape = MaterialTheme.shapes.extraSmall
                                        )
                                        .background(
                                            when {
                                                blueSquares[index] -> Color.Blue // Change to Color.Blue
                                                selectedSquares[index] -> Color.Yellow
                                                else -> Color.White
                                            }
                                        )
                                        .clickable {
                                            if (!gameOver && clickabbles) {
                                                selectedSquares[index] = !selectedSquares[index]
                                                if (selectedSquares.count { it } == numberOfBlueSquares) {
                                                    gameOver = true
                                                    checkWins.value =
                                                        checkWin(fillBlueSquares, selectedSquares)
                                                    clickabbles = false
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
    }

}

@Preview
@Composable
fun ColorMemoryPreview() {
    ColorMemory()
}