package com.example.braintrainapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.braintrainapp.ui.AttentionGame
import com.example.braintrainapp.ui.LanguageGame
import com.example.braintrainapp.ui.MainMenu
import com.example.braintrainapp.ui.MathGame
import com.example.braintrainapp.ui.MemoryGame
import com.example.braintrainapp.ui.memory.Image
import com.example.braintrainapp.ui.memory.MemorySweepGame

sealed class Screen(val route: String) {
    object MainMenu : Screen("mainMenu")
    object MemoryGame : Screen("memoryGame")
    object AttentionGame : Screen("attentionGame")
    object LanguageGame : Screen("languageGame")
    object MathGame : Screen("mathGame")
    object Image : Screen("image")
    object MemorySweep : Screen("memorySweep")
}
@Composable
fun MyApp() {
    val navController = rememberNavController()
    MaterialTheme(
        typography = Typography(),
        shapes = Shapes()
    ) {
        NavHost(navController = navController, startDestination = Screen.MainMenu.route) {
            composable(Screen.MainMenu.route) {
                MainMenu(navController = navController)
            }
            composable(Screen.MemoryGame.route) {
                MemoryGame(navController = navController)
            }
            composable(Screen.AttentionGame.route) {
                AttentionGame()
            }
            composable(Screen.LanguageGame.route) {
                LanguageGame()
            }
            composable(Screen.MathGame.route) {
                MathGame()
            }
            composable(Screen.MemorySweep.route) {
                MemorySweepGame()
            }


        }
    }
}
@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}




