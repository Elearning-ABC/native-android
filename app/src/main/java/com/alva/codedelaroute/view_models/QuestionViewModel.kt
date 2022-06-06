package com.alva.codedelaroute.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.alva.codedelaroute.models.Question
import com.alva.codedelaroute.models.QuestionProgress
import com.alva.codedelaroute.models.TopicProgress
import com.alva.codedelaroute.repositories.SqlRepo
import com.alva.codedelaroute.utils.AnswerStatus
import com.alva.codedelaroute.utils.ReviewQuestionProperty
import io.realm.realmListOf
import io.realm.toRealmList

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

    suspend fun addOrUpdateQuestionProgressToRepo(questionProgress: QuestionProgress) {
        SqlRepo.addOrUpdateQuestionProgressToRepo(questionProgress)
    }

    fun goToNextQuestion(
        questionList: MutableList<Question>, parentId: Long
    ): Question {
        //Pre-processing
        val questionProgressList = SqlRepo.getAnsweredQuestionsByTopicId(parentId)
        val tmpList = mutableListOf<Question>()
        tmpList.addAll(questionList)
        for (questionProgress in questionProgressList) {
            if (questionProgress.choiceSelectedIds.size < tmpList.first { it.id == questionProgress.questionId }.correctAnswerNumber) return tmpList.first { it.id == questionProgress.questionId }
            if (questionProgress.boxNum != 0) tmpList.remove(tmpList.first { it.id == questionProgress.questionId })
        }

        //getQuestion
        if (tmpList.size > 0) {
            return tmpList.first()
        }
//        else if (questionProgressList.any { questionProgress ->
//                questionProgress.choiceSelectedIds.size < questionList.first { it.id == questionProgress.questionId }.correctAnswers.size
//            }) {
//            return questionList.first { question ->
//                question.id == (questionProgressList.first {
//                    questionProgressList.any { questionProgress ->
//                        questionProgress.choiceSelectedIds.size < questionList.first { it.id == questionProgress.questionId }.correctAnswers.size
//                    }
//                }).id
//            }
//        }
        else {
            questionProgressList.sortBy { it.lastUpdate }
            val previousQuestionProgress = questionProgressList.last()
            val failedQuestionList = questionProgressList.filter { it.boxNum == -1 }

            return if (failedQuestionList.size == 1 && previousQuestionProgress.boxNum == -1) {
                val getRandomSuccessfulQuestion = questionProgressList.filter { it.boxNum == 1 }.random()
                questionList.first { it.id == getRandomSuccessfulQuestion.questionId }
            } else {
                return if (failedQuestionList.isNotEmpty()) {
                    val getRandomFailedQuestion = failedQuestionList.random()
                    questionList.first { it.id == getRandomFailedQuestion.questionId }
                } else {
                    //No effective, just to prevent app from null pointer exception
                    questionList.first()
                }
            }
        }
    }

    private fun isQuestionAnsweredTrue(questionId: Long, topicId: Long): Boolean {
        val questionProgress = getQuestionProgressByQuestionId(questionId, topicId)
        if (questionProgress.boxNum == 1) return true
        return false
    }

    suspend fun onQuestionAnswerClick(
        answerId: String,
        currentQuestionProgress: QuestionProgress,
        question: Question,
        subTopicProgress: TopicProgress,
        mainTopicProgress: TopicProgress
    ) {
        val tmp = QuestionProgress()
        val progressList = currentQuestionProgress.progress.toMutableList()
        tmp.lastUpdate = currentQuestionProgress.lastUpdate
        tmp.choiceSelectedIds = currentQuestionProgress.choiceSelectedIds
        tmp.boxNum = currentQuestionProgress.boxNum
        tmp.topicId = currentQuestionProgress.topicId
        tmp.questionId = currentQuestionProgress.questionId
        tmp.id = currentQuestionProgress.id
        tmp.bookmark = currentQuestionProgress.bookmark
        tmp.round = currentQuestionProgress.round
        tmp.stateId = currentQuestionProgress.stateId
        tmp.times = currentQuestionProgress.times

        if (!tmp.choiceSelectedIds.contains(answerId)) {
            tmp.choiceSelectedIds.add(answerId)
            if (isFinishQuestion(
                    question, currentQuestionProgress = tmp
                )
            ) {
                if (tmp.choiceSelectedIds.any { answerID -> !(question.choices.first { it.id == answerID }.isCorrect) }) {
                    tmp.boxNum = -1
                    progressList.add(-1)
                } else {
                    tmp.boxNum = 1
                    progressList.add(1)
                    if (!isQuestionAnsweredTrue(
                            questionId = question.id.toLong(), topicId = tmp.topicId.toLong()
                        )
                    ) {
                        SqlRepo.addOrUpdateTopicProgressToRepo(topicProgress = subTopicProgress)
                        SqlRepo.addOrUpdateTopicProgressToRepo(topicProgress = mainTopicProgress)
                    }
                }
                tmp.progress = progressList.toRealmList()
                addOrUpdateQuestionProgressToRepo(tmp)
            }
        }
    }

    fun isFinishQuestion(question: Question, currentQuestionProgress: QuestionProgress): Boolean {
        return question.correctAnswers.size == currentQuestionProgress.choiceSelectedIds.size
    }

    fun getAnswerStatus(question: Question, currentQuestionProgress: QuestionProgress): AnswerStatus {
        return if (!isFinishQuestion(question, currentQuestionProgress)) {
            when (currentQuestionProgress.boxNum) {
                1 -> AnswerStatus.TryAgainWithTrue
                -1 -> AnswerStatus.TryAgainWithFalse
                else -> AnswerStatus.None
            }
        } else {
            when (currentQuestionProgress.boxNum) {
                1 -> AnswerStatus.True
                else -> AnswerStatus.False
            }
        }
    }

    fun checkClickedAnswerList(answerId: String, currentQuestionProgress: QuestionProgress): Boolean {
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

    suspend fun clearQuestionProgressData(subTopicId: Long) {
        SqlRepo.clearQuestionProgressData(subTopicId)
    }

    private fun getReviewQuestionProperty(questionProgress: QuestionProgress): ReviewQuestionProperty {
        val numberOfCorrectAnswers = questionProgress.progress.count { it == 1 }
        val percentage = numberOfCorrectAnswers.toFloat() / questionProgress.progress.size
        return if (percentage < 0.5) ReviewQuestionProperty.WeakQuestions
        else if (percentage >= 0.5 && percentage < 0.8) ReviewQuestionProperty.MediumQuestions
        else ReviewQuestionProperty.StrongQuestions
    }

    fun getAllAnsweredQuestion(): MutableList<Question> {
        return SqlRepo.getAllAnsweredQuestions()
    }

    fun getAllAnsweredQuestionCount(): Int {
        return getAllAnsweredQuestion().size
    }

    fun getAllQuestionProgress(): MutableList<QuestionProgress> {
        return SqlRepo.getAllQuestionProgress()
    }

    fun getQuestionProgressListByReviewProperty(reviewQuestionProperty: ReviewQuestionProperty): MutableList<QuestionProgress> {
        val allQuestionProgresses = getAllQuestionProgress()
        val samePropertyQuestionProgressList = mutableListOf<QuestionProgress>()
        for (questionProgress in allQuestionProgresses) {
            val tmp = getReviewQuestionProperty(questionProgress)
            if (tmp == reviewQuestionProperty) {
                samePropertyQuestionProgressList.add(questionProgress)
            }
        }
        return samePropertyQuestionProgressList
    }

    fun getQuestionListByReviewProperty(reviewQuestionProperty: ReviewQuestionProperty): MutableList<Question> {
        val samePropertyQuestionProgressList = getQuestionProgressListByReviewProperty(reviewQuestionProperty)
        return SqlRepo.getAnsweredQuestionsByQuestionProgressList(samePropertyQuestionProgressList)
    }

    fun getQuestionListCountByReviewProperty(reviewQuestionProperty: ReviewQuestionProperty): Int {
        return getQuestionProgressListByReviewProperty(reviewQuestionProperty).size
    }

    fun getAllFavoriteQuestionProgress(): MutableList<QuestionProgress> {
        return getAllQuestionProgress().filter { it.bookmark }.toMutableList()
    }

    fun getAllFavoriteQuestions(): MutableList<Question> {
        return SqlRepo.getAnsweredQuestionsByQuestionProgressList(getAllFavoriteQuestionProgress())
    }

    fun getFavoriteQuestionCount(): Int {
        return getAllFavoriteQuestionProgress().size
    }

    fun getProgressByQuestionId(questionId: Long): MutableList<Int> {
        return SqlRepo.getProgressByQuestionId(questionId)
    }
}