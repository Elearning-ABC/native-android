package com.alva.codedelaroute.repositories

import com.alva.codedelaroute.models.Question
import com.alva.codedelaroute.models.QuestionProgress
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.realmListOf

class QuestionProgressRepo(val realm: Realm) {

    fun getProgressByQuestionId(questionId: Long): MutableList<Int> {
        val result = realm.query(QuestionProgress::class, "questionId = '$questionId'").first().find()
        return result!!.progress.toMutableList()
    }

    suspend fun addOrUpdateQuestionProgressToRepo(questionProgress: QuestionProgress) {
        realm.write {
            val result =
                realm.query(QuestionProgress::class, "questionId = '${questionProgress.questionId}'").first().find()
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
                tmp.bookmark = questionProgress.bookmark
                tmp.round = questionProgress.round
                tmp.stateId = result.stateId
                tmp.times = questionProgress.times
                this.delete(this.findLatest(result)!!)
                this.copyToRealm(tmp)
            }
        }
    }

    suspend fun clearQuestionProgressData(subTopicId: Long) {
        realm.write {
            val questionProgressList = realm.query(QuestionProgress::class, "topicId = '$subTopicId'").find()
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
                questionProgressItem.also { delete(findLatest(it)!!) }
                this.copyToRealm(tmp)
            }
        }
    }

    fun getAllAnsweredQuestions(questionProgressList: MutableList<QuestionProgress>): MutableList<Question> {
        val results = mutableListOf<Question>()
        for (questionProgress in questionProgressList) {
            val tmp = realm.query(Question::class, "id = '${questionProgress.questionId}'").first().find()
            if (tmp != null) {
                results.add(tmp)
            }
        }
        return results
    }

    fun getAllQuestionProgress(): MutableList<QuestionProgress> {
        return realm.query(QuestionProgress::class).find().toMutableList()
    }

    fun getAnsweredQuestionsByQuestionProgressList(questionProgressList: MutableList<QuestionProgress>): MutableList<Question> {
        val results = mutableListOf<Question>()
        for (questionProgress in questionProgressList) {
            val tmp = realm.query(Question::class, "id = '${questionProgress.questionId}'").first().find()
            if (tmp != null) {
                results.add(tmp)
            }
        }
        return results
    }
}