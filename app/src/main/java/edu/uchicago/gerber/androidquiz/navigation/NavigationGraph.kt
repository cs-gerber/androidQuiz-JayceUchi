package edu.uchicago.gerber.androidquiz.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.uchicago.gerber.androidquiz.viewmodel.QuizViewModel
import edu.uchicago.gerber.androidquiz.screens.HomeScreen
import edu.uchicago.gerber.androidquiz.screens.QuestionScreen
import edu.uchicago.gerber.androidquiz.screens.ResultScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: QuizViewModel = QuizViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController, viewModel)
        }
        composable(route = Screen.Question.route) {
            QuestionScreen(navController, viewModel)
        }
        composable(route = Screen.Result.route) {
            ResultScreen(navController, viewModel)
        }
    }

}
