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

    fun getEmptyQuestionProgressListProgress(questions: MutableList<Question>): MutableList<QuestionProgress> {
        val list = mutableListOf<QuestionProgress>()
        for (question in questions) {
            val tmp = QuestionProgress()
            val questionProgressInDb = getQuestionProgressByQuestionId(
                question.id.toLong(), isInReviewScreen = true
            )
            tmp.apply {
                id = questionProgressInDb.id
                questionId = questionProgressInDb.questionId
                progress = questionProgressInDb.progress
                topicId = questionProgressInDb.topicId
                lastUpdate = System.currentTimeMillis().toDouble()
                choiceSelectedIds = realmListOf()
                boxNum = 0
                bookmark = questionProgressInDb.bookmark
            }
            list.add(tmp)
        }
        return list
    }

    fun getQuestionsByParentId(parentId: Long): MutableList<Question> {
        return SqlRepo.getQuestionsByParentId(parentId)
    }

    fun getQuestionProgressByQuestionId(
        questionId: Long,
        topicId: Long = 0,
        isInReviewScreen: Boolean = false
    ): QuestionProgress {
        return SqlRepo.getQuestionProgressByQuestionId(questionId, topicId, isInReviewScreen)
    }

    fun getQuestionProgress(questionId: Long): QuestionProgress? {
        return SqlRepo.getQuestionProgress(questionId)
    }

    suspend fun addOrUpdateQuestionProgressToRepo(questionProgress: QuestionProgress) {
        SqlRepo.addOrUpdateQuestionProgressToRepo(questionProgress)
    }

    fun getQuestionIdListByParentId(parentId: Long): MutableList<String> {
        return SqlRepo.getQuestionIdListByParentId(parentId)
    }

    fun getQuestionsByIdList(idList: MutableList<String>): MutableList<Question> {
        return SqlRepo.getQuestionsByIdList(idList)
    }

    fun goToNextQuestion(
        questionList: MutableList<Question>,
        parentId: Long
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
        } else {
            questionProgressList.sortBy { it.lastUpdate }
            val previousQuestionProgress = questionProgressList.last()
            val failedQuestionList = questionProgressList.filter { it.boxNum == -1 }

            return if (failedQuestionList.size == 1 && previousQuestionProgress.boxNum == -1) {
                val successfulQuestionList = questionProgressList.filter { it.boxNum == 1 }
                if (successfulQuestionList.isEmpty()) {
                    questionList.first()
                } else {
                    val getRandomSuccessfulQuestion = successfulQuestionList.random()
                    questionList.first { it.id == getRandomSuccessfulQuestion.questionId }
                }
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

    fun goToNextReviewQuestion(
        questionList: MutableList<Question>,
        tempQuestionProgressList: MutableList<QuestionProgress>
    ): Question {
        val tmpList = mutableListOf<Question>()

        tmpList.addAll(questionList)
        for (questionProgress in tempQuestionProgressList) {
            if (questionProgress.boxNum != 0) tmpList.remove(tmpList.first { it.id == questionProgress.questionId })
        }

        //getQuestion
        if (tmpList.size > 0) {
            return tmpList.first()
        } else {
            tempQuestionProgressList.sortBy { it.lastUpdate }
            val previousQuestionProgress = tempQuestionProgressList.last()
            val failedQuestionList = tempQuestionProgressList.filter { it.boxNum == -1 }

            return if (failedQuestionList.size == 1 && previousQuestionProgress.boxNum == -1) {
                val successfulQuestionList = tempQuestionProgressList.filter { it.boxNum == 1 }
                if (successfulQuestionList.isEmpty()) {
                    questionList.first()
                } else {
                    val getRandomSuccessfulQuestion = successfulQuestionList.random()
                    questionList.first { it.id == getRandomSuccessfulQuestion.questionId }
                }
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
        subTopicProgress: TopicProgress?,
        mainTopicProgress: TopicProgress?
    ) {
        val tmp = QuestionProgress()
        tmp.apply {
            lastUpdate = currentQuestionProgress.lastUpdate
            choiceSelectedIds = currentQuestionProgress.choiceSelectedIds
            boxNum = currentQuestionProgress.boxNum
            topicId = currentQuestionProgress.topicId
            questionId = currentQuestionProgress.questionId
            id = currentQuestionProgress.id
            bookmark = currentQuestionProgress.bookmark
            round = currentQuestionProgress.round
            stateId = currentQuestionProgress.stateId
            times = currentQuestionProgress.times
        }
        val progressList = currentQuestionProgress.progress.toMutableList()


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
                        ) && subTopicProgress != null && mainTopicProgress != null
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

    fun resetReviewQuestionPropertyData() {

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

    fun getTrueQuestionsPercentByQuestionProgressList(
        questionProgressList: MutableList<QuestionProgress>
    ): Float {
        val trueQuestionsCount = questionProgressList.count { it.boxNum == 1 }
        return trueQuestionsCount.toFloat() / questionProgressList.size
    }

    fun getFalseQuestionsPercentByQuestionProgressList(
        questionProgressList: MutableList<QuestionProgress>
    ): Float {
        val falseQuestionsCount = questionProgressList.count { it.boxNum == -1 }
        return falseQuestionsCount.toFloat() / questionProgressList.size
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

    private fun getAllQuestionProgress(): MutableList<QuestionProgress> {
        return SqlRepo.getAllQuestionProgress()
    }

    private fun getQuestionProgressListByReviewProperty(reviewQuestionProperty: ReviewQuestionProperty): MutableList<QuestionProgress> {
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

    private fun getAllFavoriteQuestionProgress(): MutableList<QuestionProgress> {
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

    fun getQuestionProgressListByQuestions(questionList: MutableList<Question>): MutableList<QuestionProgress> {
        val questionProgressList = mutableListOf<QuestionProgress>()
        for (question in questionList) {
            getQuestionProgress(questionId = question.id.toLong())?.let {
                questionProgressList.add(
                    it
                )
            }
        }
        return questionProgressList
    }

    suspend fun clearQuestionProgressDataByQuestions(questionList: MutableList<Question>) {
        SqlRepo.clearQuestionProgressDataByQuestions(questionList)
    }
}