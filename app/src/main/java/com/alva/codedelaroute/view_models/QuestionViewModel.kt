package com.alva.codedelaroute.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alva.codedelaroute.models.UIQuestion
import com.alva.codedelaroute.models.UIQuestionProgress
import com.alva.codedelaroute.models.UITestProgress
import com.alva.codedelaroute.repositories.SqlRepo
import com.alva.codedelaroute.utils.GameType
import com.alva.codedelaroute.utils.ReviewQuestionProperty
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class QuestionViewModel : ViewModel() {
    private var allQuestionProgressList: MutableList<UIQuestionProgress> = mutableListOf()

    val weakQuestionsCount = MutableStateFlow(0)
    val mediumQuestionsCount = MutableStateFlow(0)
    val strongQuestionsCount = MutableStateFlow(0)
    val allAnsweredQuestionsCount = MutableStateFlow(0)
    val favoriteQuestionsCount = MutableStateFlow(0)

    init {
        allQuestionProgressList = SqlRepo.getAllQuestionProgress()
        updateAllFamiliarQuestionsCount()
        updateQuestionListCountByReviewProperty(ReviewQuestionProperty.WeakQuestions)
        updateQuestionListCountByReviewProperty(ReviewQuestionProperty.MediumQuestions)
        updateQuestionListCountByReviewProperty(ReviewQuestionProperty.StrongQuestions)
        updateFavoriteQuestionsCount()
    }

    fun getEmptyQuestionProgressListProgress(questions: MutableList<UIQuestion>): MutableList<UIQuestionProgress> {
        val list = mutableListOf<UIQuestionProgress>()
        for (question in questions) {
            val questionProgressInDb = getQuestionProgressByQuestionId(
                question.id.toLong(), question.parentId.toLong(), isForChecking = true
            ).apply {
                choiceSelectedIds = mutableListOf()
                boxNum = 0
            }
            list.add(questionProgressInDb)
        }
        return list
    }

    fun getQuestionProgressForTest(
        questions: MutableList<UIQuestion>, testProgress: UITestProgress
    ): MutableList<UIQuestionProgress> {
        val list = mutableListOf<UIQuestionProgress>()
        for (question in questions) {
            val questionProgressInDb = getQuestionProgressByQuestionId(
                question.id.toLong(), question.parentId.toLong(), isForChecking = true
            ).apply {
                choiceSelectedIds = mutableListOf()
                boxNum = 0
            }
            list.add(questionProgressInDb)
        }
        if (testProgress.answeredQuestion.isNotEmpty()) {
            testProgress.answeredQuestion.forEach { testQuestionChoice ->
                list.first { questionProgress -> questionProgress.questionId == testQuestionChoice.questionId }.choiceSelectedIds.addAll(
                    testQuestionChoice.listChoice.map { it.id }.toMutableList()
                )
            }
        }
        return list
    }

    fun getQuestionsByParentId(parentId: Long): MutableList<UIQuestion> {
        return SqlRepo.getQuestionsByParentId(parentId)
    }

    fun getQuestionProgressByQuestionId(
        questionId: Long, topicId: Long = 0, isForChecking: Boolean = false
    ): UIQuestionProgress {
        try {
            val result = allQuestionProgressList.first { it.questionId == questionId.toString() }

            return if (!isForChecking) {
                val question = getQuestionsByParentId(topicId).first { it.id == questionId.toString() }
                if (result.choiceSelectedIds.size < question.correctAnswers.size && result.choiceSelectedIds.isNotEmpty()) {
                    result.copy()
                } else result.copy().apply {
                    choiceSelectedIds = mutableListOf()
                }
            } else {
                result.copy()
            }
        } catch (e: NoSuchElementException) {
            return UIQuestionProgress(
                id = System.currentTimeMillis().toString(),
                questionId = questionId.toString(),
                topicId = topicId.toString(),
                lastUpdate = System.currentTimeMillis().toDouble()
            )
        }
    }

    suspend fun addOrUpdateQuestionProgressToRepo(questionProgress: UIQuestionProgress, gameType: GameType) {
        try {
            val questionProgressOnList = allQuestionProgressList.first { it.questionId == questionProgress.questionId }
            when (gameType) {
                GameType.Practice -> {
                    val tmp = questionProgressOnList.copy().apply {
                        if (boxNum != 1) boxNum = questionProgress.boxNum
                        progress = questionProgress.progress
                        choiceSelectedIds = questionProgress.choiceSelectedIds
                        lastUpdate = System.currentTimeMillis().toDouble()
                        bookmark = questionProgress.bookmark
                        round = questionProgress.round
                        times = questionProgress.times
                    }
                    allQuestionProgressList.removeIf { it.id == questionProgressOnList.id }
                    allQuestionProgressList.add(tmp)
                }
                else -> {
                    val tmp = questionProgressOnList.copy().apply {
                        progress = questionProgress.progress
                        lastUpdate = System.currentTimeMillis().toDouble()
                    }
                    allQuestionProgressList.removeIf { it.id == questionProgressOnList.id }
                    allQuestionProgressList.add(tmp)
                }
            }
        } catch (e: NoSuchElementException) {
            when (gameType) {
                GameType.Practice -> {
                    allQuestionProgressList.add(questionProgress)
                }
                else -> {
                    allQuestionProgressList.add(
                        UIQuestionProgress(
                            progress = questionProgress.progress,
                            lastUpdate = System.currentTimeMillis().toDouble(),
                            id = System.currentTimeMillis().toString(),
                            questionId = questionProgress.id,
                            topicId = questionProgress.topicId,
                            stateId = questionProgress.stateId,
                        )
                    )
                }
            }
        }
        SqlRepo.addOrUpdateQuestionProgressToRepo(allQuestionProgressList.first { it.questionId == questionProgress.questionId })
    }

    suspend fun saveBookmarkToRepo(questionId: Long, topicId: Long, boolean: Boolean) {
        val result = getQuestionProgressByQuestionId(questionId = questionId, topicId = topicId, isForChecking = true)
        val tmp = result.copy().apply {
            lastUpdate = System.currentTimeMillis().toDouble()
            bookmark = boolean
        }
        allQuestionProgressList.removeIf { it.questionId == questionId.toString() }
        allQuestionProgressList.add(tmp)
        updateFavoriteQuestionsCount()
        SqlRepo.addOrUpdateQuestionProgressToRepo(tmp)
    }

    fun getQuestionProgressListByTopicId(topicId: Long): MutableList<UIQuestionProgress> {
        return allQuestionProgressList.filter { it.topicId == topicId.toString() }.toMutableList()
    }

    fun getTrueQuestionsPercent(topicId: Long): Float {
        val questions = getQuestionsByParentId(topicId)
        val questionProgressList = getQuestionProgressListByTopicId(topicId)
        val trueQuestionsCount = questionProgressList.count { it.boxNum == 1 }
        return trueQuestionsCount.toFloat() / questions.size
    }

    fun getFalseQuestionsPercent(topicId: Long): Float {
        val questions = getQuestionsByParentId(topicId)
        val questionProgressList = getQuestionProgressListByTopicId(topicId)
        val falseQuestionsCount = questionProgressList.count { it.boxNum == -1 }
        return falseQuestionsCount.toFloat() / questions.size
    }

    fun getTrueQuestionsPercentByQuestionProgressList(
        questionProgressList: MutableList<UIQuestionProgress>
    ): Float {
        val trueQuestionsCount = questionProgressList.count { it.boxNum == 1 }
        return trueQuestionsCount.toFloat() / questionProgressList.size
    }

    fun getFalseQuestionsPercentByQuestionProgressList(
        questionProgressList: MutableList<UIQuestionProgress>
    ): Float {
        val falseQuestionsCount = questionProgressList.count { it.boxNum == -1 }
        return falseQuestionsCount.toFloat() / questionProgressList.size
    }

    suspend fun clearQuestionProgressData(subTopicId: Long) {
        viewModelScope.launch {
            val questionProgressList = allQuestionProgressList.filter { it.topicId == subTopicId.toString() }
            for (questionProgressItem in questionProgressList) {
                val tmp = questionProgressItem.copy().apply {
                    lastUpdate = System.currentTimeMillis().toDouble()
                    choiceSelectedIds = mutableListOf()
                    boxNum = 0
                }
                allQuestionProgressList.removeIf { it.id == questionProgressItem.id }
                allQuestionProgressList.add(tmp)
            }
            SqlRepo.clearQuestionProgressData(subTopicId)
        }
    }

    private fun getReviewQuestionProperty(questionProgress: UIQuestionProgress): ReviewQuestionProperty {
        val numberOfCorrectAnswers = questionProgress.progress.count { it == 1 }
        val percentage = numberOfCorrectAnswers.toFloat() / questionProgress.progress.size
        return if (percentage < 0.5) ReviewQuestionProperty.WeakQuestions
        else if (percentage >= 0.5 && percentage < 0.8) ReviewQuestionProperty.MediumQuestions
        else ReviewQuestionProperty.StrongQuestions
    }

    private fun getAllQuestionProgress(): MutableList<UIQuestionProgress> {
        return allQuestionProgressList
    }

    fun getProgressByQuestionId(questionId: Long): MutableList<Int> {
        return SqlRepo.getProgressByQuestionId(questionId)
    }

    private fun getAllFavoriteQuestionProgress(): MutableList<UIQuestionProgress> {
        return allQuestionProgressList.filter { it.bookmark }.toMutableList()
    }

    private fun getQuestionProgressListByReviewProperty(reviewQuestionProperty: ReviewQuestionProperty): MutableList<UIQuestionProgress> {
        val allQuestionProgresses = getAllQuestionProgress()
        val samePropertyQuestionProgressList = mutableListOf<UIQuestionProgress>()
        for (questionProgress in allQuestionProgresses) {
            val tmp = getReviewQuestionProperty(questionProgress)
            if (tmp == reviewQuestionProperty && questionProgress.progress.isNotEmpty()) {
                samePropertyQuestionProgressList.add(questionProgress)
            }
        }
        return samePropertyQuestionProgressList
    }

    private fun getAllFamiliarQuestionProgressList(): MutableList<UIQuestionProgress> {
        return allQuestionProgressList.filter { it.progress.isNotEmpty() }.toMutableList()
    }

    //questionList by review properties
    fun getAllFavoriteQuestions(): MutableList<UIQuestion> {
        return SqlRepo.getAnsweredQuestionsByQuestionProgressList(getAllFavoriteQuestionProgress())
    }

    fun getQuestionListByReviewProperty(reviewQuestionProperty: ReviewQuestionProperty): MutableList<UIQuestion> {
        val samePropertyQuestionProgressList = getQuestionProgressListByReviewProperty(reviewQuestionProperty)
        return SqlRepo.getAnsweredQuestionsByQuestionProgressList(samePropertyQuestionProgressList)
    }

    fun getAllFamiliarQuestionsList(): MutableList<UIQuestion> {
        return SqlRepo.getAllAnsweredQuestions(questionProgressList = getAllFamiliarQuestionProgressList())
    }

    //Update review question counts
    fun updateAllFamiliarQuestionsCount() {
        allAnsweredQuestionsCount.value = getAllFamiliarQuestionProgressList().size
    }

    fun updateQuestionListCountByReviewProperty(reviewQuestionProperty: ReviewQuestionProperty) {
        when (reviewQuestionProperty) {
            ReviewQuestionProperty.WeakQuestions -> weakQuestionsCount.value =
                getQuestionProgressListByReviewProperty(ReviewQuestionProperty.WeakQuestions).size
            ReviewQuestionProperty.MediumQuestions -> mediumQuestionsCount.value =
                getQuestionProgressListByReviewProperty(ReviewQuestionProperty.MediumQuestions).size
            ReviewQuestionProperty.StrongQuestions -> strongQuestionsCount.value =
                getQuestionProgressListByReviewProperty(ReviewQuestionProperty.StrongQuestions).size
            else -> {}
        }
    }

    private fun updateFavoriteQuestionsCount() {
        favoriteQuestionsCount.value = getAllFavoriteQuestionProgress().size
    }
}