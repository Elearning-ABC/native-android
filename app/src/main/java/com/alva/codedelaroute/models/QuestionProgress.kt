package com.alva.codedelaroute.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.realmListOf

class QuestionProgress : RealmObject {
    @PrimaryKey var id: String = ""
    var questionId: String = ""
    var stateId: String = ""
    var topicId: String = ""
    var progress: RealmList<Int> = realmListOf()
    var choiceSelectedIds: RealmList<String> = realmListOf()
    var boxNum: Int = 0
    var times: RealmList<Int> = realmListOf()
    var round: Int = 0
    var lastUpdate: Double = 0.0
    var bookmark: Boolean = false
}
