package com.alva.codedelaroute.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class UITestQuestionChoice(
    var questionId: String = "",
    var listChoice: MutableList<UIAnswer> = mutableListOf()
) {
    companion object {
        fun fromRealm(testQuestionChoice: TestQuestionChoice): UITestQuestionChoice {
            return UITestQuestionChoice(
                questionId = testQuestionChoice.questionId,
                listChoice = testQuestionChoice.listChoice.map { UIAnswer.fromRealm(it) }.toMutableList()
            )
        }
    }

    fun toRealm(): TestQuestionChoice {
        return TestQuestionChoice().also {
            it.questionId = questionId
            it.listChoice = listChoice.map { answer -> answer.toRealm() }.toRealmList()
        }
    }
}

open class TestQuestionChoice : RealmObject {
    @PrimaryKey
    var questionId: String = ""
    var listChoice: RealmList<Answer> = realmListOf()
}