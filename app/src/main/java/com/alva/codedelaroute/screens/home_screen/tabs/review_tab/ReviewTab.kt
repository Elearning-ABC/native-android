package com.alva.codedelaroute.screens.home_screen.tabs.review_tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.home_screen.tabs.review_tab.widgets.ReviewCard
import com.alva.codedelaroute.screens.home_screen.widgets.TopicCard
import com.alva.codedelaroute.utils.ReviewQuestionProperty
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.alva.codedelaroute.view_models.TopicViewModel

@Composable
fun ReviewTab(
    navController: NavController = rememberNavController(), questionViewModel: QuestionViewModel = viewModel(
        viewModelStoreOwner = LocalViewModelStoreOwner.current!!
    )
) {
    LazyColumn {
        item {
            ReviewCard(
                "Weak Questions",
                ReviewQuestionProperty.WeakQuestions,
                modifier = Modifier.clickable {
                    navController.navigate(Routes.ReviewListScreen.name + "/${ReviewQuestionProperty.WeakQuestions}")
                },
                questionCount = questionViewModel.getQuestionListCountByReviewProperty(ReviewQuestionProperty.WeakQuestions)
            )
            ReviewCard(
                "Medium Questions",
                ReviewQuestionProperty.MediumQuestions,
                modifier = Modifier.clickable {
                    navController.navigate(Routes.ReviewListScreen.name + "/${ReviewQuestionProperty.MediumQuestions}")
                },
                questionCount = questionViewModel.getQuestionListCountByReviewProperty(ReviewQuestionProperty.MediumQuestions)
            )
            ReviewCard(
                "Strong Questions",
                ReviewQuestionProperty.StrongQuestions,
                modifier = Modifier.clickable {
                    navController.navigate(Routes.ReviewListScreen.name + "/${ReviewQuestionProperty.StrongQuestions}")
                },
                questionCount = questionViewModel.getQuestionListCountByReviewProperty(ReviewQuestionProperty.StrongQuestions)
            )
            ReviewCard(
                "All Familiar Questions",
                ReviewQuestionProperty.AllFamiliarQuestions,
                modifier = Modifier.clickable {
                    navController.navigate(Routes.ReviewListScreen.name + "/${ReviewQuestionProperty.AllFamiliarQuestions}")
                },
                questionCount = questionViewModel.getAllAnsweredQuestionCount()
            )
            ReviewCard(
                "Your Favorite Questions",
                ReviewQuestionProperty.YourFavoriteQuestions,
                modifier = Modifier.clickable {
                    navController.navigate(Routes.ReviewListScreen.name + "/${ReviewQuestionProperty.YourFavoriteQuestions}")
                },
                questionCount = questionViewModel.getFavoriteQuestionCount()
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}