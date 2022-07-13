package com.alva.codedelaroute.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class UITestInfo(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var index: Int = -1,
    var type: Int = 0,
    var lastUpdate: Double = 0.0,
    var testQuestionData: MutableList<UITestDataItem> = mutableListOf(),
    var stateId: String = "",
    var topicId: String = "",
    var totalQuestion: Int = 0,
    var duration: Int = 0,
    var percentPassed: Int = 0,
) {
    companion object {
        fun fromRealm(testInfo: TestInfo): UITestInfo {
            return UITestInfo(
                id = testInfo.id,
                title = testInfo.title,
                description = testInfo.description,
                index = testInfo.index,
                type = testInfo.type,
                lastUpdate = testInfo.lastUpdate,
                testQuestionData = testInfo.testQuestionData.map { it -> UITestDataItem.fromRealm(it) }.toMutableList(),
                stateId = testInfo.stateId,
                topicId = testInfo.topicId,
                totalQuestion = testInfo.totalQuestion,
                duration = testInfo.duration,
                percentPassed = testInfo.percentPassed
            )
        }
    }
}

open class TestInfo : RealmObject {
    @PrimaryKey
    var id: String = ""
    var title: String = ""
    var description: String = ""
    var index: Int = -1
    var type: Int = 0
    var lastUpdate: Double = 0.0
    var testQuestionData: RealmList<TestDataItem> = realmListOf()
    var stateId: String = ""
    var topicId: String = ""
    var totalQuestion: Int = 0
    var duration: Int = 0
    var percentPassed: Int = 0
}