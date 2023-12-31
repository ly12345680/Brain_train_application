//package com.example.braintrainapp
//
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Shapes
//import androidx.compose.runtime.Composable
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import androidx.compose.material3.Typography
//@Composable
//fun Navigation() {
//    val navController = rememberNavController()
//    MaterialTheme(
//        typography = Typography(),
//        shapes = Shapes()
//    ) {
//        NavHost(navController = navController, startDestination = Screen.MainMenu.route) {
//            composable(Screen.MainMenu.route) {
//                MainMenu(navController = navController)
//            }
//            composable(Screen.MemoryGame.route) {
//                // MemoryGameScreen()
//            }
//            composable(Screen.AttentionGame.route) {
//                // AttentionGameScreen()
//            }
//            composable(Screen.MathGame.route) {
//                // MathGameScreen()
//            }
//        }
//    }
//}