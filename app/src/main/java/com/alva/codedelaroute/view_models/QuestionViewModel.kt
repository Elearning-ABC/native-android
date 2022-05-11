package com.alva.codedelaroute.view_models

import androidx.lifecycle.ViewModel
import com.alva.codedelaroute.models.Question
import com.alva.codedelaroute.models.QuestionProgress
import com.alva.codedelaroute.repositories.SqlRepo

class QuestionViewModel : ViewModel() {
    fun getQuestionsByParentId(parentId: Long): MutableList<Question> {
        val results = SqlRepo.getQuestionsByParentId(parentId)
        return results
    }

    fun getQuestionProgressById(id: Long): QuestionProgress {
        return SqlRepo.getQuestionProgressById(id)
    }

    suspend fun addOrUpdateQuestionProgressToRepo(questionProgress: QuestionProgress) {
        SqlRepo.addOrUpdateQuestionProgressToRepo(questionProgress)
    }

    fun goToNextQuestion(
        questionList: MutableList<Question>, parentId: Long, currentQuestionProgress: QuestionProgress
    ): Question {
        var questionProgressList = SqlRepo.getAnsweredQuestionsByTopicId(parentId)
        var tmpList = questionList
        for (question in questionProgressList) {
            if (tmpList.any {
                    it.id == question.questionId
                }) tmpList.remove(tmpList.filter { it.id == question.questionId }[0])
        }

        if (tmpList.size > 0) {
            return tmpList[0]
        } else {
            if (questionProgressList.filter { it.boxNum == -1 }.size == 1 && currentQuestionProgress.boxNum == -1) {
                var getRandomSuccessulQuestion = questionProgressList.filter { it.boxNum == 1 }.random()
                return questionList.filter { it.id == getRandomSuccessulQuestion.questionId }[0]
            } else {
                var getRandomFailedQuestion = questionProgressList.filter { it.boxNum == -1 }.random()
                return questionList.filter { it.id == getRandomFailedQuestion.questionId }[0]
            }
        }
    }

    suspend fun onAnswerClick(answerId: String, currentQuestionProgress: QuestionProgress) {
        if (!currentQuestionProgress.choiceSelectedIds.contains(answerId)) {
            currentQuestionProgress.choiceSelectedIds.add(answerId)
            addOrUpdateQuestionProgressToRepo(currentQuestionProgress)
        }
    }

    fun isFinishQuestion(question: Question, currentQuestionProgress: QuestionProgress): Boolean {
        return question.choices.filter { it.isCorrect }.size == currentQuestionProgress.choiceSelectedIds.size
    }

    fun checkClickedAnswerList(answerId: String, currentQuestionProgress: QuestionProgress): Boolean {
        return currentQuestionProgress.choiceSelectedIds.contains(answerId)
    }
}