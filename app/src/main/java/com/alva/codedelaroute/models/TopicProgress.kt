package com.alva.codedelaroute.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class TopicProgress : RealmObject {
    @PrimaryKey
    var id: String = ""
    var topicId: String = ""
    var totalQuestionNumber: Int = 0
    var correctNumber: Int = 0
    var lastUpdate: Double = 0.0
}
