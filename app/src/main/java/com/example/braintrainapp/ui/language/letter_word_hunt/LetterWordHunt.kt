package com.example.braintrainapp.ui.language_game.letter_word_hunt

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.braintrainapp.ui.language_game.compound_word.SearchDialog7
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@Composable
fun SearchDialog8(onDismiss: () -> Unit, onSearch: () -> Unit) {
//    var playerName by remember { mutableStateOf(TextFieldValue()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Game Instructions")
        },
        text = {
            Column {
                Text("Step 1: The game will give the user 1 letter.")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Step 2: Within 2 mins, find meaningful words that start with this letter.")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Step 3: The longer the word you finds, the higher the score you gets.")
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
fun generateLetter(): Char {
    val alphabet = ('A'..'Z').toList()
    return alphabet.random()
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LetterWordHunt(navController: NavController) {
    val gameDuration = 2 * 60 // 2 minutes in seconds

    var timerJob by remember { mutableStateOf<Job?>(null) }
    var score by remember { mutableStateOf(0) }
    var wordsFound by remember { mutableStateOf(0) }
    var remainingTime by remember { mutableStateOf(gameDuration) }
    var currentLetter by remember { mutableStateOf(generateLetter()) }
    var word by remember { mutableStateOf("") }
    var dialogShown by remember { mutableStateOf(false) }

    fun onWordFound(word: String) {
        if (validWords.contains(word.toLowerCase())) {
            val letterScore = 100 // Score per letter
            val scoreMultiplier = 2 // Score multiplier for longer words

            val calculatedScore = word.length * letterScore * (word.length - 1) * scoreMultiplier / 2
            score += calculatedScore
            wordsFound++
        }
    }
    var isDialogVisible by remember { mutableStateOf(false) }
    MaterialTheme {
        Scaffold (
            topBar = {
                TopAppBar(
                    title = { Text("Word Hunt") },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Menu",
                            modifier = Modifier
                                .padding(12.dp)
                                .clickable {
                                    navController.popBackStack()
                                }
                        )
                    },
                    actions = {
                        IconButton(onClick = {
                            isDialogVisible = true
                        }) {
                            Icon(Icons.Filled.Info, contentDescription = "Search")
                        }
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = Color(229, 237, 155, 255),
                    )
                )
                // Kiểm tra nếu Dialog nên hiển thị
                if (isDialogVisible) {
                    SearchDialog8(
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
        ) {paddingvalue ->
            GameScreen(
                currentLetter = currentLetter,
                remainingTime = remainingTime,
                score = score,
                wordsFound = wordsFound,
                onWordFound = ::onWordFound,
                word = word,
                onWordChange = { word = it },
                dialogShown = dialogShown,
                onDialogDismiss = { dialogShown = false },
                onPlayAgainRequest = { dialogShown = true }
            )
            if (dialogShown) {
                GameOverDialog(
                    score = score,
                    wordsFound = wordsFound,
                    onDismissRequest = { dialogShown = false },
                    onPlayAgainRequest = { dialogShown = true })
            }
        }
    }

    fun endGame() {
        timerJob?.cancel()
        dialogShown = true
    }

    fun startGame() {
        timerJob = CoroutineScope(Dispatchers.Default).launch {
            while (remainingTime > 0) {
                delay(1000)
                remainingTime--
            }
            endGame()
        }
    }

    if (timerJob == null) {
        val coroutineScope = rememberCoroutineScope()
        timerJob = coroutineScope.launch {
            startGame()
        }
    }

    fun startNewGame() {
        score = 0
        wordsFound = 0
        remainingTime = gameDuration
        currentLetter = generateLetter()
        word = ""
        dialogShown = false
        timerJob = null
        startGame()
    }

    if (dialogShown) {
        GameOverDialog(
            score = score,
            wordsFound = wordsFound,
            onDismissRequest = { dialogShown = false },
            onPlayAgainRequest = { startNewGame() }
        )
    }
}

@Composable
fun GameScreen(
    currentLetter: Char,
    remainingTime: Int,
    score: Int,
    wordsFound: Int,
    onWordFound: (String) -> Unit,
    word: String,
    onWordChange: (String) -> Unit,
    dialogShown: Boolean,
    onDialogDismiss: () -> Unit,
    onPlayAgainRequest: () -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 100.dp, horizontal = 16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xffffcc99), RoundedCornerShape(16.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Find countries starting with: $currentLetter",
                    style = TextStyle(fontSize = 24.sp),
                    modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
                )
            }
            // Spacer(modifier = Modifier.height(16.dp))
            Button (
                onClick = {},
                colors = ButtonDefaults.buttonColors(Color(0xFFF06292)),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "Time:  $remainingTime seconds",
                    style = TextStyle(fontSize = 16.sp),
                    textAlign = TextAlign.Center
                )
            }
            Button (
                onClick = {},
                colors = ButtonDefaults.buttonColors(Color(0xFFF06292)),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "Score: $score",
                    style = TextStyle(fontSize = 16.sp),
                    textAlign = TextAlign.Center
                )
            }
            Button (
                onClick = {},
                colors = ButtonDefaults.buttonColors(Color(0xFFF06292)),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "Countries found: $wordsFound",
                    style = TextStyle(fontSize = 16.sp),
                    textAlign = TextAlign.Center
                )
            }
            WordInput(word = word, onWordChange = onWordChange, onWordFound = onWordFound)
        }
    }

    if (dialogShown) {
        GameOverDialog(
            score = score,
            wordsFound = wordsFound,
            onDismissRequest = onDialogDismiss,
            onPlayAgainRequest = onPlayAgainRequest)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordInput(word: String, onWordChange: (String) -> Unit, onWordFound: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE6EE9C), RoundedCornerShape(16.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = word,
                onValueChange = onWordChange,
                label = { Text(text = "Enter your answer") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    onWordFound(word)
                    onWordChange("")
                },
                colors = ButtonDefaults.buttonColors(Color.DarkGray),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Submit")
            }
        }
    }
}

@Composable
fun GameOverDialog(
    score: Int,
    wordsFound: Int,
    onDismissRequest: () -> Unit,
    onPlayAgainRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xffffcc99), RoundedCornerShape(16.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Time's up!",
                    style = TextStyle(fontSize = 24.sp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Score: $score",
                    style = TextStyle(fontSize = 20.sp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Countries Found: $wordsFound",
                    style = TextStyle(fontSize = 20.sp),
                    textAlign = TextAlign.Center
                )
                Button (
                    onClick = onPlayAgainRequest,
                    colors = ButtonDefaults.buttonColors(Color.DarkGray),
                    modifier = Modifier.padding(top = 16.dp)
                ){
                    Text(text = "Play Again")
                }

            }
        }
    }
}

@Preview
@Composable
fun PreviewLetterWordHunt() {
    LetterWordHunt(navController = rememberNavController())
}