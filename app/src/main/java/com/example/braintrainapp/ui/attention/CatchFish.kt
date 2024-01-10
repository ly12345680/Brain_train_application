package com.example.braintrainapp.ui.attention
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.braintrainapp.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@Composable
fun AnimatedFish(
    fishModifier: Modifier,
    initialOffset: Offset,
    imageResourceId: Int,
    animationDurationMillis: Int = 3000,
    delayMillis: Int = 1000,
    frameWidth: Float = 300f, // Width of the frame
    frameHeight: Float = 300f // Height of the frame
) {
    val animatableX = remember { Animatable(initialOffset.x) }
    val animatableY = remember { Animatable(initialOffset.y) }

    DisposableEffect(Unit) {
        val job = GlobalScope.launch {
            // Stop animation when the composable is removed
            animatableX.stop()
            animatableY.stop()
        }

        onDispose {
            job.cancel()
        }
    }

    LaunchedEffect(animatableX, animatableY) {
        while (true) {
            val targetX = (0..frameWidth.toInt()).random().toFloat()
            val targetY = (0..frameHeight.toInt()).random().toFloat()

            animatableX.animateTo(
                targetValue = targetX,
                animationSpec = tween(durationMillis = animationDurationMillis)
            )
            animatableY.animateTo(
                targetValue = targetY,
                animationSpec = tween(durationMillis = animationDurationMillis)
            )

            // Add a delay between animations
            delay(delayMillis.toLong())
        }
    }

    Image(
        painter = painterResource(id = imageResourceId),
        contentDescription = "Fish",
        modifier = fishModifier
            .offset(animatableX.value.dp, animatableY.value.dp)
            .size(60.dp)
            .padding(5.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatchFish(){
    var fish1Offset by remember { mutableStateOf(Offset(0f, 0f)) }
    var fish2Offset by remember { mutableStateOf(Offset(0f, 0f)) }
    var fish3Offset by remember { mutableStateOf(Offset(0f, 0f)) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Catch Fish")},
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Menu",
                        modifier = Modifier
                            .padding(12.dp)
                            .clickable { /* Handle menu click */ }
                    )
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.MailOutline,"")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Search,"")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Settings,"")
                    }
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
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(paddingValues = paddingValues)
            .background(Color(230, 255, 255)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(236, 135, 14)),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(236, 135, 14)),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "FIND DIFFERENCES IN PICTURE",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black),
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .fillMaxSize()
        ){
            Box(
                modifier = Modifier
                    .offset(x = 80.dp, y = 200.dp)
                    .size(100.dp)
                    .border(
                        width = 2.dp,
                        color = Color.Blue
                    )

            ){
                Image(
                    painter = painterResource(id = R.drawable.boat),
                    contentDescription = "boat_1",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(10.dp)
                        .align(Alignment.Center)
                )
            }
            Box(
                modifier = Modifier
                    .offset(x = 70.dp, y = 600.dp)
                    .size(100.dp)
                    .border(
                        width = 2.dp,
                        color = Color.Blue
                    )

            ){
                Image(
                    painter = painterResource(id = R.drawable.boat),
                    contentDescription = "boat_2",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(10.dp)
                        .align(Alignment.Center)
                )
            }
            Box(
                modifier = Modifier
                    .offset(x = 290.dp, y = 400.dp)
                    .size(100.dp)
                    .border(
                        width = 2.dp,
                        color = Color.Blue
                    )
            ){
                Image(
                    painter = painterResource(id = R.drawable.boat),
                    contentDescription = "boat_3",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(10.dp)
                        .align(Alignment.Center)
                )
            }
            //Fish
            Box(
                modifier = Modifier
                    .offset(x = 50.dp, y = 50.dp)
                    .size(60.dp)
                    .border(
                        width = 2.dp,
                        color = Color.Blue
                    )
            ){
                AnimatedFish(
                    fishModifier = Modifier
                        .size(60.dp)
                        .padding(5.dp)
                        .border(width = 2.dp, color = Color.Blue),
                    initialOffset = fish1Offset,
                    imageResourceId = R.drawable.f_1
                )
            }
            Box(
                modifier = Modifier
                    .offset(x = 10.dp, y = 400.dp)
                    .size(60.dp)
                    .border(
                        width = 2.dp,
                        color = Color.Blue
                    )
            ){
                AnimatedFish(
                    fishModifier = Modifier
                        .size(60.dp)
                        .padding(5.dp)
                        .border(width = 2.dp, color = Color.Blue),
                    initialOffset = fish2Offset,
                    imageResourceId = R.drawable.f_1
                )
            }
            Box(
                modifier = Modifier
                    .offset(x = 20.dp, y = 30.dp)
                    .size(60.dp)
                    .border(
                        width = 2.dp,
                        color = Color.Blue
                    )
            ){
                AnimatedFish(
                    fishModifier = Modifier
                        .size(60.dp)
                        .padding(5.dp)
                        .border(width = 2.dp, color = Color.Blue),
                    initialOffset = fish3Offset,
                    imageResourceId = R.drawable.f_1
                )
            }
        }
    }
    }
}

@Preview
@Composable
fun reviewCatchFish(){
    CatchFish()
}