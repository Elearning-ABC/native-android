package com.alva.codedelaroute.screens.question_screen.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.alva.codedelaroute.R
import com.alva.codedelaroute.models.Question
import com.alva.codedelaroute.models.QuestionProgress
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.view_models.QuestionViewModel

@Composable
fun QuestionBottomBar(
    navController: NavController,
    questionViewModel: QuestionViewModel = viewModel(
        viewModelStoreOwner = QuestionViewModel.viewModelStoreOwner,
        key = QuestionViewModel.key
    ),
    question: Question,
    questionProgress: QuestionProgress,
    subTopicId: String,
    questionList: MutableList<Question>
) {
    NavigationBar(contentColor = Color.Black, containerColor = Color.White) {
        NavigationBarItem(onClick = {}, selected = false, icon = {
            Icon(
                painterResource(R.drawable.flag_icon), contentDescription = null, modifier = Modifier.size(24.dp)
            )
        })
        NavigationBarItem(onClick = {}, selected = false, icon = {
            Icon(
                painterResource(R.drawable.favorite_border_rounded_icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        })
        NavigationBarItem(onClick = {
            if (questionViewModel.isFinishQuestion(question, questionProgress)) {
                if (questionViewModel.checkFinishedTopic(questionList, subTopicId.toLong())) {
                    navController.popBackStack()
                } else {
                    navController.popBackStack()
                    navController.navigate(Routes.QuestionScreen.name + "/$subTopicId")
                }
            }
        }, selected = false, icon = {
            Icon(
                painterResource(R.drawable.arrow_forward_icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        })
    }
}