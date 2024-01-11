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
import com.example.braintrainapp.ui.attention.CatchFish
import com.example.braintrainapp.ui.attention.FinDifferences
import com.example.braintrainapp.ui.math.FindSum
import com.example.braintrainapp.ui.math.SmallerExpressionGame
import com.example.braintrainapp.ui.memory.ColorMemory
import com.example.braintrainapp.ui.memory.FindNewImage
import com.example.braintrainapp.ui.memory.PairImage


sealed class Screen(val route: String) {
    object MainMenu : Screen("mainMenu")
    object MemoryGame : Screen("memoryGame")
    object AttentionGame : Screen("attentionGame")
    object LanguageGame : Screen("languageGame")
    object MathGame : Screen("mathGame")
    object PairImage : Screen("pairImage")
    object ColorMemory : Screen("colorMemory")
    object FindNewImage : Screen("findNewImage")
    object FindDifferences : Screen("findDifferences")
    object FindSum : Screen("findSum")
    object SmallerExpression : Screen("smallerExpression")
    object CatchFish : Screen("catchFish")

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
                AttentionGame(navController = navController)
            }
            composable(Screen.LanguageGame.route) {
                LanguageGame(navController = navController)
            }
            composable(Screen.MathGame.route) {
                MathGame(navController = navController)
            }
            composable(Screen.ColorMemory.route) {
                ColorMemory()
            }
            composable(Screen.FindNewImage.route) {
                FindNewImage()
            }
            composable(Screen.PairImage.route) {
                PairImage()
            }
            composable(Screen.FindDifferences.route) {
                FinDifferences()
            }
            composable(Screen.FindSum.route) {
                FindSum()
            }
            composable(Screen.SmallerExpression.route) {
                SmallerExpressionGame()
            }
            composable(Screen.CatchFish.route) {
                CatchFish()
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




