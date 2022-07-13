@file:OptIn(ExperimentalAnimationApi::class)

package com.alva.codedelaroute.screens.game_screen.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.alva.codedelaroute.models.UIQuestion
import com.alva.codedelaroute.models.UIQuestionProgress
import com.alva.codedelaroute.models.UITestProgress
import com.alva.codedelaroute.models.UITopicProgress
import com.alva.codedelaroute.utils.AnswerStatus
import com.alva.codedelaroute.utils.GameType
import com.alva.codedelaroute.utils.TestSetting
import com.alva.codedelaroute.view_models.GameViewModel
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.alva.codedelaroute.view_models.TestInfoViewModel

import kotlinx.coroutines.runBlocking

@Composable
fun AnswerPanel(
    question: UIQuestion,
    questionList: MutableList<UIQuestion>,
    gameType: GameType,
    questionViewModel: QuestionViewModel,
    gameViewModel: GameViewModel,
    questionProgress: UIQuestionProgress,
    answerStatus: MutableState<AnswerStatus>,
    checkFinishedQuestion: MutableState<Boolean>,
    subTopicProgress: UITopicProgress? = null,
    mainTopicProgress: UITopicProgress? = null,
    explanation: String,
    showExplanation: MutableState<Boolean>,
    choiceListState: SnapshotStateList<String>?,
    fontSizeScale: MutableState<Float>,
    testSettingId: TestSetting?,
    testProgress: UITestProgress?,
    testInfoViewModel: TestInfoViewModel?
) {
    Column(modifier = Modifier.height(IntrinsicSize.Min)) {
        question.choices.map {
            val visibilityState = if (testSettingId == null || testSettingId == TestSetting.Easy) {
                if (checkFinishedQuestion.value) {
                    it.isCorrect || questionProgress.choiceSelectedIds.contains(it.id)
                } else true
            } else {
                true
            }

            val panelColorState =
                if (testSettingId == null || testSettingId == TestSetting.Easy) {
                    if (checkFinishedQuestion.value) {
                        Color.White
                    } else {
                        if (questionProgress.choiceSelectedIds.contains(it.id)) {
                            Color(0xffB6B6B6)
                        } else Color.White
                    }
                } else {
                    if (choiceListState!!.contains(it.id)) Color(0xffB6B6B6)
                    else Color.White
                }

            val borderAnswerColorState = if (testSettingId == null || testSettingId == TestSetting.Easy) {
                if (!checkFinishedQuestion.value) {
                    Color.Transparent
                } else {
                    if (it.isCorrect) Color(0xFF00C17C)
                    else Color(227, 30, 24).copy(alpha = 0.52f)
                }
            } else {
                Color.Transparent
            }

            val iconState = if (testSettingId == null || testSettingId == TestSetting.Easy) {
                if (!checkFinishedQuestion.value) {
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
            } else {
                mapOf(
                    "icon" to Icons.Default.Remove, "tint" to Color(0xFF4D4D4D)
                )
            }

            AnimatedVisibility(
                visible = visibilityState, enter = scaleIn()
            ) {
                AnswerItem(
                    it,
                    borderAnswerColorState = borderAnswerColorState,
                    panelColorState = panelColorState,
                    checkFinishedQuestion = checkFinishedQuestion,
                    iconState = iconState,
                    explanation = explanation,
                    fontSizeScale = fontSizeScale,
                    showExplanation = showExplanation,
                    testSetting = testSettingId
                ) {
                    runBlocking {
                        gameViewModel.onChoiceSelected(
                            answer = it,
                            question = question,
                            questionList = questionList,
                            questionProgress = questionProgress,
                            questionViewModel = questionViewModel,
                            answerStatus = answerStatus,
                            checkFinishedQuestion = checkFinishedQuestion,
                            gameType = gameType,
                            choiceSelectedIdListState = choiceListState,
                            subTopicProgress = subTopicProgress,
                            mainTopicProgress = mainTopicProgress,
                            testSettingId = testSettingId,
                            testProgress = testProgress,
                            testInfoViewModel = testInfoViewModel
                        )
                    }
                }
            }
        }
    }
}