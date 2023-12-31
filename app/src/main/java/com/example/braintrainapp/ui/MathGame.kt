package com.example.braintrainapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.braintrainapp.R

@Composable
fun MathGame(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val robotoFontFamily = Font(R.font.simonetta_regular, FontWeight.Bold)
        Text(
            text = "Math Game",
            style = TextStyle(

                fontSize = 30.sp, // Increase the font size to make it bigger
                color = Color.Black // Change the color to make it more highlighted
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.resim),
            contentDescription = "App Logo"
        )
    }
}
@Preview(showBackground = true)
@Composable
fun MathGamePreview() {
    MathGame()
}