package com.alva.codedelaroute.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.alva.codedelaroute.models.Answer
import com.alva.codedelaroute.models.Question
import com.alva.codedelaroute.models.QuestionProgress
import com.alva.codedelaroute.models.TopicProgress
import com.alva.codedelaroute.utils.AnswerStatus
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AnswerViewModel : ViewModel() {

    fun onAnswerClickHandler(
        answer: Answer,
        question: Question,
        questionProgress: QuestionProgress,
        questionViewModel: QuestionViewModel,
        panelColorState: MutableState<Color>,
        enabled: MutableState<Boolean>,
        coroutine: CoroutineScope,
        answerStatus: MutableState<AnswerStatus>,
        checkFinishedQuestion: MutableState<Boolean>,
        subTopicProgress: TopicProgress?,
        mainTopicProgress: TopicProgress?,
        isReviewScreen: Boolean
    ) {
        coroutine.launch {
            if (!isReviewScreen) {
                questionViewModel.onQuestionAnswerClick(
                    answer.id, questionProgress, question, subTopicProgress, mainTopicProgress
                )
                checkFinishedQuestion.value = questionViewModel.isFinishQuestion(
                    question, currentQuestionProgress = questionProgress
                )
                if (!checkFinishedQuestion.value) {
                    panelColorState.value = Color(0xffF6F6F6)
                } else {
                    if (answer.isCorrect) {
                        answerStatus.value = AnswerStatus.True
                    } else {
                        panelColorState.value = Color.White
                        answerStatus.value = AnswerStatus.False
                    }
                    enabled.value = false
                }
            } else {
                questionProgress.choiceSelectedIds.add(answer.id)
                checkFinishedQuestion.value = questionViewModel.isFinishQuestion(
                    question, currentQuestionProgress = questionProgress
                )
                if (!checkFinishedQuestion.value) {
                    panelColorState.value = Color(0xffF6F6F6)
                } else {
                    val actualQuestionProgress = questionViewModel.getQuestionProgressByQuestionId(
                        question.id.toLong(), isInReviewScreen = true
                    )

                    val tmp = QuestionProgress()
                    tmp.apply {
                        lastUpdate = actualQuestionProgress.lastUpdate
                        choiceSelectedIds = actualQuestionProgress.choiceSelectedIds
                        boxNum = actualQuestionProgress.boxNum
                        topicId = actualQuestionProgress.topicId
                        questionId = actualQuestionProgress.questionId
                        id = actualQuestionProgress.id
                        bookmark = actualQuestionProgress.bookmark
                        round = actualQuestionProgress.round
                        stateId = actualQuestionProgress.stateId
                        times = actualQuestionProgress.times
                    }

                    val progressList = actualQuestionProgress.progress.toMutableList()

                    if (answer.isCorrect) {
                        answerStatus.value = AnswerStatus.True
                        questionProgress.boxNum = 1
                        progressList.add(1)
                    } else {
                        panelColorState.value = Color.White
                        answerStatus.value = AnswerStatus.False
                        if (questionProgress.boxNum != 1) questionProgress.boxNum = -1
                        progressList.add(-1)
                    }
                    tmp.progress = progressList.toRealmList()
                    questionViewModel.addOrUpdateQuestionProgressToRepo(tmp)
                    questionProgress.lastUpdate = System.currentTimeMillis().toDouble()

                    enabled.value = false
                }
            }

        }
    }
}