package com.alva.codedelaroute.repositories

import com.alva.codedelaroute.models.TopicProgress
import io.realm.kotlin.Realm

class TopicProgressRepo(val realm: Realm) {
    fun getTopicProgressByTopicId(topicId: Long): TopicProgress {
        val topic = SqlRepo.getTopicById(topicId)
        val result = realm.query(TopicProgress::class, "topicId = '$topicId'").find()
        return if (result.isEmpty()) {
            val topicProgress = TopicProgress()
            topicProgress.id = (System.currentTimeMillis() + (0..10000).random()).toString()
            topicProgress.topicId = topicId.toString()
            topicProgress.lastUpdate = System.currentTimeMillis().toDouble()
            topicProgress.totalQuestionNumber = topic.totalQuestion
            topicProgress
        } else {
            result[0]
        }
    }

    suspend fun addOrUpdateTopicProgressToRepo(topicProgress: TopicProgress) {
        realm.write {
            val result =
                this.query(TopicProgress::class, "topicId = '${topicProgress.topicId}'").first()
                    .find()

            if (result == null) {
                topicProgress.correctNumber = 1
                this.copyToRealm(topicProgress)
            } else {
                val tmp = TopicProgress()
                tmp.topicId = result.topicId
                tmp.id = result.id
                tmp.correctNumber = result.correctNumber + 1
                tmp.totalQuestionNumber = result.totalQuestionNumber
                tmp.lastUpdate = System.currentTimeMillis().toDouble()
                this.delete(result)
                this.copyToRealm(tmp)
            }
        }
    }

    suspend fun clearSubTopicProgressData(subTopicId: Long, parentTopicId: Long) {
        realm.write {
            val subTopicProgress = this.query(TopicProgress::class, "topicId = '$subTopicId'").first().find()!!
            val parenTopicProgress =
                this.query(TopicProgress::class, "topicId = '$parentTopicId'").first().find()!!

            val parentTopicProgressTemp = TopicProgress()
            parentTopicProgressTemp.topicId = parenTopicProgress.topicId
            parentTopicProgressTemp.id = parenTopicProgress.id
            parentTopicProgressTemp.correctNumber = (parenTopicProgress.correctNumber - subTopicProgress.correctNumber)
            parentTopicProgressTemp.totalQuestionNumber = parenTopicProgress.totalQuestionNumber
            parentTopicProgressTemp.lastUpdate = System.currentTimeMillis().toDouble()

            val subTopicProgressTemp = TopicProgress()
            subTopicProgressTemp.topicId = subTopicProgress.topicId
            subTopicProgressTemp.id = subTopicProgress.id
            subTopicProgressTemp.correctNumber = 0
            subTopicProgressTemp.totalQuestionNumber = subTopicProgress.totalQuestionNumber
            subTopicProgressTemp.lastUpdate = System.currentTimeMillis().toDouble()

            subTopicProgress.also { this.delete(findLatest(subTopicProgress)!!) }
            parenTopicProgress.also { this.delete(findLatest(parenTopicProgress)!!) }
            this.copyToRealm(parentTopicProgressTemp)
            this.copyToRealm(subTopicProgressTemp)
        }
    }
}