package com.alva.codedelaroute.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class UITestDataItem(
    var id: String = "",
    var title: String = "",
    var topicId: String = "",
    var times: Int = 0,
    var questionNum: Int = 0,
    var requiredPass: Int = 0,
    var questionIds: MutableList<String> = mutableListOf(),
) {
    companion object {
        fun fromRealm(testDataItem: TestDataItem): UITestDataItem {
            return UITestDataItem(
                id = testDataItem.id,
                title = testDataItem.title,
                topicId = testDataItem.topicId,
                times = testDataItem.times,
                questionNum = testDataItem.questionNum,
                requiredPass = testDataItem.requiredPass,
                questionIds = testDataItem.questionIds.toMutableList()
            )
        }
    }
}

open class TestDataItem : RealmObject {
    @PrimaryKey
    var id: String = ""
    var title: String = ""
    var topicId: String = ""
    var times: Int = 0
    var questionNum: Int = 0
    var requiredPass: Int = 0
    var questionIds: RealmList<String> = realmListOf()
}