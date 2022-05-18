package com.alva.codedelaroute.view_models

import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.alva.codedelaroute.models.Answer
import com.alva.codedelaroute.models.Question
import com.alva.codedelaroute.models.QuestionProgress
import com.alva.codedelaroute.utils.AnswerStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
        checkFinishedQuestion: MutableState<Boolean>
    ) {
        coroutine.launch {
            questionViewModel.onAnswerClick(
                answer.id, questionProgress, question
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