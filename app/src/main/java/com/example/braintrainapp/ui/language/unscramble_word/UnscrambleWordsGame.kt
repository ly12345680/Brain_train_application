package com.example.braintrainapp.ui.language_game.unscramble_words

import android.os.CountDownTimer
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.braintrainapp.ui.memory.SearchDialog2
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random
@Composable
fun SearchDialog6(onDismiss: () -> Unit, onSearch: () -> Unit) {
//    var playerName by remember { mutableStateOf(TextFieldValue()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Game Instructions")
        },
        text = {
            Column {
                Text("Step 1: This game will provide a phrase whose letters are shuffled.")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Step 2: The player's task is to rearrange the order of the letters to find the correct word.")
                Spacer(modifier = Modifier.height(8.dp))
                Text("10 questions/20s-questions/200 per correct answer")
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
fun UnscrambleWordsGame(navController : NavController) {

    var snackbarVisible by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    val currentWordIndex = remember { mutableStateOf(Random.nextInt(0, allWordsList.size)) }
    val currentWord = remember { mutableStateOf(allWordsList[currentWordIndex.value]) }

    val userAnswerState = remember { mutableStateOf(TextFieldValue()) }

    val score = remember { mutableStateOf(0)}
    val questionCount = remember { mutableStateOf(0) }
    val playAgain = remember { mutableStateOf(false)}

    var timeRemaining by remember { mutableStateOf(20_000L) }
    val shuffleQuestion = remember { mutableStateOf(true) }
    val timer = remember {
        GameCountDownTimer(
            20000,
            1000,
            onFinish = { shuffleQuestion.value = true},
            onTick = {millisUntilFinished ->
                timeRemaining = millisUntilFinished})}
    var isDialogVisible by remember { mutableStateOf(false) }
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Unscramble word") },
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
                SearchDialog6(
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
    ) { paddingvalue ->
        Column(
            modifier = Modifier
                .padding(vertical = 100.dp, horizontal = 16.dp)
                .fillMaxSize(), //.background(Color(0xffffcc99)),
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
                        text = "Unscramble Word",
                        style = TextStyle(fontSize = 24.sp),
                        modifier = Modifier.padding(
                            bottom = 16.dp,
                            top = 16.dp
                        )
                    )
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Color(0xFFF06292)),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = "Time: ${timeRemaining / 1000}",
                        style = TextStyle(fontSize = 20.sp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Button(
                    onClick = {
                        shuffleQuestion.value = false // Stop shuffling the question
                        timer.start() // Start the timer
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFFF06292)),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = "Score: ${score.value}",
                        style = TextStyle(fontSize = 20.sp),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

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
                    Text(
                        text = "What is this word?",
                        style = TextStyle(fontSize = 20.sp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        //text = currentWord.value.toList().shuffled().joinToString(""),
                        text = if (shuffleQuestion.value) currentWord.value.toList().shuffled()
                            .joinToString("") else currentWord.value,
                        style = TextStyle(fontSize = 20.sp),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    TextField(
                        value = userAnswerState.value,
                        onValueChange = { userAnswerState.value = it },
                        label = { Text("Enter your answer") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            val userAnswer = userAnswerState.value.text
                            val isCorrectAnswer =
                                userAnswer.equals(currentWord.value, ignoreCase = true)
                            snackbarMessage = if (isCorrectAnswer) "Correct!" else "Incorrect!"
                            snackbarVisible = true
                            coroutineScope.launch {
                                delay(1500)
                                snackbarVisible = false

                                if (isCorrectAnswer) {
                                    val nextIndex = Random.nextInt(0, allWordsList.size)

                                    currentWordIndex.value = nextIndex
                                    currentWord.value = allWordsList[nextIndex]

                                    userAnswerState.value = TextFieldValue()
                                    score.value += 200
                                }

                                questionCount.value += 1
                                if (questionCount.value >= 10) {
                                    playAgain.value = true
                                } else {
                                    timer.start()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color.DarkGray),
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(text = "Check Answer")
                    }
                }

                if (snackbarVisible) {

                    Snackbar(
                        modifier = Modifier.padding(top = 16.dp),
                        action = {
                            Button(onClick = { snackbarVisible = false }) {
                                Text("Dismiss")
                            }
                        }
                    ) {
                        Text(text = snackbarMessage)
                    }
                }
            }


            if (playAgain.value) {
                Text(
                    text = "Total Score: ${score.value}",
                    style = TextStyle(fontSize = 24.sp),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Button(
                    onClick = {
                        // Restart all variables to play one more timr
                        questionCount.value = 0
                        score.value = 0
                        currentWordIndex.value = Random.nextInt(0, allWordsList.size)
                        currentWord.value = allWordsList[currentWordIndex.value]
                        userAnswerState.value = TextFieldValue()
                        playAgain.value = false
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = "Play Again",
                        style = TextStyle(fontSize = 24.sp),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }

            DisposableEffect(Unit) {
                timer.start()

                onDispose {
                    timer.cancel()
                }
            }
        }
    }
}
class GameCountDownTimer(
    millisInFuture: Long,
    countDownInterval: Long,
    private val onFinish: () -> Unit,
    private val onTick: (Long) -> Unit
) : CountDownTimer(millisInFuture, countDownInterval) {

    override fun onTick(millisUntilFinished: Long) {
        onTick.invoke(millisUntilFinished)
    }

    override fun onFinish() {
        onFinish.invoke()
    }
}

@Preview
@Composable
fun PreviewUnscrambleGame() {
    UnscrambleWordsGame(navController = rememberNavController())
}