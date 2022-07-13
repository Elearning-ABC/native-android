package com.alva.codedelaroute.navigations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alva.codedelaroute.screens.topic_screen.TopicScreen
import com.alva.codedelaroute.screens.home_screen.HomeScreen
import com.alva.codedelaroute.screens.topic_passed_screen.TopicPassedScreen
import com.alva.codedelaroute.screens.onboarding_screen.OnboardingScreen
import com.alva.codedelaroute.screens.game_screen.PracticeGameScreen
import com.alva.codedelaroute.screens.game_screen.ReviewGameScreen
import com.alva.codedelaroute.screens.game_screen.TestGameScreen
import com.alva.codedelaroute.screens.review_list_screen.ReviewListScreen
import com.alva.codedelaroute.screens.splash_screen.SplashScreen
import com.alva.codedelaroute.screens.test_done_screen.TestDoneScreen
import com.alva.codedelaroute.screens.test_option_screen.TestOptionScreen
import com.alva.codedelaroute.utils.ReviewQuestionProperty

@ExperimentalComposeUiApi
@Composable
fun AppNavigation() {
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.SplashScreen.name) {
        composable(Routes.SplashScreen.name) {
            CompositionLocalProvider(
                LocalViewModelStoreOwner provides viewModelStoreOwner
            ) {
                SplashScreen(navController)
            }
        }
        composable(Routes.OnBoardingScreen.name) {
            CompositionLocalProvider(
                LocalViewModelStoreOwner provides viewModelStoreOwner
            ) {
                OnboardingScreen(navController)
            }
        }
        composable(
            Routes.HomeScreen.name
        ) {
            CompositionLocalProvider(
                LocalViewModelStoreOwner provides viewModelStoreOwner
            ) {
                HomeScreen(navController)
            }
        }
        composable(Routes.TopicScreen.name + "/{parentId}", arguments = listOf(navArgument("parentId") {
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("parentId")?.let {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    TopicScreen(navController, parentId = it)
                }
            }
        }
        composable(
            Routes.PracticeGameScreen.name + "/{subTopicId}", arguments = listOf(navArgument("subTopicId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("subTopicId")?.let {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    PracticeGameScreen(
                        navController, subTopicId = it
                    )
                }
            }
        }
        composable(
            Routes.ReviewGameScreen.name + "/{reviewQuestionProperty}",
            arguments = listOf(navArgument("reviewQuestionProperty") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("reviewQuestionProperty")?.let {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    ReviewGameScreen(
                        navController,
                        ReviewQuestionProperty.valueOf(it),
                    )
                }

            }
        }
        composable(Routes.TopicPassedScreen.name + "/{subTopicId}", arguments = listOf(navArgument("subTopicId") {
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("subTopicId")?.let {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    TopicPassedScreen(navController, subTopicId = it)
                }
            }
        }
        composable(
            Routes.ReviewListScreen.name + "/{reviewQuestionProperty}",
            arguments = listOf(navArgument("reviewQuestionProperty") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("reviewQuestionProperty")?.let {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    ReviewListScreen(navController, ReviewQuestionProperty.valueOf(it))
                }
            }
        }
        composable(
            Routes.TestOptionScreen.name + "/{testInfoId}", arguments = listOf(navArgument("testInfoId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("testInfoId")?.let {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    TestOptionScreen(navController, it)
                }
            }
        }
        composable(
            Routes.TestGameScreen.name + "/{testInfoId}/{testProgressId}",
            arguments = listOf(navArgument("testInfoId") {
                type = NavType.StringType
            }, navArgument("testProgressId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            CompositionLocalProvider(
                LocalViewModelStoreOwner provides viewModelStoreOwner
            ) {
                TestGameScreen(
                    navController,
                    testInfoId = backStackEntry.arguments?.getString("testInfoId")!!,
                    testProgressId = backStackEntry.arguments?.getString("testProgressId")!!
                )
            }
        }
        composable(Routes.TestDoneScreen.name + "/{testProgressId}", arguments = listOf(navArgument("testProgressId") {
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("testProgressId")?.let {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    TestDoneScreen(navController, it)
                }
            }
        }
    }
}