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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AnswerViewModel : ViewModel() {
    companion object {
        var viewModelStoreOwner = ViewModelStoreOwner { ViewModelStore() }
        var key = "AnswerViewModel"
    }

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
        subTopicProgress: TopicProgress,
        mainTopicProgress: TopicProgress
    ) {
        coroutine.launch {
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
        }
    }
}