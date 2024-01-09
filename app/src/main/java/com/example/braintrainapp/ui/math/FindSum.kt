package com.example.braintrainapp.ui.math

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


@Composable
fun FindSum() {
    val maxElement by remember { mutableStateOf(6) }
    var condition by remember { mutableStateOf(10) }
    val wallpaperImage = painterResource(id = R.drawable.math_wallpaper)
    var numbers by remember { mutableStateOf(createList(condition, maxElement)) }

    var message by remember { mutableStateOf("") }
    var openCards by remember { mutableStateOf(List(numbers.size) { false }) }
    var coroutineScope  =  rememberCoroutineScope()
    var lastOpenedCardIndex by remember { mutableStateOf(-1) }
    var score by remember { mutableStateOf(0) }
    var level by remember { mutableStateOf(1) }
    var numberOfQuestions by remember { mutableStateOf(1) }
    val maxNumberOfQuestion by remember { mutableStateOf(15) }
    val maxTime = 10
    var isFail by remember { mutableStateOf(false) }
    var isTimeOut by remember { mutableStateOf(false) }
    var gameOver by remember { mutableStateOf(false) }
    var timLeft by remember { mutableStateOf(maxTime) }

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
            gameOver = checkOver(numberOfQuestions, maxNumberOfQuestion)
            level++
            numberOfQuestions++
        } else {
            if (isFail) {
                numbers = createList(condition, maxElement)
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

                                .clickable { /* Handle menu click */ }
                        )
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Guidelines",
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .clickable { /* Handle guidelines click */ }
                        )
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
                                    numberOfQuestions = 1
                                    level = 1
                                    condition = 10
                                    timLeft = maxTime
                                }) {
                                    Text(text = "Restart")
                                }
                            }
                            lastOpenedCardIndex = -1
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
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Center,
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
                                Text(text = "Question $numberOfQuestions / $maxNumberOfQuestion",
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
                                Text(text = "Which two numbers sum to ${condition} ?",
                                    modifier = Modifier
                                        .padding(16.dp),
                                    style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold)
                                )
                                Column(modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White),
                                    horizontalAlignment = Alignment.CenterHorizontally

                                ) {
                                    Column(modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentSize(Alignment.Center)
                                        .padding(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center) {
                                    }
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            ,
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        LazyVerticalGrid(
                                            columns = GridCells.Fixed(2),
                                            contentPadding = PaddingValues(16.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ){
                                            items(numbers.size) { index ->
                                                CardView(number = numbers[index], isOpen = openCards[index], onClick = {
                                                    coroutineScope.launch {
                                                        if (openCards.count { it } >= 2) {
                                                            return@launch



                                                        }
                                                        openCards= openCards.toMutableList().also { it[index] = true }
                                                        if (lastOpenedCardIndex == -1) {
                                                            lastOpenedCardIndex = index
                                                        } else if(lastOpenedCardIndex == index){
                                                            openCards= openCards.toMutableList().also {
                                                                it[index] = false
                                                            }
                                                            lastOpenedCardIndex = -1
                                                        } else {
                                                            if (numbers[lastOpenedCardIndex] + numbers[index] == 10) {
                                                                message = "Success! You found two numbers that sum to 10."
                                                                score += 10
                                                                delay(1000)
                                                                openCards = openCards.toMutableList().also {
                                                                    it[lastOpenedCardIndex] = false
                                                                    it[index] = false
                                                                }
                                                                numberOfQuestions++
                                                                timLeft = maxTime
                                                                numbers = createList(condition, maxElement)
                                                                lastOpenedCardIndex = -1
                                                            } else if (numbers[lastOpenedCardIndex] + numbers[index] != 10) {
                                                                message = "Failure! The selected numbers do not sum to 10."
                                                                delay(1000)
                                                                openCards = openCards.toMutableList().also {
                                                                    it[lastOpenedCardIndex] = false
                                                                    it[index] = false
                                                                }
                                                                numberOfQuestions++
                                                                isFail = true
                                                                delay(1000)
                                                                isFail = false
                                                                numbers = createList(condition, maxElement)
                                                                lastOpenedCardIndex = -1
                                                            } else {
                                                                openCards = openCards.toMutableList().also {
                                                                    it[lastOpenedCardIndex] = false
                                                                    it[index] = false
                                                                }
                                                                lastOpenedCardIndex = -1
                                                            }
                                                        }
                                                        if(numberOfQuestions ==5 || numberOfQuestions == 10 || numberOfQuestions == 15 ){
                                                            condition += 20
                                                        }
                                                        if (numberOfQuestions > maxNumberOfQuestion) {

                                                            gameOver = true
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
            }
        }
    }
}
@Composable
fun CardView(number: Int, isOpen: Boolean,onClick: () -> Unit) {
    Box(

        modifier = Modifier
            .width(100.dp)
            .height(60.dp)
            .padding(8.dp, 8.dp)
            .clickable(onClick = onClick)
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .border(1.dp, Color.Black, shape = MaterialTheme.shapes.medium),


    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = number.toString(),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black.copy(alpha = 0.7f))
            )
            Box(modifier = Modifier
                .size(24.dp)
                .background(if (isOpen) Color.Red else Color.White, CircleShape)
                .border(1.dp, Color.Black, CircleShape),
            )
        }
    }
}
fun createList(sumNumber: Int, maxElement: Int): List<Int> {
    val a = Random.nextInt(1, sumNumber)
    val b = sumNumber - a
    val list = mutableListOf(a, b) // a and b sum up to 10

    for (i in 1..(maxElement-2)) { // generate remaining elements
        while (true) {
            val c = Random.nextInt(1, sumNumber) // generate numbers from 1 to 11
            val checkSum = checkedSum(c, list, sumNumber)
            if (c != a && c != b && checkSum) {
                list.add(c)
                break
            }
        }
    }
    return list.shuffled() // shuffle the list
}
fun checkedSum(checkedNumber:Int, list: List<Int>, sumNumber: Int): Boolean{
    for(i in list){
        if(checkedNumber + i == sumNumber) {return false}
    }
    return true
}
fun checkGameOver(numberOfQuestion: Int, maxNumberOfQuestion: Int): Boolean {
    if (numberOfQuestion > maxNumberOfQuestion) {
        return true
    }
    return false
}
@Preview
@Composable
fun PreviewFindSum() {
    FindSum()
//    CardView(number = 1, isOpen = false, onClick = {})
}
