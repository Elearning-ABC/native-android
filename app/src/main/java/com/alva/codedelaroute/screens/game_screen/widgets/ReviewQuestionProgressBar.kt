package com.alva.codedelaroute.screens.game_screen.widgets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alva.codedelaroute.models.UIQuestionProgress
import com.alva.codedelaroute.view_models.QuestionViewModel

@Composable
fun ReviewQuestionProgressBar(
    questionViewModel: QuestionViewModel = viewModel(),
    questionProgressList: MutableList<UIQuestionProgress>,
    checkFinishedQuestion: MutableState<Boolean>
) {
    val trueIndicatorProgress = remember {
        Animatable(
            questionViewModel.getTrueQuestionsPercentByQuestionProgressList(
                questionProgressList
            )
        )
    }
    val falseIndicatorProgress = remember {
        Animatable(
            questionViewModel.getTrueQuestionsPercentByQuestionProgressList(
                questionProgressList
            ) + questionViewModel.getFalseQuestionsPercentByQuestionProgressList(
                questionProgressList
            )
        )
    }

    Surface(color = Color.Transparent, modifier = Modifier.fillMaxWidth()) {
        LinearProgressIndicator(
            color = Color(0xFFEBAD34), backgroundColor = Color.LightGray, progress = falseIndicatorProgress.value
        )
        LinearProgressIndicator(
            color = Color(0xFF00E291),
            backgroundColor = Color.Transparent,
            progress = trueIndicatorProgress.value,
        )
        if (checkFinishedQuestion.value) {
            LaunchedEffect(checkFinishedQuestion.value) {
                trueIndicatorProgress.animateTo(
                    questionViewModel.getTrueQuestionsPercentByQuestionProgressList(
                        questionProgressList
                    ), animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                )
            }
            LaunchedEffect(checkFinishedQuestion.value) {
                falseIndicatorProgress.animateTo(
                    questionViewModel.getTrueQuestionsPercentByQuestionProgressList(
                        questionProgressList
                    ) + questionViewModel.getFalseQuestionsPercentByQuestionProgressList(
                        questionProgressList
                    ), animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                )
            }
        }
    }
}