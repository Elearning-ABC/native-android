package com.alva.codedelaroute.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class QuestionProgress : RealmObject {
    @PrimaryKey
    var id: String = ""
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
