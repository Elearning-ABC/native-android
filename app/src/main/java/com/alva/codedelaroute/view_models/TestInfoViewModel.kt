package com.alva.codedelaroute.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alva.codedelaroute.models.*
import com.alva.codedelaroute.repositories.SqlRepo
import com.alva.codedelaroute.utils.TestIconStatus
import com.alva.codedelaroute.utils.TestStatus
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.max
import kotlin.math.roundToInt

class TestInfoViewModel : ViewModel() {
    lateinit var allTestInfoList: MutableList<UITestInfo>

    init {
        runBlocking {
            getAllTestInfo()
            allTestInfoList.forEach {
                SqlRepo.createTestProgressTable(it)
            }
        }
    }

    private fun getAllTestInfo() {
        allTestInfoList = SqlRepo.getAllTestInfo()
    }

    fun getTestInfoById(id: String): UITestInfo {
        return allTestInfoList.first { it.id == id }
    }

    fun getQuestionListByTestInfo(testInfo: UITestInfo): MutableList<UIQuestion> {
        val questionIdList = mutableListOf<String>()
        testInfo.testQuestionData.forEach {
            questionIdList.addAll(it.questionIds)
        }
        return SqlRepo.getQuestionsByIdList(questionIdList)
    }

    fun getAllTestProgressByTestInfoId(testInfoId: String): MutableList<UITestProgress> {
        return SqlRepo.getAllTestProgressByTestInfoId(testInfoId).sortedBy { it.testSettingId }.toMutableList()
    }

    fun getTestProgressById(id: String): UITestProgress {
        return SqlRepo.getTestProgressById(id)
    }

    fun getOverallTestLockStatus(testInfoId: String): Boolean {
        return getAllTestProgressByTestInfoId(testInfoId)[0].lock
    }

    fun updateTestProgressAnswerQuestion(
        testProgressId: String, listTestQuestionChoice: MutableList<UITestQuestionChoice>
    ) {
        viewModelScope.launch {
            SqlRepo.updateTestProgressAnswerQuestion(testProgressId, listTestQuestionChoice)
        }
    }

    fun updateTestProgressTime(testProgressId: String, time: Int) {
        viewModelScope.launch {
            SqlRepo.updateTestProgressTime(testProgressId, time)
        }
    }

    fun updateTestProgressStatus(testProgressId: String, status: TestStatus) {
        viewModelScope.launch {
            SqlRepo.updateTestProgressStatus(testProgressId, status)
        }
    }

    fun updateTestProgressCorrectNumber(testProgressId: String, correctNumber: Int) {
        viewModelScope.launch {
            SqlRepo.updateTestProgressCorrectNumber(testProgressId, correctNumber)
        }
    }

    fun updateTestProgressCurrentQuestionId(testProgressId: String, currentQuestionId: String) {
        viewModelScope.launch {
            SqlRepo.updateTestProgressCurrentQuestionId(
                testProgressId = testProgressId, currentQuestionId = currentQuestionId
            )
        }
    }

    suspend fun updateTestProgressLockStatus(testProgressId: String, lock: Boolean) {
        SqlRepo.updateTestProgressLockStatus(testProgressId, lock)
    }

    suspend fun resetTestProgressData(testProgressId: String) {
        SqlRepo.resetTestProgressData(testProgressId)
    }

    fun getTestProgressPercentage(testProgress: UITestProgress): Float {
        return if (testProgress.answeredQuestion.isEmpty()) 0f
        else {
            if (testProgress.answeredQuestion.last().listChoice.isEmpty()) {
                (testProgress.answeredQuestion.size - 1) * 100.0f / testProgress.totalQuestion
            } else testProgress.answeredQuestion.size * 100.0f / testProgress.totalQuestion
        }
    }

    fun getTotalTestQuestionsAnsweredCount(testProgress: UITestProgress): Int {
        return if (testProgress.answeredQuestion.isEmpty()) 0
        else {
            if (testProgress.answeredQuestion.last().listChoice.isEmpty()) {
                testProgress.answeredQuestion.size - 1
            } else testProgress.answeredQuestion.size
        }
    }

