package com.alva.codedelaroute.screens.home_screen.tabs.review_tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alva.codedelaroute.R
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.home_screen.tabs.review_tab.widgets.ReviewCard
import com.alva.codedelaroute.utils.ReviewQuestionProperty
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.alva.codedelaroute.widgets.CustomToast

@Composable
fun ReviewTab(
    navController: NavController, questionViewModel: QuestionViewModel = viewModel()
) {
    val weakQuestionsCount = questionViewModel.weakQuestionsCount.collectAsState()
    val mediumQuestionsCount = questionViewModel.mediumQuestionsCount.collectAsState()
    val strongQuestionsCount = questionViewModel.strongQuestionsCount.collectAsState()
    val allFamiliarQuestionsCount = questionViewModel.allAnsweredQuestionsCount.collectAsState()
    val favoriteQuestionsCount = questionViewModel.favoriteQuestionsCount.collectAsState()

    val context = LocalContext.current

    LazyColumn {
        item {
            ReviewCard(
                "Weak Questions", ReviewQuestionProperty.WeakQuestions, modifier = Modifier.clickable {
                    if (weakQuestionsCount.value != 0) {
                        navController.navigate(Routes.ReviewListScreen.name + "/${ReviewQuestionProperty.WeakQuestions}")
                    } else {
                        CustomToast.showToast(
                            context = context,
                            message = "No question available",
                            icon = R.drawable.error_circle,
                            textColor = R.color.toast_error_text_color,
                            toastBackgroundColor = R.color.toast_error_background_color
                        )
                    }
                }, questionCount = weakQuestionsCount.value
            )
            ReviewCard(
                "Medium Questions", ReviewQuestionProperty.MediumQuestions, modifier = Modifier.clickable {
                    if (mediumQuestionsCount.value != 0) {
                        navController.navigate(Routes.ReviewListScreen.name + "/${ReviewQuestionProperty.MediumQuestions}")
                    } else {
                        CustomToast.showToast(
                            context = context,
                            message = "No question available",
                            icon = R.drawable.error_circle,
                            textColor = R.color.toast_error_text_color,
                            toastBackgroundColor = R.color.toast_error_background_color
                        )
                    }
                }, questionCount = mediumQuestionsCount.value
            )
            ReviewCard(
                "Strong Questions", ReviewQuestionProperty.StrongQuestions, modifier = Modifier.clickable {
                    if (strongQuestionsCount.value != 0) {
                        navController.navigate(Routes.ReviewListScreen.name + "/${ReviewQuestionProperty.StrongQuestions}")
                    } else {
                        CustomToast.showToast(
                            context = context,
                            message = "No question available",
                            icon = R.drawable.error_circle,
                            textColor = R.color.toast_error_text_color,
                            toastBackgroundColor = R.color.toast_error_background_color
                        )
                    }
                }, questionCount = strongQuestionsCount.value
            )
            ReviewCard(
                "All Familiar Questions", ReviewQuestionProperty.AllFamiliarQuestions, modifier = Modifier.clickable {
                    if (allFamiliarQuestionsCount.value != 0) {
                        navController.navigate(Routes.ReviewListScreen.name + "/${ReviewQuestionProperty.AllFamiliarQuestions}")
                    } else {
                        CustomToast.showToast(
                            context = context,
                            message = "No question available",
                            icon = R.drawable.error_circle,
                            textColor = R.color.toast_error_text_color,
                            toastBackgroundColor = R.color.toast_error_background_color
                        )
                    }
                }, questionCount = allFamiliarQuestionsCount.value
            )
            ReviewCard(
                "Your Favorite Questions", ReviewQuestionProperty.YourFavoriteQuestions, modifier = Modifier.clickable {
                    if (favoriteQuestionsCount.value != 0) {
                        navController.navigate(Routes.ReviewListScreen.name + "/${ReviewQuestionProperty.YourFavoriteQuestions}")
                    } else {
                        CustomToast.showToast(
                            context = context,
                            message = "No question available",
                            icon = R.drawable.error_circle,
                            textColor = R.color.toast_error_text_color,
                            toastBackgroundColor = R.color.toast_error_background_color
                        )
                    }
                }, questionCount = favoriteQuestionsCount.value
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}