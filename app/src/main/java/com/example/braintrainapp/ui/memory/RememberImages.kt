package com.example.braintrainapp.ui.memory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.braintrainapp.R
import com.example.braintrainapp.Screen
import com.example.braintrainapp.ui.data.ImageItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemembersImages(navController: NavController) {

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
    var mixedImage by remember { mutableStateOf<List<ImageItem>>(allImages.shuffled().take(4)) }
    var ramdomImage by remember { mutableStateOf<ImageItem>(getAImage(mixedImage)) }
    var mixedImage2 by remember { mutableStateOf<List<ImageItem>>(allImages.shuffled().take(3) + listOf<ImageItem>(ramdomImage ?: ImageItem(12, R.drawable.wolf))) }
    var selectedImage by remember { mutableStateOf<ImageItem?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val questionImage = ImageItem(13, R.drawable.question)
    var message by remember { mutableStateOf("") }
    var score by remember { mutableStateOf(0) }
    var level by remember { mutableStateOf(1) }
    val maxLevel by remember { mutableStateOf(10) }
    val maxTime = 20
    var timeLeft by remember { mutableStateOf(maxTime) }
    var gameOver by remember { mutableStateOf(false) }

    var isWin by remember { mutableStateOf(false) }
    var islose by remember { mutableStateOf(false) }
    var timer by remember { mutableStateOf(true) } // activate the timer
    var isTick by remember { mutableStateOf(false) } // activate the MagicTickEffect
    var isX by remember { mutableStateOf(false) } // activate the MagicXEffect
    var rememberTime by remember { mutableStateOf(maxTime-(maxTime -4)) }
    LaunchedEffect(key1 = timer) {
        timeLeft = maxTime
        rememberTime = maxTime-(maxTime -4)
        while (timeLeft > 0 ) {
            delay(1000)
            timeLeft--
            rememberTime--
        }

        if (gameOver == false && timeLeft == 0){
            gameOver = true
        }
        rememberTime = maxTime-(maxTime -4)
        timeLeft = maxTime
    }
    if (gameOver) {
        if (isWin) {
            AlertDialog(
                onDismissRequest = {
                    level = 1
                    score = 0
                    gameOver = false
                    isWin = false
                    mixedImage = allImages.shuffled().take(4)
                    ramdomImage = getAImage(mixedImage)
                    mixedImage2 = allImages.shuffled().filterNot { it in mixedImage }.take(3) + listOf<ImageItem>(ramdomImage ?: ImageItem(12, R.drawable.wolf))
                    selectedImage = null
                    timer = !timer
                },
                title = { Text("You Win") },
                text = { Text("You have completed all levels! Your Score: $score") },
                confirmButton = {
                    Button(onClick = {
                        // Reset game
                        level = 1
                        score = 0
                        gameOver = false
                        isWin = false
                        mixedImage = allImages.shuffled().take(4)
                        ramdomImage = getAImage(mixedImage)
                        mixedImage2 = allImages.shuffled().filterNot { it in mixedImage }.take(3) + listOf<ImageItem>(ramdomImage ?: ImageItem(12, R.drawable.wolf))
                        selectedImage = null
                        timer = !timer
                    }) {
                        Text("Restart")
                    }
                }
            )
        } else if (islose) {
            AlertDialog(
                onDismissRequest = {
                    level = 1
                    score = 0
                    gameOver = false
                    islose = false
                    mixedImage = allImages.shuffled().take(4)
                    ramdomImage = getAImage(mixedImage)
                    mixedImage2 = allImages.shuffled().filterNot { it in mixedImage }.take(3) + listOf<ImageItem>(ramdomImage ?: ImageItem(12, R.drawable.wolf))
                    selectedImage = null
                    timer = !timer
                },
                title = { Text("You lose") },
                text = { Text("OUT OF TIME! Your Score: $score") },
                confirmButton = {
                    Button(onClick = {
                        // Reset game
                        level = 1
                        score = 0
                        mixedImage = allImages.shuffled().take(4)
                        ramdomImage = getAImage(mixedImage)

                        gameOver = false
                        islose = false
                        selectedImage = null
                        timer = !timer
                    }) {
                        Text("Restart")
                    }
                }
            )
        }else {
            AlertDialog(
                onDismissRequest = {
                    gameOver = false
                    mixedImage = allImages.shuffled().take(4)
                    ramdomImage = getAImage(mixedImage)
                    mixedImage2 = allImages.shuffled().filterNot { it in mixedImage }.take(3) + listOf<ImageItem>(ramdomImage ?: ImageItem(12, R.drawable.wolf))
                    timer = !timer
                    selectedImage = null

                },
                title = { Text("Time Up") },
                text = { Text("OUT OF TIME! Your Score: $score") },
                confirmButton = {
                    Button(onClick = {
                        // Reset game
                        mixedImage = allImages.shuffled().take(4)
                        ramdomImage = getAImage(mixedImage)
                        mixedImage2 = allImages.shuffled().filterNot { it in mixedImage }.take(3) + listOf<ImageItem>(ramdomImage ?: ImageItem(12, R.drawable.wolf))
                        gameOver = false
                        timer = !timer
                        selectedImage = null

                    }) {
                        Text("Restart")
                    }
                }
            )
        }
    }
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Remember Image") },
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
                        text = "Level: ${level} / $maxLevel",
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
    ) {paddingValues->
        if(!isTick && !isX) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(204, 255, 255)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    // Show score and level
                    Text(text = "Score: $score",
                        modifier = Modifier
                            .padding(top = 16.dp),
                        style = TextStyle(fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.width(28.dp))
                    Text(text = "Time: $timeLeft",
                        modifier = Modifier
                            .padding(top = 16.dp),
                        style = TextStyle(fontSize = 30.sp, color = Color.Red, fontWeight = FontWeight.Bold))

                }
                Spacer(modifier = Modifier.height(30.dp))
                sample(mixedImage, selectImage = {
                    coroutineScope.launch {
                        if (selectedImage == null && rememberTime == maxTime - (maxTime -4)) {
                            delay(4000) // delay for 3 seconds
                            mixedImage = mixedImage.map {
                                if (it.imageRes == ramdomImage?.imageRes) {
                                    questionImage // replace the selected image with a placeholder image
                                } else {
                                    it
                                }
                            }
                        }
                    }
                }
                )
                Text(text = "The Dissapeared Image is: ",
                    modifier = Modifier
                        .padding( 16.dp),
                    style = TextStyle(fontSize = 24.sp, color = Color.Red, fontWeight = FontWeight.Bold))
                if(rememberTime <0) {

                    sample2(mixedImage2, selectImage = { image ->
                        selectedImage = image
                    })
                }


                Button(onClick = {
                    if (selectedImage?.imageRes == ramdomImage?.imageRes) {
                        coroutineScope.launch {
                            isTick = true
                            delay(1000)
                            isTick = false
                            score += 10
                        }
                    } else {
                        coroutineScope.launch {
                            isX = true
                            delay(1000)
                            isX = false
                        }
                        message = "You Lose"
                    }
                    if(level >= maxLevel) {
                        if (score >= (maxLevel*10)) {
                            isWin = true
                        } else {
                            islose = true
                            isWin = false
                        }
                        gameOver = true
                    }
                    level++
                    mixedImage = allImages.shuffled().take(4)
                    ramdomImage = getAImage(mixedImage)
                    mixedImage2 = allImages.shuffled().filterNot { it in mixedImage }.take(3) + listOf<ImageItem>(ramdomImage ?: ImageItem(12, R.drawable.wolf))
                    selectedImage = null
                    timer = !timer

                }) {
                    Text(text = "Check")
                }
            }

        }
        else if (isTick) {
            MagicTickEffect()

        } else if (isX) {
            MagicXEffect()
        }
    }
}
@Composable
fun sample(imagesList: List<ImageItem>, selectImage:() -> Unit = {}) {
    val shuffledIndices = imagesList.indices.toList()
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(shuffledIndices) { index ->
            ImageCard(
                image = imagesList[index],
                onImageSelected = { image ->
                    // Handle image selection
                },
            )
        }
        selectImage()
    }
}

@Composable
fun sample2(imagesList: List<ImageItem>, selectImage:(ImageItem) -> Unit = {}) {
    val shuffledIndices = imagesList.indices.toList()
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(shuffledIndices) { index ->
            ImageCard(
                image = imagesList[index],
                onImageSelected = selectImage
            )
        }
    }
}
@Composable
fun ImageCard(
    image: ImageItem,
    onImageSelected: (ImageItem) -> Unit,
) {
    var isSelected by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                isSelected = !isSelected
                if (isSelected) {
                    onImageSelected(image) // Set the selected image
                    isSelected = true
                }

            }
            .fillMaxWidth()
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
        horizontalAlignment = Alignment.CenterHorizontally
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

@Composable
private fun MagicXEffect() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.5f))
            .pointerInput(Unit) { detectTapGestures {} },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "X Symbol",
            modifier = Modifier
                .background(Color(255, 102, 102), MaterialTheme.shapes.extraLarge)
                .size(48.dp)

        )
    }
}

fun getAImage(list: List<ImageItem>): ImageItem {
    return list.random()
}

@Preview
@Composable
fun RemembersImagesPreview() {
    RemembersImages(
        navController = rememberNavController()
    )
}