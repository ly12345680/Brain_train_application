package com.example.braintrainapp.ui.language_game.unscramble_words

import android.os.CountDownTimer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnscrambleWordsGame() {

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
    val timer = remember {
        GameCountDownTimer(
            20000,
            1000,
            onFinish = {},
            onTick = {millisUntilFinished ->
                timeRemaining = millisUntilFinished})}

    Column(
        modifier = Modifier
            .padding(16.dp), //.background(Color(0xffffcc99)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!playAgain.value){
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
                ){
                    Text(
                        text = "Unscramble Word",
                        style = TextStyle(fontSize = 24.sp),
                        modifier = Modifier.padding(bottom = 16.dp, top = 16.dp) //.background(Color(0xffffcc99))
                    )
                }
                Text(
                    text = "Time: ${timeRemaining / 1000}",
                    style = TextStyle(fontSize = 20.sp),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button (
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Color(0xFFF06292)),
                    modifier = Modifier.padding(top = 16.dp)
                ){
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
            ){
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "What is this word?",
                        style = TextStyle(fontSize = 20.sp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = currentWord.value.toList().shuffled().joinToString(""),
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
        } else {
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
                    modifier = Modifier.padding(bottom = 16.dp))
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
    UnscrambleWordsGame()
}