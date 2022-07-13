package com.alva.codedelaroute.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class UIAnswer(
    var id: String = "",
    var text: String = "",
    var questionId: String = "",
    var isCorrect: Boolean = false
) {
    companion object {
        fun fromRealm(answer: Answer): UIAnswer {
            return UIAnswer(
                id = answer.id,
                text = answer.text,
                questionId = answer.questionId,
                isCorrect = answer.isCorrect
            )
        }
    }

    fun toRealm(): Answer {
        return Answer().also {
            it.id = id
            it.questionId = questionId
            it.text = text
            it.isCorrect = isCorrect
        }
    }
}

open class Answer
    : RealmObject {
    @PrimaryKey
    var id: String = ""
    var text: String = ""
    var questionId: String = ""
    var isCorrect: Boolean = false
}