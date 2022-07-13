package com.alva.codedelaroute.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alva.codedelaroute.models.*
import com.alva.codedelaroute.repositories.SqlRepo
import com.alva.codedelaroute.utils.AnswerStatus
import com.alva.codedelaroute.utils.GameType
import com.alva.codedelaroute.utils.ReviewQuestionProperty
import com.alva.codedelaroute.utils.TestSetting
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class GameViewModel : ViewModel() {

    fun getNextGameQuestion(
        questionList: MutableList<UIQuestion>, questionProgressList: MutableList<UIQuestionProgress>
    ): UIQuestion {
        //Pre-processing
        val tmpList = mutableListOf<UIQuestion>()

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

    fun getTestGameQuestion(
        questionList: MutableList<UIQuestion>, testProgress: UITestProgress, testInfoViewModel: TestInfoViewModel
    ): UIQuestion {
        if (testProgress.currentQuestionId != null) {
            val question = questionList.first { it.id == testProgress.currentQuestionId }
            if (!testProgress.answeredQuestion.any { it.questionId == question.id }) {
                testProgress.answeredQuestion.add(
                    UITestQuestionChoice(
                        questionId = question.id, listChoice = mutableListOf()
                    )
                )
                testInfoViewModel.updateTestProgressAnswerQuestion(
                    testProgress.id, testProgress.answeredQuestion
                )
            }
            return question
        } else {
            viewModelScope.launch {
                testInfoViewModel.updateTestProgressCurrentQuestionId(
                    testProgressId = testProgress.id, questionList.first().id
                )
            }
            testProgress.answeredQuestion.add(
                UITestQuestionChoice(
                    questionId = questionList.first().id, listChoice = mutableListOf()
                )
            )
            testInfoViewModel.updateTestProgressAnswerQuestion(
                testProgress.id, testProgress.answeredQuestion
            )
            return questionList.first()
        }
    }

    fun onNextTestClick(
        questionList: MutableList<UIQuestion>,
        testInfoViewModel: TestInfoViewModel,
        testProgress: UITestProgress
    ) {
        runBlocking {
            val tmpList = mutableListOf<UIQuestion>()
            tmpList.addAll(questionList)
            for (testAnswered in testProgress.answeredQuestion) {
                if (questionList.any { it.id == testAnswered.questionId }) tmpList.removeIf { it.id == testAnswered.questionId }
            }
            testProgress.currentQuestionId = tmpList.first().id
            testInfoViewModel.updateTestProgressCurrentQuestionId(testProgressId = testProgress.id, tmpList.first().id)
        }
    }

    fun onChoiceSelected(
        answer: UIAnswer,
        question: UIQuestion,
        questionList: MutableList<UIQuestion>,
        questionProgress: UIQuestionProgress,
        questionViewModel: QuestionViewModel,
        answerStatus: MutableState<AnswerStatus>,
        checkFinishedQuestion: MutableState<Boolean>,
        gameType: GameType,
        choiceSelectedIdListState: SnapshotStateList<String>?,
        subTopicProgress: UITopicProgress?,
        mainTopicProgress: UITopicProgress?,
        testSettingId: TestSetting?,
        testProgress: UITestProgress?,
        testInfoViewModel: TestInfoViewModel?
    ) {
        runBlocking {
            when (gameType) {
                GameType.Practice -> {
                    if (!checkFinishedQuestion.value) {
                        if (!questionProgress.choiceSelectedIds.contains(answer.id)) {
                            questionProgress.choiceSelectedIds.add(answer.id)
                        }
                    }
                    practiceGameDataSavingHandler(
                        currentQuestionProgress = questionProgress,
                        question = question,
                        questionViewModel = questionViewModel,
                        subTopicProgress = subTopicProgress!!,
                        mainTopicProgress = mainTopicProgress!!
                    )
                    gameAnswerPanelUIHandler(
                        checkFinishedQuestion = checkFinishedQuestion,
                        question = question,
                        questionProgress = questionProgress,
                        answer = answer,
                        answerStatus = answerStatus,
                    )
                }
                GameType.Test -> {
                    if (!checkFinishedQuestion.value) {
                        if (!questionProgress.choiceSelectedIds.contains(answer.id)) {
                            questionProgress.choiceSelectedIds.add(answer.id)
                            choiceSelectedIdListState!!.add(answer.id)
                        }
                        if (testProgress?.answeredQuestion?.any { it.questionId == question.id }!!) {
                            if (!testProgress.answeredQuestion.first { it.questionId == question.id }.listChoice.any { it.id == answer.id }) {
                                testProgress.answeredQuestion.first { it.questionId == question.id }.listChoice.add(
                                    answer
                                )
                            }
                        } else {
                            testProgress.answeredQuestion.add(
                                UITestQuestionChoice(
                                    questionId = question.id, listChoice = mutableListOf(answer)
                                )
                            )
                        }
                    } else if (question.correctAnswers.size == testProgress!!.answeredQuestion.first { it.questionId == question.id }.listChoice.size && testSettingId != TestSetting.Easy) {
                        if (!questionProgress.choiceSelectedIds.contains(answer.id)) {
                            questionProgress.choiceSelectedIds.removeFirst()
                            questionProgress.choiceSelectedIds.add(answer.id)

                            choiceSelectedIdListState!!.removeFirst()
                            choiceSelectedIdListState.add(answer.id)
                        }
                        if (!testProgress.answeredQuestion.first { it.questionId == question.id }.listChoice.any { it.id == answer.id }) {
                            testProgress.answeredQuestion.first { it.questionId == question.id }.listChoice.apply {
                                this.removeFirst()
                                this.add(answer)
                            }
                        }
                    }
                    testGameDataSavingHandler(
                        questionList = questionList,
                        question = question,
                        currentQuestionProgress = questionProgress,
                        testInfoViewModel = testInfoViewModel!!,
                        testProgress = testProgress,
                        testSettingId = testSettingId
                    )
                    gameAnswerPanelUIHandler(
                        checkFinishedQuestion = checkFinishedQuestion,
                        question = question,
                        questionProgress = questionProgress,
                        answer = answer,
                        answerStatus = answerStatus,
                        testSettingId = testSettingId
                    )
                }
                GameType.Review -> {
                    if (!checkFinishedQuestion.value) {
                        if (!questionProgress.choiceSelectedIds.contains(answer.id)) {
                            questionProgress.choiceSelectedIds.add(answer.id)
                        }
                    }
                    reviewGameDataSavingHandler(
                        currentQuestionProgress = questionProgress,
                        question = question,
                        questionViewModel = questionViewModel,
                    )
                    gameAnswerPanelUIHandler(
                        checkFinishedQuestion = checkFinishedQuestion,
                        question = question,
                        questionProgress = questionProgress,
                        answer = answer,
                        answerStatus = answerStatus,
                    )
                }
            }
        }
    }

    private fun gameAnswerPanelUIHandler(
        checkFinishedQuestion: MutableState<Boolean>,
        question: UIQuestion,
        questionProgress: UIQuestionProgress,
        answer: UIAnswer,
        answerStatus: MutableState<AnswerStatus>,
        testSettingId: TestSetting? = null,
    ) {
        checkFinishedQuestion.value = isFinishQuestion(
            question, currentQuestionProgress = questionProgress
        )
        if (testSettingId == null || testSettingId == TestSetting.Easy) {
            if (checkFinishedQuestion.value) {
                if (answer.isCorrect) {
                    answerStatus.value = AnswerStatus.True
                } else {
                    answerStatus.value = AnswerStatus.False
                }
            }
        }
    }

    private fun practiceGameDataSavingHandler(
        currentQuestionProgress: UIQuestionProgress,
        question: UIQuestion,
        questionViewModel: QuestionViewModel,
        subTopicProgress: UITopicProgress,
        mainTopicProgress: UITopicProgress,
    ) {
        runBlocking {
            if (isFinishQuestion(
                    question = question, currentQuestionProgress = currentQuestionProgress
                )
            ) {
                if (currentQuestionProgress.choiceSelectedIds.any { answerID -> !(question.choices.first { it.id == answerID }.isCorrect) }) {
                    currentQuestionProgress.also {
                        it.boxNum = -1
                        it.progress.add(-1)
                    }
                } else {
                    currentQuestionProgress.also {
                        it.boxNum = 1
                        it.progress.add(1)
                    }
                    if (!isQuestionPreviouslyAnsweredTrue(
                            questionId = question.id.toLong(),
                            topicId = currentQuestionProgress.topicId.toLong(),
                            questionViewModel = questionViewModel
                        )
                    ) {
                        subTopicProgress.correctNumber++
                        mainTopicProgress.correctNumber++
                        SqlRepo.addOrUpdateTopicProgressToRepo(topicProgress = subTopicProgress)
                        SqlRepo.addOrUpdateTopicProgressToRepo(topicProgress = mainTopicProgress)
                    }
                }
                questionViewModel.addOrUpdateQuestionProgressToRepo(
                    currentQuestionProgress, gameType = GameType.Practice
                )
                questionViewModel.updateAllFamiliarQuestionsCount()
                questionViewModel.updateQuestionListCountByReviewProperty(ReviewQuestionProperty.WeakQuestions)
                questionViewModel.updateQuestionListCountByReviewProperty(ReviewQuestionProperty.MediumQuestions)
                questionViewModel.updateQuestionListCountByReviewProperty(ReviewQuestionProperty.StrongQuestions)
            }
        }
    }

    private fun testGameDataSavingHandler(
        questionList: MutableList<UIQuestion>,
        question: UIQuestion,
        currentQuestionProgress: UIQuestionProgress,
        testInfoViewModel: TestInfoViewModel,
        testProgress: UITestProgress,
        testSettingId: TestSetting?
    ) {
        runBlocking {
            testInfoViewModel.updateTestProgressAnswerQuestion(testProgress.id, testProgress.answeredQuestion)
            if (testSettingId == TestSetting.Easy) {
                if (isFinishQuestion(
                        question = question,
                        currentQuestionProgress = currentQuestionProgress,
                    )
                ) {
                    testInfoViewModel.calculateTestCorrectNumber(testProgress)

                    val tmpList = mutableListOf<UIQuestion>()
                    tmpList.addAll(questionList)

                    for (testAnswered in testProgress.answeredQuestion) {
                        if (questionList.any { it.id == testAnswered.questionId }) tmpList.removeIf { it.id == testAnswered.questionId }
                    }

                    testProgress.currentQuestionId = tmpList.first().id

                    testInfoViewModel.updateTestProgressCurrentQuestionId(
                        testProgressId = testProgress.id, tmpList.first().id
                    )
                }
            } else {
                if (isFinishQuestion(
                        question = question, currentQuestionProgress
                    )
                ) {
                    testInfoViewModel.calculateTestCorrectNumber(testProgress)
                }
            }
        }
    }

    private fun reviewGameDataSavingHandler(
        currentQuestionProgress: UIQuestionProgress,
        question: UIQuestion,
        questionViewModel: QuestionViewModel,
    ) {
        runBlocking {
            if (isFinishQuestion(
                    question = question, currentQuestionProgress = currentQuestionProgress
                )
            ) {
                if (currentQuestionProgress.choiceSelectedIds.any { answerID -> !(question.choices.first { it.id == answerID }.isCorrect) }) {
                    currentQuestionProgress.also {
                        it.boxNum = -1
                        it.progress.add(-1)
                    }
                } else {
                    currentQuestionProgress.also {
                        it.boxNum = 1
                        it.progress.add(1)
                    }
                }
                questionViewModel.addOrUpdateQuestionProgressToRepo(
                    currentQuestionProgress, gameType = GameType.Review
                )
                questionViewModel.updateAllFamiliarQuestionsCount()
                questionViewModel.updateQuestionListCountByReviewProperty(ReviewQuestionProperty.WeakQuestions)
                questionViewModel.updateQuestionListCountByReviewProperty(ReviewQuestionProperty.MediumQuestions)
                questionViewModel.updateQuestionListCountByReviewProperty(ReviewQuestionProperty.StrongQuestions)
            }
        }
    }

    fun isFinishQuestion(
        question: UIQuestion, currentQuestionProgress: UIQuestionProgress
    ): Boolean {
        return question.correctAnswers.size == currentQuestionProgress.choiceSelectedIds.size
    }

    private fun isQuestionPreviouslyAnsweredTrue(
        questionId: Long, topicId: Long, questionViewModel: QuestionViewModel
    ): Boolean {
        val questionProgress =
            questionViewModel.getQuestionProgressByQuestionId(questionId, topicId, isForChecking = true)
        if (questionProgress.boxNum == 1) return true
        return false
    }

    fun getAnswerStatus(
        question: UIQuestion, currentQuestionProgress: UIQuestionProgress, gameType: GameType
    ): AnswerStatus {
        when (gameType) {
            GameType.Test -> {
                return AnswerStatus.None
            }
            else -> {
                return if (!isFinishQuestion(question, currentQuestionProgress)) {
                    if (currentQuestionProgress.progress.isEmpty()) {
                        AnswerStatus.None
                    } else {
                        when (currentQuestionProgress.progress.last()) {
                            1 -> AnswerStatus.TryAgainWithTrue
                            else -> AnswerStatus.TryAgainWithFalse
                        }
                    }
                } else {
                    when (currentQuestionProgress.boxNum) {
                        1 -> AnswerStatus.True
                        else -> AnswerStatus.False
                    }
                }
            }
        }
    }
}