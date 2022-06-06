package com.alva.codedelaroute.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class Answer
    : RealmObject {
    @PrimaryKey
    var id: String = ""
    var text: String = ""
    var questionId: String = ""
    var isCorrect: Boolean = false
}
