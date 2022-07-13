package com.alva.codedelaroute.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class UITopicProgress(
    var id: String = "",
    var topicId: String = "",
    var totalQuestionNumber: Int = 0,
    var correctNumber: Int = 0,
    var lastUpdate: Double = 0.0,
) {
    companion object {
        fun fromRealm(topicProgress: TopicProgress): UITopicProgress {
            return UITopicProgress(
                id = topicProgress.id,
                topicId = topicProgress.topicId,
                totalQuestionNumber = topicProgress.totalQuestionNumber,
                correctNumber = topicProgress.correctNumber,
                lastUpdate = topicProgress.lastUpdate
            )
        }
    }

    fun toRealm(): TopicProgress{
        return TopicProgress().also {
            it.id = id
            it.topicId = topicId
            it.correctNumber = correctNumber
            it.totalQuestionNumber = totalQuestionNumber
            it.lastUpdate = lastUpdate
        }
    }
}

class TopicProgress : RealmObject {
    @PrimaryKey
    var id: String = ""
    var topicId: String = ""
    var totalQuestionNumber: Int = 0
    var correctNumber: Int = 0
    var lastUpdate: Double = 0.0
}
