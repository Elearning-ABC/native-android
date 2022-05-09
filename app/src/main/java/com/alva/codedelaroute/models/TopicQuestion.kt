package com.alva.codedelaroute.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class TopicQuestion : RealmObject {
    @PrimaryKey
    var id: String = ""
    var parentId: String = ""
    var questionId: String = ""
    var mainTopicId: String = ""
}