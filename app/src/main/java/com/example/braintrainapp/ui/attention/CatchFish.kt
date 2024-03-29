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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.braintrainapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sqrt

@Composable
fun AnimatedFish(
    fishModifier: Modifier,
    initialOffset: Offset,
    imageResourceId: Int,
    animationDurationMillis: Int = 2000,
    delayMillis: Int = 500,
    frameWidth: Float = 200f, // Width of the frame
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
@Composable
fun SearchDialog(onDismiss: () -> Unit, onSearch: () -> Unit) {
//    var playerName by remember { mutableStateOf(TextFieldValue()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Game Instructions")
        },
        text = {
            Column {
                Text("Step 1: Players must ensure that boats at sea catch as many fish as possible.")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Step 2: When the player predicts the fish will move to the boat, touch the screen to catch the fish and get points.")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Step 3: For each fish caught, you will receive 1,000 points.")
                Spacer(modifier = Modifier.height(8.dp))
                Text("In 2 minutes, try to catch as many fish and accumulate the most points..")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatchFish(navController: NavController){
    var fish1Offset by remember { mutableStateOf(Offset(0f, 0f)) }
    var fish2Offset by remember { mutableStateOf(Offset(0f, 0f)) }
    var fish3Offset by remember { mutableStateOf(Offset(0f, 0f)) }
    var fish4Offset by remember { mutableStateOf(Offset(0f, 0f)) }
    var fish5Offset by remember { mutableStateOf(Offset(0f, 0f)) }
    var fish6Offset by remember { mutableStateOf(Offset(0f, 0f)) }
    var fish7Offset by remember { mutableStateOf(Offset(0f, 0f)) }
    var fish8Offset by remember { mutableStateOf(Offset(0f, 0f)) }
    var fish9Offset by remember { mutableStateOf(Offset(0f, 0f)) }
    var fish11Offset by remember { mutableStateOf(Offset(0f, 0f)) }
    var fish10Offset by remember { mutableStateOf(Offset(0f, 0f)) }
    var fish12Offset by remember { mutableStateOf(Offset(0f, 0f)) }

    //timeline
    var score by remember { mutableStateOf(0) }
    val gameTime = 5
    var remainingTime by remember {
        mutableStateOf(gameTime)
    }
    var timerJob by remember {
        mutableStateOf<Job?>(null)
    }
    var diaLogShow by remember {
        mutableStateOf(false)
    }
    fun endGame(){
        timerJob?.cancel()
        diaLogShow = true
    }
    suspend fun startGame(){
        timerJob = CoroutineScope(Dispatchers.Default).launch {
            while (remainingTime > 0){
                delay(1000)
                remainingTime--
            }
            endGame()
        }
    }
    if (timerJob == null){
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(Unit) {
            timerJob = coroutineScope.launch {
                startGame()
            }
        }
    }
// Display the dialog when diaLogShow is true
    if (diaLogShow) {
        GameOverDialog(
            score = score, // Pass the score to the dialog
            onPlayAgain = {
                diaLogShow = false
                // Reset game state and start again
                score = 0
                remainingTime = gameTime
                CoroutineScope(Dispatchers.Default).launch {
                    startGame()
                }
            },
            onDismissRequest = {
                // Handle dismiss request
            }
        )
    }




    //GamePlay

    // Add this line to your CatchFish composable
    val caughtFishList: MutableList<Offset> by remember { mutableStateOf(mutableListOf()) }

    // Kích thước của hình chiếc thuyền
    val boatSize = 100.dp

    // Biến để theo dõi xem có con cá nào ở gần chiếc thuyền không
    var fishNearBoat by remember { mutableStateOf(false) }

    // Biến để theo dõi xem người chơi đã nhấn vào chiếc thuyền hay chưa
    var isBoatClicked by remember { mutableStateOf(false) }

    var fishCaught by remember { mutableStateOf(false) }

    // Kiểm tra khi người chơi nhấn vào hình chiếc thuyền
    fun getRandomFishOffset(): Offset {
        val randomX = (0..300).random().toFloat()
        val randomY = (0..300).random().toFloat()
        return Offset(randomX, randomY)
    }
    // Reset the flag when the boat is clicked again
    if (isBoatClicked && fishCaught) {
        fishCaught = false
    }


    // Hàm tính khoảng cách giữa hai điểm
    fun calculateDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val dx = x2 - x1
        val dy = y2 - y1
        return sqrt((dx * dx + dy * dy).toDouble()).toFloat()
    }

//...

    // Hàm kiểm tra xem con cá có ở gần thuyền không
    fun isFishNearBoat(fishOffset: Offset, boatOffset: Offset, boatSize: Dp, proximityThreshold: Dp): Boolean {
        val distance = calculateDistance(fishOffset.x, fishOffset.y, boatOffset.x, boatOffset.y)

        // Check if the fish is near the boat and hasn't been caught yet
        return distance < proximityThreshold.value
    }
    // Kiểm tra khi người chơi nhấn vào hình chiếc thuyền
    if (isBoatClicked) {
        val fishNearBoat1 = isFishNearBoat(fish1Offset, Offset(0f, 0f), boatSize, proximityThreshold = 10000.dp)
        val fishNearBoat2 = isFishNearBoat(fish2Offset, Offset(0f, 0f), boatSize, proximityThreshold = 10000.dp)
        val fishNearBoat3 = isFishNearBoat(fish3Offset, Offset(0f, 0f), boatSize, proximityThreshold = 10000.dp)
        val fishNearBoat4 = isFishNearBoat(fish4Offset, Offset(0f, 0f), boatSize, proximityThreshold = 10000.dp)
        val fishNearBoat5 = isFishNearBoat(fish5Offset, Offset(0f, 0f), boatSize, proximityThreshold = 11000.dp)
        val fishNearBoat6 = isFishNearBoat(fish6Offset, Offset(0f, 0f), boatSize, proximityThreshold = 11500.dp)
        val fishNearBoat7 = isFishNearBoat(fish7Offset, Offset(0f, 0f), boatSize, proximityThreshold = 11500.dp)
        val fishNearBoat8 = isFishNearBoat(fish8Offset, Offset(0f, 0f), boatSize, proximityThreshold = 11500.dp)
        val fishNearBoat9 = isFishNearBoat(fish9Offset, Offset(0f, 0f), boatSize, proximityThreshold = 11500.dp)
        val fishNearBoat10 = isFishNearBoat(fish10Offset, Offset(0f, 0f), boatSize, proximityThreshold = 1150.dp)
        val fishNearBoat11 = isFishNearBoat(fish11Offset, Offset(0f, 0f), boatSize, proximityThreshold = 1150.dp)
        val fishNearBoat12 = isFishNearBoat(fish12Offset, Offset(0f, 0f), boatSize, proximityThreshold = 1150.dp)
        if (fishNearBoat1 || fishNearBoat2 || fishNearBoat3 || fishNearBoat4 || fishNearBoat5 || fishNearBoat6 || fishNearBoat7||fishNearBoat8||fishNearBoat9||fishNearBoat10||fishNearBoat11||fishNearBoat12) {
            if (!fishCaught) {
                fishNearBoat = true

                score += 1000


                fishCaught = true

                // Set new positions for the caught fish
                when {
                    fishNearBoat1 -> fish1Offset = getRandomFishOffset()
                    fishNearBoat2 -> fish2Offset = getRandomFishOffset()
                    fishNearBoat3 -> fish3Offset = getRandomFishOffset()
                    fishNearBoat4 -> fish4Offset = getRandomFishOffset()
                    fishNearBoat5 -> fish5Offset = getRandomFishOffset()
                    fishNearBoat6 -> fish6Offset = getRandomFishOffset()
                    fishNearBoat7 -> fish7Offset = getRandomFishOffset()
                    fishNearBoat8 -> fish1Offset = getRandomFishOffset()
                    fishNearBoat9 -> fish1Offset = getRandomFishOffset()
                    fishNearBoat10 -> fish1Offset = getRandomFishOffset()
                    fishNearBoat11 -> fish1Offset = getRandomFishOffset()
                    fishNearBoat12 -> fish1Offset = getRandomFishOffset()
                }
            }
        } else {

            // No fish is near the boat
            fishNearBoat = false
            fishCaught = false
        }
        isBoatClicked = false
    }

    var isDialogVisible by remember { mutableStateOf(false) }
    var isFishInsideBox1 by remember { mutableStateOf(false) }
    var isFishInsideBox2 by remember { mutableStateOf(false) }
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
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.MailOutline,"")
                    }
                    IconButton(onClick = {
                        isDialogVisible = true
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Settings,"")
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color(204, 255, 255),
                )
            )
            // Kiểm tra nếu Dialog nên hiển thị
            if (isDialogVisible) {
                SearchDialog(
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(236, 135, 14)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Time:$remainingTime",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black),
                    )
                    Text(
                        text = "Score=$score ",
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
                        .size(boatSize)
                        .border(
                            width = 2.dp,
                            color = Color.Blue
                        )
                        .clickable {
                            if (!isBoatClicked) {
                                // Check if fish is inside the box and not caught
                                if (isFishInsideBox1 && !fishCaught) {
                                    fishNearBoat = true
                                    score += 1000
                                    fishCaught = true

                                    // Set new position for the caught fish
                                    fish1Offset = getRandomFishOffset()
                                }
                                isBoatClicked = true
                            }
                        }
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
                        .offset(x = 70.dp, y = 400.dp)
                        .size(boatSize)
                        .border(
                            width = 2.dp,
                            color = Color.Blue
                        )
                        .clickable {
                            if (!isBoatClicked) {
                                // Check if fish is inside the box and not caught
                                if (isFishInsideBox1 && !fishCaught) {
                                    fishNearBoat = true
                                    score += 1000
                                    fishCaught = true

                                    // Set new position for the caught fish
                                    fish1Offset = getRandomFishOffset()
                                }
                                isBoatClicked = true
                            }
                        }
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
                        .size(boatSize)
                        .border(
                            width = 2.dp,
                            color = Color.Blue
                        )
                        .clickable {
                            if (!isBoatClicked) {
                                // Check if fish is inside the box and not caught
                                if (isFishInsideBox1 && !fishCaught) {
                                    fishNearBoat = true
                                    score += 1000
                                    fishCaught = true

                                    // Set new position for the caught fish
                                    fish1Offset = getRandomFishOffset()
                                }
                                isBoatClicked = true
                            }
                        }
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



                //Fish-1
                AnimatedFish(
                    fishModifier = Modifier
                        .offset(x = 100.dp, y = 30.dp)
                        .size(60.dp)
                        .padding(5.dp),
                    initialOffset = fish1Offset,
                    imageResourceId = R.drawable.f_1
                )
                //Fish-2
                AnimatedFish(
                    fishModifier = Modifier
                        .offset(x = 10.dp, y = 400.dp)
                        .size(60.dp)
                        .padding(5.dp),
                    initialOffset = fish2Offset,
                    imageResourceId = R.drawable.f_1
                )
                //Fish-3
                AnimatedFish(
                    fishModifier = Modifier
                        .offset(x = 20.dp, y = 30.dp)
                        .size(60.dp)
                        .padding(5.dp),
                    initialOffset = fish3Offset,
                    imageResourceId = R.drawable.f_2
                )
                //Fish-4
                AnimatedFish(
                    fishModifier = Modifier
                        .offset(x = 20.dp, y = 310.dp)
                        .size(60.dp)
                        .padding(5.dp),
                    initialOffset = fish4Offset,
                    imageResourceId = R.drawable.f_3
                )
                //fish-5
                AnimatedFish(
                    fishModifier = Modifier
                        .offset(x = 100.dp, y = 400.dp)
                        .size(60.dp)
                        .padding(5.dp),
                    initialOffset = fish5Offset,
                    imageResourceId = R.drawable.f_1
                )
                //fish-6
                AnimatedFish(
                    fishModifier = Modifier
                        .offset(x = 200.dp, y = 130.dp)
                        .size(60.dp)
                        .padding(5.dp),
                    initialOffset = fish6Offset,
                    imageResourceId = R.drawable.f_2
                )
                //fish-7
                AnimatedFish(
                    fishModifier = Modifier
                        .offset(x = 200.dp, y = 200.dp)
                        .size(60.dp)
                        .padding(5.dp),
                    initialOffset = fish7Offset,
                    imageResourceId = R.drawable.f_3
                )
                //fish-8
                AnimatedFish(
                    fishModifier = Modifier
                        .offset(x = 200.dp, y = 600.dp)
                        .size(60.dp)
                        .padding(5.dp),
                    initialOffset = fish8Offset,
                    imageResourceId = R.drawable.f_4
                )
                //fish-9
                AnimatedFish(
                    fishModifier = Modifier
                        .offset(x = 200.dp, y = 550.dp)
                        .size(60.dp)
                        .padding(5.dp),
                    initialOffset = fish9Offset,
                    imageResourceId = R.drawable.f_5
                )
                //fish-10
                AnimatedFish(
                    fishModifier = Modifier
                        .offset(x = 10.dp, y = 600.dp)
                        .size(60.dp)
                        .padding(5.dp),
                    initialOffset = fish10Offset,
                    imageResourceId = R.drawable.f_5
                )
                //fish-11
                AnimatedFish(
                    fishModifier = Modifier
                        .offset(x = 220.dp, y = 550.dp)
                        .size(60.dp)
                        .padding(5.dp),
                    initialOffset = fish11Offset,
                    imageResourceId = R.drawable.f_4
                )
            }
        }
    }
}

@Composable
fun GameOverDialog(score: Int, onPlayAgain: () -> Unit, onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xffffcc99), RoundedCornerShape(16.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Time's up!",
                    style = TextStyle(fontSize = 24.sp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Your Score: $score", // Display the score
                    style = TextStyle(fontSize = 20.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Button(
                    onClick = {
                        onPlayAgain()
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Play Again")
                }
            }
        }
    }
}

@Preview
@Composable
fun reviewCatchFish(){
    CatchFish(navController = rememberNavController())
}