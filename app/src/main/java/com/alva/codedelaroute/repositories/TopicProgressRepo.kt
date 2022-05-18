package com.alva.codedelaroute.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.alva.codedelaroute.models.QuestionProgress
import com.alva.codedelaroute.models.TopicProgress
import io.realm.MutableRealm
import io.realm.Realm
import kotlin.random.Random

class TopicProgressRepo(val realm: Realm) {
    fun getTopicProgressByTopicId(topicId: Long): TopicProgress {
        val topic = SqlRepo.getTopicById(topicId)
        val result = realm.query(TopicProgress::class).query("topicId = '$topicId'").find()
        return if (result.isEmpty()) {
            var topicProgress = TopicProgress()
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
                this.query(TopicProgress::class).query("topicId = '${topicProgress.topicId}'").first()
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
}