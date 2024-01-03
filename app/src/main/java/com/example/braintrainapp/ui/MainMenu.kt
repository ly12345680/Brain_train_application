package com.example.braintrainapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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


/**
 * Composable that allows the user to select the desired cupcake quantity and expects
 * [onNextButtonClicked] lambda that expects the selected quantity and triggers the navigation to
 * next screen
 */
@Composable
fun MainMenu(navController: NavController) {
    val myColor = colorResource(id = R.color.sari)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(myColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val robotoFontFamily = Font(R.font.simonetta_regular, FontWeight.Bold)
        Text(
            text = "Brain Train Games",
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



        Button(onClick = {
            navController.navigate(Screen.MemoryGame.route)
        }) {
            Text(text = "Memory Game")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate(Screen.AttentionGame.route)
        }) {
            Text(text = "Attention Game")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate(Screen.LanguageGame.route)
        }) {
            Text(text = "Language Game")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate(Screen.MathGame.route)
        }) {
            Text(text = "Math Game")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuPreview() {
    val navController = rememberNavController()
    MainMenu(navController = navController)
}


