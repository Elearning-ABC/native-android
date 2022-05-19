package com.alva.codedelaroute.repositories

import android.util.Log
import com.alva.codedelaroute.models.Question
import com.alva.codedelaroute.models.QuestionProgress
import io.realm.MutableRealm
import io.realm.Realm
import io.realm.realmListOf
import java.util.Date

class QuestionProgressRepo(val realm: Realm) {
    fun getAnsweredQuestionsByTopicId(topicId: Long): MutableList<QuestionProgress> {
        var result = mutableListOf<QuestionProgress>()
        try {
            result = realm.query(QuestionProgress::class).query("topicId = '$topicId'").find().toMutableList()
        } catch (e: Exception) {
            Log.d("answered questions bug", e.toString())
        }
        return result;
    }

    fun getQuestionProgressByQuestionId(questionId: Long, topicId: Long): QuestionProgress {
        val result = realm.query(QuestionProgress::class).query("questionId = '$questionId'").find()
        val question = SqlRepo.getQuestionsByParentId(topicId).filter { it.id == questionId.toString() }[0]
        return if (result.isEmpty()) {
            var questionProgress = QuestionProgress()
            questionProgress.id = System.currentTimeMillis().toString()
            questionProgress.questionId = questionId.toString()
            questionProgress.topicId = topicId.toString()
            questionProgress.lastUpdate = System.currentTimeMillis().toDouble()
            questionProgress
        } else if (result[0].boxNum != 0 && question.choices.filter { it.isCorrect }.size == result[0].choiceSelectedIds.size) {
            var questionProgress = QuestionProgress()
            questionProgress.questionId = result[0].questionId
            questionProgress.id = result[0].id
            questionProgress.topicId = result[0].topicId
            questionProgress.choiceSelectedIds = realmListOf()
            questionProgress.progress = result[0].progress
            questionProgress.boxNum = result[0].boxNum
            questionProgress.lastUpdate = result[0].lastUpdate
            questionProgress.bookmark = result[0].bookmark
            questionProgress.round = result[0].round
            questionProgress.stateId = result[0].stateId
            questionProgress.times = result[0].times
            questionProgress
        } else {
            result[0]
        }
    }

    suspend fun addOrUpdateQuestionProgressToRepo(questionProgress: QuestionProgress) {
        realm.write {
            val result =
                realm.query(QuestionProgress::class).query("questionId = '${questionProgress.questionId}'").first()
                    .find()
            if (result == null) {
                this.copyToRealm(questionProgress)
            } else {
                val tmp = QuestionProgress()
                if (result.boxNum != 1) tmp.boxNum = questionProgress.boxNum else tmp.boxNum = result.boxNum
                tmp.progress = questionProgress.progress
                tmp.choiceSelectedIds = questionProgress.choiceSelectedIds
                tmp.lastUpdate = System.currentTimeMillis().toDouble()
                tmp.topicId = result.topicId
                tmp.questionId = result.questionId
                tmp.id = result.id
                tmp.bookmark = result.bookmark
                tmp.round = result.round
                tmp.stateId = result.stateId
                tmp.times = result.times
                this.delete(this.findLatest(result)!!)
                this.copyToRealm(tmp)
            }
        }
    }
}