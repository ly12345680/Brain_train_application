package com.example.braintrainapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.braintrainapp.R
import com.example.braintrainapp.Screen

@Composable
fun MemoryGame(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val robotoFontFamily = Font(R.font.simonetta_regular, FontWeight.Bold)
        Text(
            text = "Memory Game",
            style = TextStyle(

                fontSize = 30.sp, // Increase the font size to make it bigger
                color = Color.Black // Change the color to make it more highlighted
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Button(onClick = { navController.navigate(Screen.ColorMemory.route) }) {
            Text(text = "Color Memorization")
        }
        Button(onClick = {  navController.navigate(Screen.FindNewImage.route)}) {
            Text(text = "Finding a New Image")
        }
        Button(onClick = {}) {
            Text(text = "What Is That Image")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MemoryGamePreview() {
    val navController = rememberNavController()
    MemoryGame(navController = navController)
}