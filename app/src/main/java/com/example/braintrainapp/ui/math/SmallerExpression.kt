package com.example.braintrainapp.ui.math

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.braintrainapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.braintrainapp.Screen
import com.example.braintrainapp.ui.language_game.unscramble_words.SearchDialog6

@Composable
fun SearchDialog9(onDismiss: () -> Unit, onSearch: () -> Unit) {
//    var playerName by remember { mutableStateOf(TextFieldValue()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Game Instructions")
        },
        text = {
            Column {
                Text("Step 1: Calculate the calculations available on the screen.")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Step 2: Choose the answer based on the requirements of the question..")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Time is 20s for each question")
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
fun SmallerExpressionGame(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var level by remember { mutableStateOf(1) }
    var score by remember { mutableStateOf(0) }
    var gameOver by remember { mutableStateOf(false) }
    var expressions by remember { mutableStateOf(Pair(generateExpression(level), generateExpression(level))) }
    val wallpaperImage = painterResource(id = R.drawable.math_wallpaper)

    var isFail by remember { mutableStateOf(false) }
    var isTimeOut by remember { mutableStateOf(false) }
    var maxTime by remember { mutableStateOf(20) }
    var timLeft by remember { mutableStateOf(maxTime) }
    var numberOfQuestion by remember { mutableStateOf(1) }
    val maxNumberOfQuestion = 10
    var isDialogVisible by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = isFail, key2 = isTimeOut) {
        timLeft = maxTime
        if (!isTimeOut ) {
            while (timLeft > 0) {
                delay(1000)
                timLeft--
            }
            isFail = true
            isTimeOut = true
            timLeft = maxTime
            gameOver = checkOver(numberOfQuestion, maxNumberOfQuestion)
            level++
            numberOfQuestion++
        } else {
            if (isFail) {
                expressions = Pair(generateExpression(level), generateExpression(level))
            }
            delay(1000)
            isTimeOut = false
            isFail = false
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = wallpaperImage,
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxSize(),

            contentScale = ContentScale.FillBounds
        )
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()

                        .background(Color.Blue.copy(alpha = 0.3f)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp)
                            .height(50.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier

                                .clickable {
                                    navController.popBackStack()
                                }
                        )
                        IconButton(onClick = {
                            isDialogVisible = true
                        }) {
                            Icon(Icons.Filled.Info, contentDescription = "Search")
                        }
                        // Kiểm tra nếu Dialog nên hiển thị
                        if (isDialogVisible) {
                            SearchDialog9(
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


                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                        if (gameOver) {
                            Column  (
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Color.Green.copy(alpha = 0.4f),
                                            shape = MaterialTheme.shapes.medium
                                        ),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Text(
                                    text = "Game over! Your score: $score",
                                    style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White),
                                    modifier = Modifier
                                        .padding(16.dp),
                                    textAlign = TextAlign.Center
                                )
                                Button(onClick = {
                                    gameOver = false
                                    score = 0
                                    numberOfQuestion = 1
                                    level = 1
                                    expressions = Pair(generateExpression(level), generateExpression(level))
                                }) {
                                    Text(text = "Restart")
                                }
                            }
                            isFail = false
                            isTimeOut = false
                        } else if(isFail) {
                            Column  (
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Color.Red.copy(alpha = 0.4f),
                                        shape = MaterialTheme.shapes.medium
                                    ),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Text(
                                    text = "Game Fail! Continue",
                                    style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White),
                                    modifier = Modifier
                                        .padding(16.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                            expressions = Pair(generateExpression(level), generateExpression(level))
                        }
                        else {
                            Row(modifier = Modifier
                                .padding(10.dp)
                                .size(180.dp, 50.dp)
                                .background(
                                    color = Color.White.copy(alpha = .5f),
                                    shape = MaterialTheme.shapes.extraLarge
                                )
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
                                        .fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,

                                ) {
                                    Text(
                                        text = "Score: " + score.toString(),
                                        modifier = androidx.compose.ui.Modifier
                                            .fillMaxWidth(),
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                        color = Color.Red,
                                        style = TextStyle(fontFamily = FontFamily(Font(R.font.simonetta_regular)), fontWeight = FontWeight.Bold),
                                        fontSize = 25.sp
                                    )
                                }
                            }
                            Text(text = "Time: $timLeft",
                                style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold),
                                color = Color(0, 0, 204))

                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(text = "Question $numberOfQuestion",
                                    style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold),
                                    color = Color(0, 0, 204))
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 16.dp)
                                    .background(Color.White, shape = MaterialTheme.shapes.medium),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "Which one is smaller?",
                                    modifier = Modifier
                                        .padding(16.dp),
                                    style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold)
                                )
                                buttonStyle(text = expressions.first.expression, onClick = {
                                    coroutineScope.launch{
                                        if (expressions.first.value < expressions.second.value) {
                                            gameOver = checkOver(numberOfQuestion, maxNumberOfQuestion)
                                            score += 10
                                            level++
                                            isFail = false
                                            isTimeOut = true
                                            numberOfQuestion++
                                            expressions = Pair(generateExpression(level), generateExpression(level))
                                        } else {
                                            level++
                                            isFail = true
                                            delay(1000)
                                            isFail = false
                                        }
                                    }
                                } )
                                buttonStyle(text = expressions.second.expression, onClick = {
                                    coroutineScope.launch {
                                        if (expressions.second.value < expressions.first.value) {
                                            gameOver = checkOver(numberOfQuestion, maxNumberOfQuestion)
                                            score += 10
                                            level++
                                            isFail = false
                                            isTimeOut = true
                                            numberOfQuestion++
                                            expressions = Pair(generateExpression(level), generateExpression(level))
                                        }
                                        else {
                                            level++
                                            isFail = true
                                            delay(1000)
                                            numberOfQuestion++
                                            isFail = false

                                        }
                                    }
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun buttonStyle(text: String, onClick: () -> Unit) {

    Button(
        border = BorderStroke(1.5.dp, Color.Blue),
        colors = ButtonDefaults.buttonColors(Color.White, Color.Blue),
        modifier = Modifier
            .padding(16.dp, 10.dp)
            .fillMaxWidth()
        ,
        onClick =onClick
    ){
        Text(text = text,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
    }
}
fun checkOver (currentLevel: Int, maxLevel: Int): Boolean {
    return currentLevel >= maxLevel
}

@Preview
@Composable
fun PreviewSmallerExpressionGame() {
    SmallerExpressionGame(navController = rememberNavController())
}