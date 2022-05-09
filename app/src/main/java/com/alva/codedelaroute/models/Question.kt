package com.alva.codedelaroute.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.realmListOf

class Question : RealmObject {
    @PrimaryKey var id: String = ""
    var parentId: String = ""
    var paragraphId: String = ""
    var status: Int = 0
    var text: String = ""
    var video: String = ""
    var audio: String = ""
    var image: String = ""
    var hint: String = ""
    var explanation: String = ""
    var hasChild: Boolean = false
    var level: Int = 0
    var createDate: Double = 0.0
    var lastUpdate: Double = 0.0
    var choices: RealmList<Answer> = realmListOf()
    var correctAnswers: RealmList<String> = realmListOf()
    var inCorrectAnswers: RealmList<String> = realmListOf()
    var correctAnswerNumber: Int = 0
}