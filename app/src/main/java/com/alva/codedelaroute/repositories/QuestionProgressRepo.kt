package com.alva.codedelaroute.repositories

import com.alva.codedelaroute.models.Question
import com.alva.codedelaroute.models.QuestionProgress
import com.alva.codedelaroute.models.UIQuestion
import com.alva.codedelaroute.models.UIQuestionProgress
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList

class QuestionProgressRepo(private val realm: Realm) {

    fun getProgressByQuestionId(questionId: Long): MutableList<Int> {
        try {
            val result = realm.query(QuestionProgress::class, "questionId = '$questionId'").first().find()
            return result!!.progress.toMutableList()
        } catch (e: Exception) {
            return mutableListOf()
        }
    }

    suspend fun addOrUpdateQuestionProgressToRepo(questionProgress: UIQuestionProgress) {
        realm.write {
            val result =
                realm.query(QuestionProgress::class, "questionId = '${questionProgress.questionId}'").first().find()
            if (result == null) {
                this.copyToRealm(questionProgress.toRealm())
            } else {
                findLatest(result)?.also {
                    it.boxNum = questionProgress.boxNum
                    it.progress = questionProgress.progress.toRealmList()
                    it.choiceSelectedIds = questionProgress.choiceSelectedIds.toRealmList()
                    it.lastUpdate = System.currentTimeMillis().toDouble()
                    it.bookmark = questionProgress.bookmark
                    it.round = questionProgress.round
                    it.times = questionProgress.times.toRealmList()
                }
            }
        }
    }

    suspend fun clearQuestionProgressData(subTopicId: Long) {
        val questionProgressList = realm.query(QuestionProgress::class, "topicId = '$subTopicId'").find()
        realm.write {
            for (questionProgressItem in questionProgressList) {
                findLatest(questionProgressItem)?.also {
                    it.choiceSelectedIds = realmListOf()
                    it.boxNum = 0
                    it.lastUpdate = System.currentTimeMillis().toDouble()
                }
            }
        }
    }

    fun getAllAnsweredQuestions(questionProgressList: MutableList<UIQuestionProgress>): MutableList<UIQuestion> {
        val results = mutableListOf<UIQuestion>()
        for (questionProgress in questionProgressList) {
            val tmp = realm.query(Question::class, "id = '${questionProgress.questionId}'").first().find()
            if (tmp != null) {
                results.add(UIQuestion.fromRealm(tmp))
            }
        }
        return results
    }

    fun getAllQuestionProgress(): MutableList<UIQuestionProgress> {
        return realm.query(QuestionProgress::class).find().map { UIQuestionProgress.fromRealm(it) }.toMutableList()
    }

    fun getAnsweredQuestionsByQuestionProgressList(questionProgressList: MutableList<UIQuestionProgress>): MutableList<UIQuestion> {
        val results = mutableListOf<UIQuestion>()
        for (questionProgress in questionProgressList) {
            val tmp = realm.query(Question::class, "id = '${questionProgress.questionId}'").first().find()
            if (tmp != null) {
                results.add(UIQuestion.fromRealm(tmp))
            }
        }
        return results
    }
}