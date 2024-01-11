//package com.example.memorygame.ui.memory
//
//import android.media.Image
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.border
//import androidx.compose.foundation.gestures.detectTapGestures
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.material3.Button
//import androidx.compose.material3.Card
//import androidx.compose.runtime.Composable
//import androidx.compose.material3.Text
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.draw.alpha
//import kotlinx.coroutines.delay
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//
//
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Check
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.material.icons.filled.Check
//import androidx.compose.material.icons.filled.Info
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.ui.text.font.FontWeight
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.Scaffold
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.material3.AlertDialog
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.painter.Painter
//import com.example.braintrainapp.R
//import com.example.braintrainapp.ui.data.ImageItem
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun RememberImage() {
//    var score by remember { mutableStateOf(0) }
//    var showTickEffect by remember { mutableStateOf(false) }
//    var level by remember { mutableStateOf(1) }
//    var currentRound by remember { mutableStateOf(1) }
//    var isTimeUp by remember { mutableStateOf(false) }
//    var selectedImage by remember { mutableStateOf(-1) }
//    var selectedImage2 by remember { mutableStateOf(-1) }
//
//    var selectedImages by remember { mutableStateOf<List<Int>>(emptyList()) }
//    var scoreIncrease by remember { mutableStateOf(false) }
//    var gameCompleted by remember { mutableStateOf(false) }
//    var images = listOf<ImageItem>(
//        ImageItem(1, R.drawable.fil),
//        ImageItem(2, R.drawable.kedi),
//        ImageItem(3, R.drawable.tavuk),
//        ImageItem(4, R.drawable.balik),
//        ImageItem(5, R.drawable.ari),
//        ImageItem(6, R.drawable.ordek),
//        ImageItem(7, R.drawable.camel),
//        ImageItem(8, R.drawable.coala),
//        ImageItem(9, R.drawable.fox),
//        ImageItem(10, R.drawable.lion),
//        ImageItem(11, R.drawable.monkey),
//        ImageItem(12, R.drawable.wolf),
//    )
//    var message by remember { mutableStateOf("Let's guess") }
//    var image  = getAImage(images)
//    var mixedImages by remember { mutableStateOf<List<ImageItem>>(images.shuffled().take(4)) }
//   var mixedImages2 by remember { mutableStateOf<List<ImageItem>>(images.filter { it !in mixedImages }.shuffled().take(4)) }
//    val mutableMixedImages2 = mixedImages2.toMutableList()
//    var coroutineScope = rememberCoroutineScope()
//    var isTrue by remember { mutableStateOf(false) }
//
//    if (gameCompleted) {
//        AlertDialog(
//            onDismissRequest = {
//                // Quay lại màn hình trước đó hoặc thoát ứng dụng
//            },
//            title = { Text(text = "Congratulations!") },
//            text = { Text(text = "You've completed all levels!") },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        // Quay lại màn hình trước đó hoặc thoát ứng dụng
//                    }
//                ) {
//                    Text("OK")
//                }
//            }
//        )
//    }
//
//
//    Scaffold (
//        topBar = {
//            TopAppBar(
//                title = { Text("Remember Image") },
//                navigationIcon = {
//                    Icon(
//                        imageVector = Icons.Filled.ArrowBack,
//                        contentDescription = "Menu",
//                        modifier = Modifier
//                            .padding(12.dp)
//                            .clickable { /* Handle menu click */ }
//                    )
//                },
//                actions = {
//                    Icon(
//                        imageVector = Icons.Filled.Info,
//                        contentDescription = "Guidelines",
//                        modifier = Modifier
//                            .padding(end = 12.dp)
//                            .clickable { /* Handle guidelines click */ }
//                    )
//
//                },
//                colors = TopAppBarDefaults.largeTopAppBarColors(
//                    containerColor = Color(204, 255, 255),
//                )
//            )
//        }
//    ) { paddingValues ->
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(10.dp),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
//            ) {
//                Row(
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ){
//                    // Show score and level
//                    Text("Score: $score")
//                    Spacer(modifier = Modifier.width(28.dp))
//                    Text("Level: $level")
//
//                }
//                // Show images for memorization
//                Text("Remember these images:")
//                sample(imagesList = mixedImages, selectImage = {
//                    coroutineScope.launch {
//                        delay(5000)
//                        image = getAImage(mixedImages)
//                        mixedImages =
//                        mutableMixedImages2[0] = image
//                        mixedImages2 = mutableMixedImages2.shuffled()
//                    }
//                })
//                sample2(mutableMixedImages2, selectImage = {imageSelected ->
//                    coroutineScope.launch {
//                        delay(1000)
//                        image = mixedImages2.random()
//                        mixedImages2 = mixedImages2.map { if (it == image) R.drawable.question else it }
//                        mutableMixedImages2[0] = image
//                        mixedImages2 = mutableMixedImages2.shuffled()
//                        selectedImage = imageSelected.imageRes
//                    }
//                })
//
//                Text(text = message,
//                    style = TextStyle(
//                        fontSize = 20.sp, // Increase the font size to make it bigger
//                        color = Color.Black // Change the color to make it more highlighted
//                    ),
//                    modifier = Modifier.padding(vertical = 16.dp)
//                )
////                if (selectedImage == -1) {
////                    selectedImage = R.drawable.ari
////                } else {
////
////                    Image(painter = painterResource(id = selectedImage), contentDescription = "")
////                }
//                Button(
//                    onClick = {
//                        // Check if the user's guess is correct
//                        if (selectedImage == image) {
//                            // If the guess is correct, show a success message
//                            message = "Correct guess!"
//                            score += 10
//                            isTrue = true
//                        } else {
//                            // If the guess is incorrect, show an error message
//                            message = "Incorrect guess!"
//                            isTrue = false
//                        }
//                    },
//                    modifier = Modifier.padding(8.dp)
//                ) {
//                    Text("Check")
//                }
//            }
//            if (showTickEffect) {
//                MagicTickEffect()
//            }
//            if(isTrue == true) {
//                LaunchedEffect(Unit){
//                     delay(1000)
//                    showTickEffect = true
//                    delay(3000)
//                    showTickEffect = false
//                    isTrue = false
//                }
//
//
//            }
//        }
//    }
//}
//
//@Composable
//fun sample(imagesList: List<ImageItem>, selectImage:() -> Unit = {}) {
//    val shuffledIndices = imagesList.indices.toList().shuffled()
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(4),
//        modifier = Modifier
//            .fillMaxWidth()
//
//    ) {
//        items(shuffledIndices) { index ->
//            ImageCard(
//                image = imagesList[index],
//                onImageSelected = { image ->
//                    // Handle image selection
//                },
//            )
//        }
//        selectImage()
//    }
//}
//@Composable
//fun sample2(imagesList: List<ImageItem>, selectImage:(ImageItem) -> Unit = {}) {
//    val shuffledIndices = imagesList.indices.toList().shuffled()
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(4),
//        modifier = Modifier
//            .fillMaxWidth()
//
//    ) {
//        items(shuffledIndices) { index ->
//            ImageCard(
//                image = imagesList[index],
//
//                onImageSelected = selectImage
//            )
//        }
//
//    }
//}
//@Composable
//fun ImageCard(
//    image: ImageItem,
//    onImageSelected: (ImageItem) -> Unit,
//) {
//    var isSelected by remember { mutableStateOf(false) }
//    Box(
//        modifier = Modifier
//            .padding(4.dp)
//            .clickable {
//                isSelected = !isSelected
//                if (isSelected) {
//                    onImageSelected(image) // Set the selected image
//                    isSelected = true
//                }
//
//            }
//            .fillMaxWidth()
//            .background(if (isSelected) Color.Gray else Color.White, CircleShape)
//            .border(
//                if (isSelected) 4.dp else 1.dp,
//                if (isSelected) Color.Blue else Color.Black,
//                CircleShape
//            )
//            .clip(CircleShape)
//    ) {
//        Image(
//            painter = painterResource(id = image.imageRes),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxSize()
//                .clip(CircleShape)
//        )
//    }
//    if (isSelected) {
//        LaunchedEffect(Unit) {
//            delay(3000) // delay for 3 seconds
//            isSelected = false // Reset to default background and border
//        }
//    }
//}
//@Composable
//private fun MagicTickEffect() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.LightGray.copy(alpha = 0.5f))
//            .pointerInput(Unit) { detectTapGestures {} },
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
//    ) {
//        Icon(
//            imageVector = Icons.Default.Check,
//            contentDescription = "Tick Symbol",
//            modifier = Modifier
//                .background(Color.Green, MaterialTheme.shapes.extraLarge)
//                .size(48.dp)
//
//        )
//    }
//}
//fun getAImage(list: List<ImageItem>): ImageItem {
//    return list.random()
//}
//@Preview
//@Composable
//fun RememberImageScreenPreview(
//) {
//    RememberImage()
//
//}
