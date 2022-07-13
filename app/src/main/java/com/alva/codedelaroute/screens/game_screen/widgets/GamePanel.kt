package com.alva.codedelaroute.screens.game_screen.widgets

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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


@Composable
fun GamePanel(
    modifier: Modifier = Modifier,
    gameType: GameType,
    currentQuestion: MutableState<UIQuestion>,
    questionList: MutableList<UIQuestion>,
    answerStatus: MutableState<AnswerStatus>,
    questionViewModel: QuestionViewModel,
    gameViewModel: GameViewModel,
    questionProgress: UIQuestionProgress,
    subTopicProgress: UITopicProgress? = null,
    mainTopicProgress: UITopicProgress? = null,
    checkFinishedQuestion: MutableState<Boolean>,
    sliderValue: MutableState<Float>,
    showExplanation: MutableState<Boolean>,
    choiceListState: SnapshotStateList<String>? = null,
    testSettingId: TestSetting? = null,
    testProgress: UITestProgress? = null,
    testInfoViewModel: TestInfoViewModel? = null
) {
    LazyColumn(modifier = modifier) {
        item {
            QuestionContainer(currentQuestion.value, answerStatus, sliderValue, testSettingId)
            Spacer(modifier = Modifier.height(20.dp))
            AnswerPanel(
                question = currentQuestion.value,
                questionList = questionList,
                gameType = gameType,
                questionViewModel = questionViewModel,
                gameViewModel = gameViewModel,
                questionProgress = questionProgress,
                answerStatus = answerStatus,
                checkFinishedQuestion = checkFinishedQuestion,
                subTopicProgress = subTopicProgress,
                mainTopicProgress = mainTopicProgress,
                explanation = currentQuestion.value.explanation,
                choiceListState = choiceListState,
                fontSizeScale = sliderValue,
                showExplanation = showExplanation,
                testSettingId = testSettingId,
                testInfoViewModel = testInfoViewModel,
                testProgress = testProgress
            )
        }
    }
}