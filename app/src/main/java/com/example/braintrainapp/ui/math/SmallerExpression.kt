package com.example.braintrainapp.ui.math

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

data class Expression(val expression: String, val value: Int)

fun generateExpression(level: Int): Expression {
    val operators = listOf("+", "-", "*", "/")
    val operator = operators[Random.nextInt(operators.size)]
    val a = Random.nextInt(1, level * 10)
    val b = Random.nextInt(1, level * 10)
    val value = when (operator) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> a / b
        else -> 0
    }
    return Expression("$a $operator $b", value)
}

@Composable
fun SmallerExpressionGame() {
    var level by remember { mutableStateOf(1) }
    var score by remember { mutableStateOf(0) }
    var gameOver by remember { mutableStateOf(false) }
    var expressions by remember { mutableStateOf(Pair(generateExpression(level), generateExpression(level))) }

    if (gameOver) {
        Text(text = "Game over! Your score: $score")
        Button(onClick = {
            gameOver = false
            score = 0
            level = 1
            expressions = Pair(generateExpression(level), generateExpression(level))
        }) {
            Text(text = "Restart")
        }
    } else {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Score: $score")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (expressions.first.value < expressions.second.value) {
                    score++
                    level = score / 10 + 1
                    expressions = Pair(generateExpression(level), generateExpression(level))
                } else {
                    gameOver = true
                }
            }) {
                Text(text = expressions.first.expression)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (expressions.second.value < expressions.first.value) {
                    score++
                    level = score / 10 + 1
                    expressions = Pair(generateExpression(level), generateExpression(level))
                } else {
                    gameOver = true
                }
            }) {
                Text(text = expressions.second.expression)
            }
        }
    }
}

@Preview
@Composable
fun PreviewSmallerExpressionGame() {
    SmallerExpressionGame()
}