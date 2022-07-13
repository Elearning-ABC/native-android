package com.alva.codedelaroute.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class UITopic(
    var id: String = "",
    var parentId: String = "",
    var status: Int = 0,
    var name: String = "",
    var description: String = "",
    var orderIndex: Int = 0,
    var totalQuestion: Int = 0,
    var allowMistakes: Int = 0,
    var numMaster: Int = 0,
    var childrenType: Int = 0,
    var type: Int = 0,
    var lastUpdate: Double = 0.0,
    var thumbnail: String = "",
    var icon: String = "",
    var tag: String = ""
) {
    companion object {
        fun fromRealm(topic: Topic): UITopic {
            return UITopic(
                id = topic.id,
                parentId = topic.parentId,
                status = topic.status,
                name = topic.name,
                description = topic.description,
                orderIndex = topic.orderIndex,
                totalQuestion = topic.totalQuestion,
                allowMistakes = topic.allowMistakes,
                numMaster = topic.numMaster,
                childrenType = topic.childrenType,
                type = topic.type,
                lastUpdate = topic.lastUpdate,
                thumbnail = topic.thumbnail,
                icon = topic.icon,
                tag = topic.tag,
            )
        }
    }
}

class Topic : RealmObject {
    @PrimaryKey
    var id: String = ""
    var parentId: String = ""
    var status: Int = 0
    var name: String = ""
    var description: String = ""
    var orderIndex: Int = 0
    var totalQuestion: Int = 0
    var allowMistakes: Int = 0
    var numMaster: Int = 0
    var childrenType: Int = 0
    var type: Int = 0
    var lastUpdate: Double = 0.0
    var thumbnail: String = ""
    var icon: String = ""
    var tag: String = ""
}