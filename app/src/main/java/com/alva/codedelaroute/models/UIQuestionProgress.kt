package com.alva.codedelaroute.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class UIQuestionProgress(
    var id: String = "",
    var questionId: String = "",
    var stateId: String = "",
    var topicId: String = "",
    var progress: MutableList<Int> = mutableListOf(),
    var choiceSelectedIds: MutableList<String> = mutableListOf(),
    var boxNum: Int = 0,
    var times: MutableList<Int> = mutableListOf(),
    var round: Int = 0,
    var lastUpdate: Double = 0.0,
    var bookmark: Boolean = false,
) {
    companion object {
        fun fromRealm(questionProgress: QuestionProgress): UIQuestionProgress {
            return UIQuestionProgress(
                id = questionProgress.id,
                questionId = questionProgress.questionId,
                stateId = questionProgress.stateId,
                topicId = questionProgress.topicId,
                progress = questionProgress.progress.toMutableList(),
                choiceSelectedIds = questionProgress.choiceSelectedIds.toMutableList(),
                boxNum = questionProgress.boxNum,
                times = questionProgress.times,
                round = questionProgress.round,
                lastUpdate = questionProgress.lastUpdate,
                bookmark = questionProgress.bookmark
            )
        }
    }

    fun toRealm(): QuestionProgress {
        return QuestionProgress().also {
            it.id = id
            it.questionId = questionId
            it.stateId = stateId
            it.topicId = topicId
            it.progress = progress.toRealmList()
            it.choiceSelectedIds = choiceSelectedIds.toRealmList()
            it.boxNum = boxNum
            it.times = times.toRealmList()
            it.round = round
            it.lastUpdate = lastUpdate
            it.bookmark = bookmark
        }
    }
}

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
