package com.alva.codedelaroute.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Question : RealmObject {
    @PrimaryKey
    var id: String = ""
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