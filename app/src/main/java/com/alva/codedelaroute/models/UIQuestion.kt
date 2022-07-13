package com.alva.codedelaroute.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class UIQuestion(
    var id: String = "",
    var parentId: String = "",
    var paragraphId: String = "",
    var status: Int = 0,
    var text: String = "",
    var video: String = "",
    var audio: String = "",
    var image: String = "",
    var hint: String = "",
    var explanation: String = "",
    var hasChild: Boolean = false,
    var level: Int = 0,
    var createDate: Double = 0.0,
    var lastUpdate: Double = 0.0,
    var choices: MutableList<UIAnswer> = mutableListOf(),
    var correctAnswers: MutableList<String> = mutableListOf(),
    var inCorrectAnswers: MutableList<String> = mutableListOf(),
    var correctAnswerNumber: Int = 0
) {
    companion object {
        fun fromRealm(question: Question): UIQuestion {
            return UIQuestion(
                id = question.id,
                parentId = question.parentId,
                paragraphId = question.paragraphId,
                status = question.status,
                text = question.text,
                video = question.video,
                audio = question.audio,
                image = question.image,
                hint = question.hint,
                explanation = question.explanation,
                hasChild = question.hasChild,
                level = question.level,
                createDate = question.createDate,
                lastUpdate = question.lastUpdate,
                choices = question.choices.map { it -> UIAnswer.fromRealm(it) }.toMutableList(),
                correctAnswers = question.correctAnswers.toMutableList(),
                inCorrectAnswers = question.inCorrectAnswers.toMutableList(),
                correctAnswerNumber = question.correctAnswerNumber
            )
        }
    }
}

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