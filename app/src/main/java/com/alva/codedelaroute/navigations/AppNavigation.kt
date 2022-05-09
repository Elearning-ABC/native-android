package com.alva.codedelaroute.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alva.codedelaroute.screens.ChildTopicListScreen
import com.alva.codedelaroute.screens.HomeScreen
import com.alva.codedelaroute.screens.question_screen.QuestionScreen

@Composable
fun AppNavigation() {
    var navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HomeScreen.name) {
        composable(Routes.HomeScreen.name) {
            HomeScreen(navController)
        }
        composable(Routes.ChildTopicListScreen.name) {
            ChildTopicListScreen(navController)
        }
        composable(Routes.QuestionScreen.name) {
            QuestionScreen(navController)
        }
    }
}