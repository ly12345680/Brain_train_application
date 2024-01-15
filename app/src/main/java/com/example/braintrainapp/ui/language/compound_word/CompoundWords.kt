package com.example.braintrainapp.ui.language_game.compound_word

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.braintrainapp.ui.language_game.unscramble_words.SearchDialog6
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
@Composable
fun SearchDialog7(onDismiss: () -> Unit, onSearch: () -> Unit) {
//    var playerName by remember { mutableStateOf(TextFieldValue()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Game Instructions")
        },
        text = {
            Column {
                Text("Step 1: The game has a duration of 2 minutes.")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Step 2: The task is to find words that can be combined with the given initial word into a meaningful compound word.")
                Spacer(modifier = Modifier.height(8.dp))
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
data class CompoundWordList(val baseWord: String, val meaning: String)

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompoundNounGame(navController: NavController) {
    val gameDuration = 90 // 90  seconds

    //var currentCompoundWordIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var userInput by remember { mutableStateOf("") }
    var wordsFound by remember { mutableStateOf(0) }
    var timerJob by remember { mutableStateOf<Job?>(null) }
    var remainingTime by remember { mutableStateOf(gameDuration) }
    var dialogShown by remember { mutableStateOf(false) }
    val currentCompoundWordIndex = remember { mutableStateOf(Random.nextInt(compoundWords.size) + 10 ) }
    var currentCompoundWord = compoundWords[currentCompoundWordIndex.value]
    var isDialogVisible by remember { mutableStateOf(false) }
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Compound Noun") },
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
                    IconButton(onClick = {
                        isDialogVisible = true
                    }) {
                        Icon(Icons.Filled.Info, contentDescription = "Search")
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color(229, 237, 155, 255),
                )
            )
            // Kiểm tra nếu Dialog nên hiển thị
            if (isDialogVisible) {
                SearchDialog7(
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
    ) { paddingvalue ->
        Column(
            modifier = Modifier.padding(vertical = 100.dp, horizontal = 16.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xffffcc99), RoundedCornerShape(16.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Complete the compound noun:",
                        style = TextStyle(fontSize = 24.sp),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Color(0xFFF06292)),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = "${currentCompoundWord.baseWord}________",
                        style = TextStyle(fontSize = 20.sp),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                }

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Color(0xFFF06292)),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = "Score: $score",
                        style = TextStyle(fontSize = 20.sp),
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Color(0xFFF06292)),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = "Time: $remainingTime seconds",
                        style = TextStyle(fontSize = 16.sp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFE6EE9C), RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(
                            value = userInput,
                            onValueChange = { userInput = it },
                            label = { Text(text = "Enter your answer") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                //userInput = userInput.toLowerCase()
                                if (userInput.isNotBlank() &&
                                    CompoundWordList(
                                        currentCompoundWord.baseWord,
                                        userInput
                                    ) in compoundWords
                                ) {
                                    score += 100
                                    wordsFound++
                                    //currentCompoundWordIndex++
                                    userInput = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color.DarkGray),
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text(text = "Submit")
                        }
                    }
                }
            }
        }
    }

    fun endGame() {
        timerJob?.cancel()
        dialogShown = true
    }
    fun startGame() {
        timerJob = CoroutineScope(Dispatchers.Default).launch {
            while (remainingTime > 0) {
                delay(1000)
                remainingTime--
            }
            endGame()
        }
    }

    if (timerJob == null) {
        val coroutineScope = rememberCoroutineScope()
        timerJob = coroutineScope.launch {
            startGame()
        }
    }

    fun startNewGame() {
        score = 0
        wordsFound = 0
        remainingTime = gameDuration
        currentCompoundWord = compoundWords[currentCompoundWordIndex.value]
        userInput = ""
        dialogShown = false
        timerJob = null
        startGame()
    }

    if (dialogShown) {
        Dialog(
            onDismissRequest = { dialogShown = false },
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
                        text = "Score: $score",
                        style = TextStyle(fontSize = 20.sp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Word Found: $wordsFound",
                        style = TextStyle(fontSize = 20.sp),
                        textAlign = TextAlign.Center
                    )
                    Button (
                        onClick = { startNewGame() },
                        colors = ButtonDefaults.buttonColors(Color.DarkGray),
                        modifier = Modifier.padding(top = 16.dp)
                    ){
                        Text(text = "Play Again")
                    }

                }
            }
        }

    }
}

@Preview
@Composable
fun PreviewCompoundNounGame() {
    CompoundNounGame(navController = rememberNavController())
}