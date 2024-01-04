package com.example.braintrainapp.ui.language_game.unscramble_words

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

data class Question(val phrase: String, val answer: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnscrambleWordsGame() {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var responseTime by remember { mutableStateOf(0L) }
    var totalScore by remember { mutableStateOf(0) }
    var averageResponseTime by remember { mutableStateOf(0L) }
    var bonusScore by remember { mutableStateOf(0) }
    var gameFinished by remember { mutableStateOf(false) }

    if (currentQuestionIndex < 10) {
        val currentQuestion = generateRandomQuestion()
        var userInput by remember { mutableStateOf("") }
        var timerRunning by remember { mutableStateOf(false) }
        var secondsLeft by remember { mutableStateOf(20) }

        LaunchedEffect(Unit) {
            timerRunning = true
            withContext(Dispatchers.Default) {
                while (secondsLeft > 0 && timerRunning) {
                    delay(1000)
                    secondsLeft--
                }
            }

            if (timerRunning) {
                checkAnswer(currentQuestion.answer, userInput)
                timerRunning = false
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Unscramble the word:")
            Text(text = currentQuestion.phrase)
            Text(text = "Seconds Left: $secondsLeft")
            Text(text = "Score: $score")
            Text(text = "Total Score: $totalScore")
            Text(text = "Average Response Time: $averageResponseTime")
            Text(text = "Bonus Score: $bonusScore")

            Button(
                onClick = {
                    checkAnswer(currentQuestion.answer, userInput)
                    timerRunning = false
                }
            ) {
                Text(text = "Submit")
            }

            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    } else {
        gameFinished = true
        averageResponseTime = responseTime / 10
        totalScore = score + bonusScore
    }

    if (gameFinished) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Game Finished")
            Text(text = "Total Score: $totalScore")
            Text(text = "Average Response Time: $averageResponseTime")
            Button(
                onClick = { restartGame() },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Restart Game")
            }
        }
    }
}

fun generateRandomQuestion(): Question {
    val random = Random()
    val randomIndex = random.nextInt(allWordsList.size)
    val word = allWordsList[randomIndex]
    val shuffledWord = word.toCharArray().apply { shuffle(random) }.joinToString("")
    return Question(shuffledWord, word)
}

fun checkAnswer(correctAnswer: String, userAnswer: String) {
    if (correctAnswer.lowercase(Locale.getDefault()) == userAnswer.lowercase(Locale.getDefault())) {
        score += 200
    }
}

fun restartGame() {
    // Reset game state variables here
}