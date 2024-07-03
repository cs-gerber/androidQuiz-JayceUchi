package edu.uchicago.gerber.androidquiz.navigation

sealed class Screen(val route: String) {
    object Home : Screen(route = "home_screen")
    object Question : Screen(route = "question_screen")
    object Result : Screen(route = "result_screen")
}