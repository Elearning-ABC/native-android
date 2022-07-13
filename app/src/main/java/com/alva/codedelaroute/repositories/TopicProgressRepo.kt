package com.alva.codedelaroute.repositories

import com.alva.codedelaroute.models.TopicProgress
import com.alva.codedelaroute.models.UITopicProgress
import io.realm.kotlin.Realm

class TopicProgressRepo(private val realm: Realm) {
    fun getTopicProgressByTopicId(topicId: Long): UITopicProgress {
        val topic = SqlRepo.getTopicById(topicId)
        val result = realm.query(TopicProgress::class, "topicId = '$topicId'").find()
        return if (result.isEmpty()) {
            UITopicProgress(
                id = (System.currentTimeMillis() + (0..10000).random()).toString(),
                topicId = topicId.toString(),
                lastUpdate = System.currentTimeMillis().toDouble(),
                totalQuestionNumber = topic.totalQuestion,
                correctNumber = 0
            )
        } else {
            UITopicProgress.fromRealm(result[0])
        }
    }

    suspend fun addOrUpdateTopicProgressToRepo(topicProgress: UITopicProgress) {
        val result = realm.query(TopicProgress::class, "topicId = '${topicProgress.topicId}'").first()
            .find()
        realm.write {
            if (result == null) {
                this.copyToRealm(topicProgress.toRealm())
            } else {
                findLatest(result)?.also {
                    it.correctNumber = topicProgress.correctNumber
                    it.lastUpdate = System.currentTimeMillis().toDouble()
                }
            }
        }
    }

    suspend fun clearSubTopicProgressData(subTopicId: Long, parentTopicId: Long) {
        val subTopicProgress = realm.query(TopicProgress::class, "topicId = '$subTopicId'").first().find()!!
        val parenTopicProgress = realm.query(TopicProgress::class, "topicId = '$parentTopicId'").first().find()!!
        realm.write {
            findLatest(parenTopicProgress)?.also {
                it.correctNumber = (parenTopicProgress.correctNumber - subTopicProgress.correctNumber)
                it.lastUpdate = System.currentTimeMillis().toDouble()
            }
            findLatest(subTopicProgress)?.also {
                it.correctNumber = 0
                it.lastUpdate = System.currentTimeMillis().toDouble()
            }
        }
    }
}