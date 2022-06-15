package com.alva.codedelaroute.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class TopicQuestion : RealmObject {
    @PrimaryKey
    var id: String = ""
    var parentId: String = ""
    var questionId: String = ""
    var mainTopicId: String = ""
}