@file:OptIn(DelicateCoroutinesApi::class)

package com.alva.codedelaroute.view_models

import androidx.lifecycle.ViewModel
import com.alva.codedelaroute.models.Question
import com.alva.codedelaroute.models.QuestionProgress
import com.alva.codedelaroute.models.TopicProgress
import com.alva.codedelaroute.repositories.SqlRepo
import com.alva.codedelaroute.utils.AnswerStatus
import com.alva.codedelaroute.utils.ReviewQuestionProperty
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuestionViewModel : ViewModel() {
    private var allQuestionProgressList: MutableList<QuestionProgress> = mutableListOf()

    init {
        allQuestionProgressList = SqlRepo.getAllQuestionProgress()
    }

    fun initQuestionProgressList() {
        allQuestionProgressList = SqlRepo.getAllQuestionProgress()
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
        val questionProgress = QuestionProgress()
        try {
            val result = allQuestionProgressList.first { it.questionId == questionId.toString() }

            if (!isInReviewScreen) {
                val question = SqlRepo.getQuestionsByParentId(topicId).first { it.id == questionId.toString() }
                return if (result.boxNum != 0 && question.choices.filter { it.isCorrect }.size == result.choiceSelectedIds.size) {
                    questionProgress.questionId = result.questionId
                    questionProgress.id = result.id
                    questionProgress.topicId = result.topicId
                    questionProgress.choiceSelectedIds = realmListOf()
                    questionProgress.progress = result.progress
                    questionProgress.boxNum = result.boxNum
                    questionProgress.lastUpdate = result.lastUpdate
                    questionProgress.bookmark = result.bookmark
                    questionProgress.round = result.round
                    questionProgress.stateId = result.stateId
                    questionProgress.times = result.times
                    questionProgress
                } else {
                    questionProgress.questionId = result.questionId
                    questionProgress.id = result.id
                    questionProgress.topicId = result.topicId
                    questionProgress.choiceSelectedIds = realmListOf()
                    questionProgress.progress = result.progress
                    questionProgress.boxNum = 0
                    questionProgress.lastUpdate = result.lastUpdate
                    questionProgress.bookmark = result.bookmark
                    questionProgress.round = result.round
                    questionProgress.stateId = result.stateId
                    questionProgress.times = result.times
                    questionProgress
                }
            } else {
                questionProgress.questionId = result.questionId
                questionProgress.id = result.id
                questionProgress.topicId = result.topicId
                questionProgress.choiceSelectedIds = result.choiceSelectedIds
                questionProgress.progress = result.progress
                questionProgress.boxNum = result.boxNum
                questionProgress.lastUpdate = result.lastUpdate
                questionProgress.bookmark = result.bookmark
                questionProgress.round = result.round
                questionProgress.stateId = result.stateId
                questionProgress.times = result.times
                return questionProgress
            }
        } catch (e: NoSuchElementException) {
            questionProgress.id = System.currentTimeMillis().toString()
            questionProgress.questionId = questionId.toString()
            questionProgress.topicId = topicId.toString()
            questionProgress.lastUpdate = System.currentTimeMillis().toDouble()
            return questionProgress
        }
    }

    @DelicateCoroutinesApi
    suspend fun addOrUpdateQuestionProgressToRepo(questionProgress: QuestionProgress) {
        GlobalScope.launch {
            try {
                val result = allQuestionProgressList.first { it.questionId == questionProgress.questionId }

                val tmp = QuestionProgress()
                if (result.boxNum != 1) tmp.boxNum = questionProgress.boxNum else tmp.boxNum = result.boxNum
                tmp.progress = questionProgress.progress
                tmp.choiceSelectedIds = questionProgress.choiceSelectedIds
                tmp.lastUpdate = System.currentTimeMillis().toDouble()
                tmp.topicId = result.topicId
                tmp.questionId = result.questionId
                tmp.id = result.id
                tmp.bookmark = questionProgress.bookmark
                tmp.round = questionProgress.round
                tmp.stateId = result.stateId
                tmp.times = questionProgress.times

                allQuestionProgressList.remove(result)
                allQuestionProgressList.add(tmp)
            } catch (e: NoSuchElementException) {
                allQuestionProgressList.add(questionProgress)
            }
            SqlRepo.addOrUpdateQuestionProgressToRepo(questionProgress)
        }
    }

    private fun getAnsweredQuestionsByTopicId(topicId: Long): MutableList<QuestionProgress> {
        return allQuestionProgressList.filter { it.topicId == topicId.toString() }.toMutableList()
    }

    fun goToNextQuestion(
        questionList: MutableList<Question>,
        parentId: Long
    ): Question {
        //Pre-processing
        val questionProgressList = getAnsweredQuestionsByTopicId(parentId)

        val tmpList = mutableListOf<Question>()

        tmpList.addAll(questionList)
        for (questionProgress in questionProgressList) {
            if (questionProgress.choiceSelectedIds.size < questionList.first { it.id == questionProgress.questionId }.correctAnswerNumber) return questionList.first { it.id == questionProgress.questionId }
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

    fun getTrueQuestionsPercent(topicId: Long): Float {
        val questions = getQuestionsByParentId(topicId)
        val questionProgressList = getAnsweredQuestionsByTopicId(topicId)
        val trueQuestionsCount = questionProgressList.count { it.boxNum == 1 }
        return trueQuestionsCount.toFloat() / questions.size
    }

    fun getFalseQuestionsPercent(topicId: Long): Float {
        val questions = getQuestionsByParentId(topicId)
        val questionProgressList = getAnsweredQuestionsByTopicId(topicId)
        val falseQuestionsCount = questionProgressList.count { it.boxNum == -1 }
        return falseQuestionsCount.toFloat() / questions.size
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
        GlobalScope.launch {
            val questionProgressList = allQuestionProgressList.filter { it.topicId == subTopicId.toString() }
            for (questionProgressItem in questionProgressList) {
                val tmp = QuestionProgress()
                tmp.progress = questionProgressItem.progress
                tmp.lastUpdate = System.currentTimeMillis().toDouble()
                tmp.choiceSelectedIds = realmListOf()
                tmp.boxNum = 0
                tmp.topicId = questionProgressItem.topicId
                tmp.questionId = questionProgressItem.questionId
                tmp.id = questionProgressItem.id
                tmp.bookmark = questionProgressItem.bookmark
                tmp.round = questionProgressItem.round
                tmp.stateId = questionProgressItem.stateId
                tmp.times = questionProgressItem.times
                allQuestionProgressList.removeIf { it.id == questionProgressItem.id }
                allQuestionProgressList.add(tmp)
            }
            SqlRepo.clearQuestionProgressData(subTopicId)
        }
    }

    private fun getReviewQuestionProperty(questionProgress: QuestionProgress): ReviewQuestionProperty {
        val numberOfCorrectAnswers = questionProgress.progress.count { it == 1 }
        val percentage = numberOfCorrectAnswers.toFloat() / questionProgress.progress.size
        return if (percentage < 0.5) ReviewQuestionProperty.WeakQuestions
        else if (percentage >= 0.5 && percentage < 0.8) ReviewQuestionProperty.MediumQuestions
        else ReviewQuestionProperty.StrongQuestions
    }

    fun getAllAnsweredQuestion(): MutableList<Question> {
        return SqlRepo.getAllAnsweredQuestions(questionProgressList = allQuestionProgressList)
    }

    fun getAllAnsweredQuestionCount(): Int {
        return allQuestionProgressList.size
    }

    private fun getAllQuestionProgress(): MutableList<QuestionProgress> {
        return allQuestionProgressList
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
}