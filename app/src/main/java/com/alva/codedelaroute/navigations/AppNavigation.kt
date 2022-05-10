package com.alva.codedelaroute.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alva.codedelaroute.screens.ChildTopicListScreen
import com.alva.codedelaroute.screens.HomeScreen
import com.alva.codedelaroute.screens.question_screen.QuestionScreen

@Composable
fun AppNavigation() {
    var navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HomeScreen.name) {
        composable(
            Routes.HomeScreen.name
        ) {
            HomeScreen(navController)
        }
        composable(Routes.ChildTopicListScreen.name + "/{parentId}", arguments = listOf(navArgument("parentId") {
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("parentId")?.let {
                ChildTopicListScreen(navController, parentId = it)
            }
        }
        composable(Routes.QuestionScreen.name + "/{subTopicId}", arguments = listOf(navArgument("subTopicId") {
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("subTopicId")?.let {
                QuestionScreen(navController, subTopicId = it)
            }
        }
    }
}