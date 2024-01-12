package com.example.braintrainapp.ui.memory

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.example.braintrainapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.braintrainapp.Screen
import com.example.braintrainapp.ui.data.ImageItem
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindNewImage(navController: NavController) {
    var selectedImage by remember {mutableStateOf<ImageItem?>(null) }
    var previousImages by remember { mutableStateOf<List<ImageItem>>(emptyList()) }
    var indexTest by remember { mutableStateOf(0) }
    var randomImages by remember { mutableStateOf<List<ImageItem>>(emptyList()) }
    val allImages = listOf<ImageItem>(
        ImageItem(1, R.drawable.fil),
        ImageItem(2, R.drawable.kedi),
        ImageItem(3, R.drawable.tavuk),
        ImageItem(4, R.drawable.balik),
        ImageItem(5, R.drawable.ari),
        ImageItem(6, R.drawable.ordek),
        ImageItem(7, R.drawable.camel),
        ImageItem(8, R.drawable.coala),
        ImageItem(9, R.drawable.fox),
        ImageItem(10, R.drawable.lion),
        ImageItem(11, R.drawable.monkey),
        ImageItem(12, R.drawable.wolf),
    )
    var selectedOne by remember { mutableStateOf(false) }
    val lives = 2
    var currentLives by remember { mutableStateOf(1) }
    var gameOver by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }
    var isFailed by remember { mutableStateOf(false) }
    var isWin by remember { mutableStateOf(false) }
    var level by remember { mutableStateOf(1) }
    val maxLevel = 7

    LaunchedEffect(selectedOne){
        if(selectedOne == true) {
            score += 10
        }
        delay(3000)
        selectedOne = false

    }
    LaunchedEffect(selectedImage) {
        if(randomImages.isNotEmpty()) {
            delay(3000)
            randomImages = allImages.filter { it != selectedImage && it !in previousImages }.shuffled().take(3) .toMutableList()
            randomImages += previousImages
            randomImages = randomImages.shuffled()
            indexTest++
            selectedOne = false
        }
        if(randomImages.isEmpty()) {
            randomImages = allImages.filter { it != selectedImage && it !in previousImages }.shuffled().take(3) .toMutableList()

            randomImages += previousImages
            randomImages = randomImages.shuffled()
            indexTest++
            selectedOne = false
        }
    }
if (gameOver) {

    AlertDialog(
        onDismissRequest = { gameOver = false },
        title = { Text(
            "You chose the same image twice!"
        ) },
        text = { Text(
            if (isFailed==true) "You lose, your point: $score"
            else "You still have a chance!"
        ) },
        confirmButton = {
            Button(onClick = {
                if(isFailed == true){
                    score = 0
                    currentLives = 1
                } else {
                    currentLives++
                }
                // Reset game
                level = 1


                    previousImages = emptyList()
                    randomImages = emptyList()
                    selectedImage = null
                    selectedOne = false


                    isFailed = false
                    isWin = false
                    gameOver = false

            }) {
                Text("Restart")
            }
        }
    )
}
    if (isWin) {

        AlertDialog(
            onDismissRequest = { isWin = false },
            title = { Text("You Win") },
            text = { Text("You have matched all cards!") },
            confirmButton = {
                Button(onClick = {
                    // Reset game
                    previousImages = emptyList()
                    randomImages = emptyList()
                    selectedImage = null
                    selectedOne = false
                    indexTest = 0
                    currentLives = 1
                    score = 0
                    isFailed = false
                    isWin = false
                    level = 1
                }) {
                    Text("Restart")
                }
            }
        )
}

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Find New Image") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Menu",
                        modifier = Modifier
                            .padding(12.dp)
                            .clickable {
                                navController.navigate(Screen.MemoryGame.route)
                            }
                    )
                },
                actions = {
                    Text(
                        text = "Level: ${level}",
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
                .background(Color(204, 255, 255)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(text = "Lives: " + currentLives + "/" + lives,
                modifier = Modifier.padding(vertical = 8.dp),
                style = TextStyle(fontSize = 20.sp, color = Color.Red, fontWeight = FontWeight.Bold),
            )
            Text(
                text = "Score: $score",
                modifier = Modifier.padding(vertical = 8.dp),
                style = TextStyle(fontSize = 20.sp, color = Color.Green, fontWeight = FontWeight.Bold),
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),

                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = paddingValues,
            ) {
                items(randomImages.size) { index ->
                    ImageCard(
                        image = randomImages[index]!!,
                        onImageSelected = { image ->
                            selectedImage = image
                            selectedOne = true
                            level++
                        },
                        onPreviousImageSelected = { image ->
                            previousImages += image}
                    )
                }
            }
        }
        if (selectedOne == true && selectedImage in  previousImages.dropLast(1 ) && selectedImage != null) {
            previousImages = emptyList()
            selectedOne = false
            gameOver = true
            if(currentLives >= lives) {
                isFailed = true
                currentLives = 1
            }
        }
        if (selectedOne == true && selectedImage != null) {
            MagicTickEffect()
            if (level > maxLevel) {
                isWin = true
            }
            selectedOne == false
        }

    }
}
@Composable
fun ImageCard(
    image: ImageItem,
    onImageSelected: (ImageItem) -> Unit,
    onPreviousImageSelected: (ImageItem) -> Unit
) {
    var isSelected by remember { mutableStateOf(false) }
    var isClicked by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                isSelected = !isSelected
                if (isSelected) {
                    onImageSelected(image) // Set the selected image
                    isSelected = true
                }
                onPreviousImageSelected(image)
            }
            .fillMaxSize()
            .background(if (isSelected) Color.Gray else Color.White, CircleShape)
            .border(
                if (isSelected) 4.dp else 1.dp,
                if (isSelected) Color.Blue else Color.Black,
                CircleShape
            )
            .clip(CircleShape)
    ) {
        Image(
            painter = painterResource(id = image.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )
    }
    if (isSelected) {
        LaunchedEffect(Unit) {
            delay(3000) // delay for 3 seconds
            isSelected = false // Reset to default background and border
        }
    }
}
@Composable
private fun MagicTickEffect() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.5f))
            .pointerInput(Unit) { detectTapGestures {} },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Tick Symbol",
            modifier = Modifier
                .background(Color.Green, MaterialTheme.shapes.extraLarge)
                .size(48.dp)

        )
    }
}

@Preview(showBackground = true)
@Composable
fun FindNewImagePreview() {
    FindNewImage(
        navController = rememberNavController()
    )
}