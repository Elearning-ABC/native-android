package com.alva.codedelaroute.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.alva.codedelaroute.models.Question
import com.alva.codedelaroute.models.QuestionProgress
import com.alva.codedelaroute.models.TopicProgress
import com.alva.codedelaroute.repositories.SqlRepo

class QuestionViewModel : ViewModel() {
    companion object {
        var viewModelStoreOwner = ViewModelStoreOwner { ViewModelStore() }
        var key = "QuestionViewModel"
    }

    fun getQuestionsByParentId(parentId: Long): MutableList<Question> {
        return SqlRepo.getQuestionsByParentId(parentId)
    }

    fun getQuestionProgressByQuestionId(questionId: Long, topicId: Long): QuestionProgress {
        return SqlRepo.getQuestionProgressByQuestionId(questionId, topicId)
    }

    private suspend fun addOrUpdateQuestionProgressToRepo(questionProgress: QuestionProgress) {
        SqlRepo.addOrUpdateQuestionProgressToRepo(questionProgress)
    }

    fun goToNextQuestion(
        questionList: MutableList<Question>, parentId: Long
    ): Question? {
        //Pre-processing
        val questionProgressList = SqlRepo.getAnsweredQuestionsByTopicId(parentId)
        val tmpList = mutableListOf<Question>()
        tmpList.addAll(questionList)
        for (questionProgress in questionProgressList) {
            if (questionProgress.boxNum == 0) return tmpList.filter { it.id == questionProgress.questionId }[0]
            if (tmpList.any {
                    it.id == questionProgress.questionId
                }) tmpList.remove(tmpList.filter { it.id == questionProgress.questionId }[0])
        }

        //getQuestion
        if (tmpList.size > 0) {
            return tmpList[0]
        } else {
            questionProgressList.sortBy { it.lastUpdate }
            questionProgressList.reverse()
            val previousQuestionProgress = questionProgressList[0]
            return if (questionProgressList.filter { it.boxNum == -1 }.size == 1 && previousQuestionProgress.boxNum == -1) {
                val getRandomSuccessfulQuestion = questionProgressList.filter { it.boxNum == 1 }.random()
                questionList.filter { it.id == getRandomSuccessfulQuestion.questionId }[0]
            } else {
                val failedQuestionList = questionProgressList.filter { it.boxNum == -1 }
                return if (failedQuestionList.isNotEmpty()) {
                    val getRandomFailedQuestion = failedQuestionList.random()
                    questionList.filter { it.id == getRandomFailedQuestion.questionId }[0]
                } else {
                    null
                }
            }
        }
    }

    fun checkFinishedTopic(questionList: MutableList<Question>, parentId: Long): Boolean {
        val questionProgressList = SqlRepo.getAnsweredQuestionsByTopicId(parentId)
        if (questionList.size != questionProgressList.size) return false
        if (questionProgressList.any { it.boxNum != 1 }) return false
        return true
    }

    suspend fun onAnswerClick(
        answerId: String,
        currentQuestionProgress: QuestionProgress,
        question: Question,
        subTopicProgress: TopicProgress,
        mainTopicProgress: TopicProgress
    ) {
        if (!currentQuestionProgress.choiceSelectedIds.contains(answerId)) {
            currentQuestionProgress.choiceSelectedIds.add(answerId)
            if (isFinishQuestion(
                    question, currentQuestionProgress = currentQuestionProgress
                )
            ) {
                if (currentQuestionProgress.choiceSelectedIds.any { answerID -> !(question.choices.filter { it.id == answerID }[0].isCorrect) }) {
                    currentQuestionProgress.boxNum = -1
                } else {
                    currentQuestionProgress.boxNum = 1
//                    subTopicProgress.correctNumber += 1
//                    mainTopicProgress.correctNumber += 1
                    SqlRepo.addOrUpdateTopicProgressToRepo(topicProgress = subTopicProgress)
                    SqlRepo.addOrUpdateTopicProgressToRepo(topicProgress = mainTopicProgress)
                }
            }
            addOrUpdateQuestionProgressToRepo(currentQuestionProgress)
        }
    }

    fun isFinishQuestion(question: Question, currentQuestionProgress: QuestionProgress): Boolean {
        return question.choices.filter { it.isCorrect }.size == currentQuestionProgress.choiceSelectedIds.size
    }

    fun checkClickedAnswerList(answerId: String, currentQuestionProgress: QuestionProgress): Boolean {
        Log.d("Hi", currentQuestionProgress.choiceSelectedIds.contains(answerId).toString())
        return currentQuestionProgress.choiceSelectedIds.contains(answerId)
    }

    fun getTrueQuestionsPercent(topicId: Long): Float {
        val questions = getQuestionsByParentId(topicId)
        val questionProgressList = SqlRepo.getAnsweredQuestionsByTopicId(topicId)
        val trueQuestionsCount = questionProgressList.count { it.boxNum == 1 }
        return trueQuestionsCount.toFloat() / questions.size
    }

    fun getFalseQuestionsPercent(topicId: Long): Float {
        val questions = getQuestionsByParentId(topicId)
        val questionProgressList = SqlRepo.getAnsweredQuestionsByTopicId(topicId)
        val falseQuestionsCount = questionProgressList.count { it.boxNum == -1 }
        return falseQuestionsCount.toFloat() / questions.size
    }

    fun getRemainingQuestionsPercent(topicId: Long): Float {
        return 100.0f - getTrueQuestionsPercent(topicId) - getFalseQuestionsPercent(topicId)
    }
}