    fun getOverallTestProgress(testProgressList: MutableList<UITestProgress>): Float {
        val tmp = mutableListOf<UITestProgress>()
        tmp.addAll(testProgressList)
        return getTestProgressPercentage(tmp.maxByOrNull { it.answeredQuestion.size }!!)
    }

    fun getTestProgressTruePercentage(testProgress: UITestProgress): Float {
        return testProgress.correctNumber.toFloat() / testProgress.totalQuestion
    }

    fun calculateTestCorrectNumber(testProgress: UITestProgress) {
        var correctNumber = 0
        testProgress.answeredQuestion.forEach {
            val question = SqlRepo.getQuestionByQuestionId(it.questionId)
            if (it.listChoice.size == question.correctAnswerNumber && !it.listChoice.any { answer -> !answer.isCorrect }) {
                correctNumber++
            }
        }
        updateTestProgressCorrectNumber(testProgressId = testProgress.id, correctNumber)
    }

    fun calculateTestCorrectNumberPerTopic(topicId: String, testInfo: UITestInfo, testProgress: UITestProgress): Float {
        var correctNumber = 0
        val list = testInfo.testQuestionData.first { it.topicId == topicId }.questionIds

        list.forEach {
            if (testProgress.answeredQuestion.any { testData -> testData.questionId == it }) {
                val testQuestionChoice = testProgress.answeredQuestion.first { testData -> testData.questionId == it }
                val question = SqlRepo.getQuestionByQuestionId(testQuestionChoice.questionId)
                if (testQuestionChoice.listChoice.size == question.correctAnswerNumber && !testQuestionChoice.listChoice.any { answer -> !answer.isCorrect }) {
                    correctNumber++
                }
            }
        }
        return correctNumber.toFloat() / list.size
    }

    fun checkTestInfoPassed(testInfo: UITestInfo, testProgressList: MutableList<UITestProgress>): TestIconStatus {
        val tmp = mutableListOf<UITestProgress>()
        tmp.addAll(testProgressList)
        return if (tmp.any { it.status == TestStatus.Done }) {
            var maxPercent = 0f
            tmp.forEach {
                val percent = getTestProgressTruePercentage(it)
                if (percent > maxPercent) maxPercent = percent
            }
            if (maxPercent * 100 >= testInfo.percentPassed) {
                TestIconStatus.Passed
            } else TestIconStatus.Failed
        } else {
            TestIconStatus.None
        }
    }

    suspend fun unLockNextTest(testInfo: UITestInfo, testProgress: UITestProgress) {
        if (((testProgress.correctNumber.toFloat() / testProgress.totalQuestion) * 100).roundToInt() >= testInfo.percentPassed) {
            val allTestInfos = mutableListOf<UITestInfo>()
            allTestInfos.addAll(allTestInfoList)
            try {
                val nexTestInfo = allTestInfos.first { it.index == testInfo.index + 1 }
                val allNextTestProgressList = getAllTestProgressByTestInfoId(nexTestInfo.id)
                allNextTestProgressList.forEach {
                    it.lock = false
                    updateTestProgressLockStatus(testProgressId = it.id, lock = it.lock)
                }
            } catch (e: Exception) {
                return
            }
        }
    }

    fun getNextTestInfoId(testInfo: UITestInfo): String? {
        val allTestInfos = mutableListOf<UITestInfo>()
        allTestInfos.addAll(allTestInfoList)
        return try {
            val nexTestInfo = allTestInfos.first { it.index == testInfo.index + 1 }
            val nextTestInfoLockStatus = getAllTestProgressByTestInfoId(testInfoId = nexTestInfo.id)[0].lock
            if (nextTestInfoLockStatus) {
                return null
            } else {
                nexTestInfo.id
            }
        } catch (e: Exception) {
            null
        }
    }
}