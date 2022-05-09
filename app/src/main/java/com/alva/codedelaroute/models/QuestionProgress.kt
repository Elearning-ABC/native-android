package com.alva.codedelaroute.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class QuestionProgress : RealmObject {
    @PrimaryKey var id: String = ""
    var questionId: String = ""
    var stateId: String = ""
    var topicId: String = ""
    var progress: MutableList<Int> = mutableListOf()
    var choiceSelectedIds: MutableList<String> = mutableListOf()
    var boxNum: Int = 0
    var times: MutableList<Int> = mutableListOf()
    var round: Int = 0
    var lastUpdate: Double = 0.0
    var bookmark: Boolean = false
}