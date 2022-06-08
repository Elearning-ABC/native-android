@file:OptIn(ExperimentalAnimationApi::class)

package com.alva.codedelaroute.screens.question_screen.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.alva.codedelaroute.models.Answer
import com.alva.codedelaroute.models.Question
import com.alva.codedelaroute.models.QuestionProgress
import com.alva.codedelaroute.models.TopicProgress
import com.alva.codedelaroute.utils.AnswerStatus
import com.alva.codedelaroute.view_models.AnswerViewModel
import com.alva.codedelaroute.view_models.QuestionViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun AnswerPanel(
    question: Question,
    questionViewModel: QuestionViewModel,
    questionProgress: QuestionProgress,
    enabled: MutableState<Boolean>,
    answerViewModel: AnswerViewModel,
    coroutine: CoroutineScope,
    answerStatus: MutableState<AnswerStatus>,
    checkFinishedQuestion: MutableState<Boolean>,
    subTopicProgress: TopicProgress? = null,
    mainTopicProgress: TopicProgress? = null,
    explanation: String,
    isReviewScreen: Boolean = false
) {
    LazyColumn {

        items(items = question.choices, key = { item: Answer -> item.id }) {

            val visibilityState = if (!enabled.value) {
                it.isCorrect || questionProgress.choiceSelectedIds.contains(it.id)
            } else true

            val panelColorState = remember { mutableStateOf(Color.White) }

            val borderAnswerColorState = if (enabled.value) {
                Color.Transparent
            } else {
                if (it.isCorrect) Color(0xFF00C17C)
                else Color(227, 30, 24).copy(alpha = 0.52f)
            }

            val iconState = if (enabled.value) {
                mapOf(
                    "icon" to Icons.Default.Remove, "tint" to Color(0xFF4D4D4D)
                )
            } else {
                if (it.isCorrect) mapOf(
                    "icon" to Icons.Default.Check, "tint" to Color(0xFF00C17C)

                )
                else mapOf(
                    "icon" to Icons.Default.Close, "tint" to Color(227, 30, 24).copy(alpha = 0.52f)
                )

            }


            AnimatedVisibility(
                visible = visibilityState, enter = scaleIn()
            ) {
                AnswerItem(
                    it,
                    borderAnswerColorState = borderAnswerColorState,
                    panelColorState = panelColorState,
                    enabled = enabled,
                    iconState = iconState,
                    explanation = explanation
                ) {
                    answerViewModel.onAnswerClickHandler(
                        answer = it,
                        question = question,
                        questionProgress = questionProgress,
                        questionViewModel = questionViewModel,
                        panelColorState = panelColorState,
                        enabled = enabled,
                        coroutine = coroutine,
                        answerStatus = answerStatus,
                        checkFinishedQuestion = checkFinishedQuestion,
                        subTopicProgress = subTopicProgress,
                        mainTopicProgress = mainTopicProgress,
                        isReviewScreen = isReviewScreen
                    )
                }
            }
        }
    }
}