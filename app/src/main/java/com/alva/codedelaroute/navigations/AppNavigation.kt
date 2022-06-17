package com.alva.codedelaroute.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alva.codedelaroute.screens.child_topic_list_screen.ChildTopicListScreen
import com.alva.codedelaroute.screens.home_screen.HomeScreen
import com.alva.codedelaroute.screens.finish_topic_screen.FinishTopicScreen
import com.alva.codedelaroute.screens.onboarding_screen.OnboardingScreen
import com.alva.codedelaroute.screens.question_screen.QuestionScreen
import com.alva.codedelaroute.screens.question_screen.ReviewQuestionScreen
import com.alva.codedelaroute.screens.review_list_screen.ReviewListScreen
import com.alva.codedelaroute.screens.splash_screen.SplashScreen
import com.alva.codedelaroute.utils.ReviewQuestionProperty

@ExperimentalComposeUiApi
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.SplashScreen.name) {
        composable(Routes.SplashScreen.name) {
            SplashScreen(navController)
        }
        composable(Routes.OnBoardingScreen.name) {
            OnboardingScreen(navController)
        }
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
        composable(
            Routes.QuestionScreen.name + "/{subTopicId}",
            arguments = listOf(navArgument("subTopicId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("subTopicId")?.let {
                QuestionScreen(
                    navController,
                    subTopicId = it
                )
            }
        }
        composable(
            Routes.ReviewQuestionScreen.name + "/{reviewQuestionProperty}",
            arguments = listOf(navArgument("reviewQuestionProperty") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("reviewQuestionProperty")?.let {
                ReviewQuestionScreen(navController, ReviewQuestionProperty.valueOf(it))
            }
        }
        composable(Routes.FinishTopicScreen.name + "/{subTopicId}", arguments = listOf(navArgument("subTopicId") {
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("subTopicId")?.let {
                FinishTopicScreen(navController, subTopicId = it)
            }
        }
        composable(
            Routes.ReviewListScreen.name + "/{reviewQuestionProperty}",
            arguments = listOf(navArgument("reviewQuestionProperty") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("reviewQuestionProperty")?.let {
                ReviewListScreen(navController, ReviewQuestionProperty.valueOf(it))
            }
        }
    }
}