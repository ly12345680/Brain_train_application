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
fun AttentionGame(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val robotoFontFamily = Font(R.font.simonetta_regular, FontWeight.Bold)
        Text(
            text = "Attention Game",
            style = TextStyle(
                fontSize = 30.sp,
                color = Color.Black
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Button(onClick = { /* TODO: Implement your logic */ }) {
            Text(text = "Find Differences")
        }
        Button(onClick = { /* TODO: Implement your logic */ }) {
            Text(text = "Game mode 2")
        }
        Button(onClick = { navController.navigate(Screen.AttentionGame.route) }) {
            Text(text = "Catch Fish")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AttentionGamePreview() {
    val navController = rememberNavController()
    AttentionGame(navController = navController)
}