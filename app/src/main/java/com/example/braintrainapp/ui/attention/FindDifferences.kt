package com.example.braintrainapp.ui.attention

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.braintrainapp.R
import com.example.braintrainapp.ui.memory.ScoreAndLevel
import kotlinx.coroutines.delay

class FindDifferencesState {
    var isWinner by mutableStateOf(false)
        private set
    fun checkWin(isBoxPressed: Boolean) {
        if (isBoxPressed) {
            // Check your winning condition here
            // For example, if the box is pressed, set isWinner to true
            isWinner = true
            println("Winning condition met!")
        }
    }
    var currentImage by mutableStateOf(R.drawable.df_1)
        private set

    var boxPosition by mutableStateOf(Pair(250.dp, 145.dp))
        private set

    fun changeImageAndBoxPosition() {
        // Change the image resource ID here
        currentImage = R.drawable.df_2
        // Change the position of the box here
        boxPosition = Pair(290.dp, 310.dp)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinDifferences(){
    val state = remember { FindDifferencesState() }
    val dialogShown = remember { mutableStateOf(false) }
    var level = remember { mutableStateOf(1) }
    var timeLeft by remember { mutableStateOf(5)}
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Find Differences")},
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                    .background(Color(136,135,112))

            ){
                var isBoxPressed by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .absoluteOffset(y = state.boxPosition.second, x = state.boxPosition.first)
                        .zIndex(2f)
                        .clickable {
                            isBoxPressed = !isBoxPressed
                            state.checkWin(isBoxPressed)
                            if (state.isWinner) {
                                state.changeImageAndBoxPosition()
                                isBoxPressed = false
                                dialogShown.value = true
                            }
                        }
                        .border(
                            width = 2.dp,
                            color = if (isBoxPressed) Color.Blue else Color.Transparent
                        )
                )
                Image(
                    painter = painterResource(id = state.currentImage),
                    contentDescription = "DF_1",
                    modifier = Modifier
                        .size(500.dp)
                        .padding(20.dp)
                        .zIndex(1f)
                )
                LaunchedEffect(state.isWinner) {
                    if (state.isWinner) {
                        delay(500) // Delay for a short time to allow the UI to update
                        state.changeImageAndBoxPosition()
                    }
                }
            }
            if (state.isWinner && dialogShown.value) {
                WinDialog {
                    dialogShown.value = false
                }
            }



            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(236, 135, 14)),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "Time left: $timeLeft",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    )
                }
            }

        }
    }
}

@Composable
fun WinDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Win!") },
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text("OK")
            }
        }
    )
}
@Preview
@Composable
fun review(){
    FinDifferences()
}