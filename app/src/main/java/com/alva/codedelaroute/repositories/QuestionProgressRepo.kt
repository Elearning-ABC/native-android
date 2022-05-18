package com.alva.codedelaroute.repositories

import android.util.Log
import com.alva.codedelaroute.models.Question
import com.alva.codedelaroute.models.QuestionProgress
import io.realm.Realm
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
        return if (result.isEmpty()) {
            var questionProgress = QuestionProgress()
            questionProgress.id = System.currentTimeMillis().toString()
            questionProgress.questionId = questionId.toString()
            questionProgress.topicId = topicId.toString()
            questionProgress.lastUpdate = System.currentTimeMillis().toDouble()
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
                result.boxNum = questionProgress.boxNum
                result.progress = questionProgress.progress
                result.choiceSelectedIds = questionProgress.choiceSelectedIds
                result.lastUpdate = System.currentTimeMillis().toDouble()
            }
        }
    }
}