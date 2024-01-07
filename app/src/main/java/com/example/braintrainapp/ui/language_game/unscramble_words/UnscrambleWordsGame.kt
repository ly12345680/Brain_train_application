package com.example.braintrainapp.ui.language_game.unscramble_words

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

//data class Question(val phrase: String, val answer: String)

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun UnscrambleWordsGame() {
//    var currentQuestionIndex by remember { mutableStateOf(0) }
//    var score by remember { mutableStateOf(0) }
//    var responseTime by remember { mutableStateOf(0L) }
//    var totalScore by remember { mutableStateOf(0) }
//    var averageResponseTime by remember { mutableStateOf(0L) }
//    var bonusScore by remember { mutableStateOf(0) }
//    var gameFinished by remember { mutableStateOf(false) }
//
//    if (currentQuestionIndex < 10) {
//        val currentQuestion = generateRandomQuestion()
//        var userInput by remember { mutableStateOf("") }
//        var timerRunning by remember { mutableStateOf(false) }
//        var secondsLeft by remember { mutableStateOf(20) }
//
//        LaunchedEffect(Unit) {
//            timerRunning = true
//            withContext(Dispatchers.Default) {
//                while (secondsLeft > 0 && timerRunning) {
//                    delay(1000)
//                    secondsLeft--
//                }
//            }
//
//            if (timerRunning) {
//                checkAnswer(currentQuestion.answer, userInput)
//                timerRunning = false
//            }
//        }
//
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(text = "Unscramble Word", style = MaterialTheme.typography.headlineMedium,
//                modifier = Modifier.padding(bottom = 16.dp))
//            Text(text = currentQuestion.phrase)
//            Text(text = "Seconds Left: $secondsLeft")
//            Text(text = "Score: $score")
//
//            TextField(
//                value = userInput,
//                onValueChange = { userInput = it },
//                modifier = Modifier.padding(top = 16.dp)
//            )
//
//            Button(
//                onClick = {
//                    checkAnswer(currentQuestion.answer, userInput)
//                    timerRunning = false
//                },
//                modifier = Modifier.padding(top = 16.dp)
//            ) {
//                Text(text = "Submit")
//            }
//
//            Text(text = "Total Score: $totalScore")
//            Text(text = "Average Response Time: $averageResponseTime")
//            Text(text = "Bonus Score: $bonusScore")
//        }
//    } else {
//        gameFinished = true
//        averageResponseTime = responseTime / 10
//        totalScore = score + bonusScore
//    }
//
//    if (gameFinished) {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(text = "Game Finished")
//            Text(text = "Total Score: $totalScore")
//            Text(text = "Average Response Time: $averageResponseTime")
//            Button(
//                onClick = { restartGame() },
//                modifier = Modifier.padding(top = 16.dp)
//            ) {
//                Text(text = "Restart Game")
//            }
//        }
//    }
//}

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

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!playAgain.value){
//            Text(
//                text = "Unscramble Word",
//                style = TextStyle(fontSize = 24.sp),
//                modifier = Modifier.padding(bottom = 16.dp)
//            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Unscramble Word",
                    style = TextStyle(fontSize = 24.sp),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Score: ${score.value}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE6EE9C))
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
                        style = TextStyle(fontSize = 16.sp),
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
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color.DarkGray),
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(text = "Check Answer")
                    }
                }

//            Text(
//                text = "Score: ${score.value}",
//                style = TextStyle(fontSize = 16.sp),
//                modifier = Modifier.padding(top = 8.dp)
//            )

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
                    // Reset all variables to play again
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
    }
}

//fun generateRandomQuestion(): Question {
//    val random = Random.Default
//    val randomIndex = random.nextInt(allWordsList.size)
//    val word = allWordsList[randomIndex]
//    val shuffledWord = word.toCharArray().apply { shuffle(random) }.joinToString("")
//    return Question(shuffledWord, word)
//}

//fun checkAnswer(correctAnswer: String, userAnswer: String) {
//    if (correctAnswer.lowercase(Locale.getDefault()) == userAnswer.lowercase(Locale.getDefault())) {
//        var score = 200
//    }
//}
//
//fun restartGame() {
//    // Reset game state variables here
//}

@Preview
@Composable
fun PreviewUnscrambleGame() {
    UnscrambleWordsGame()
}