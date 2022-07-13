package com.alva.codedelaroute.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class UITopicQuestion(
    var id: String = "",
    var parentId: String = "",
    var questionId: String = "",
    var mainTopicId: String = "",
) {
    companion object {
        fun fromRealm(topicQuestion: TopicQuestion): UITopicQuestion {
            return UITopicQuestion(
                id = topicQuestion.id,
                parentId = topicQuestion.parentId,
                questionId = topicQuestion.questionId,
                mainTopicId = topicQuestion.mainTopicId
            )
        }
    }
}

class TopicQuestion : RealmObject {
    @PrimaryKey
    var id: String = ""
    var parentId: String = ""
    var questionId: String = ""
    var mainTopicId: String = ""
}