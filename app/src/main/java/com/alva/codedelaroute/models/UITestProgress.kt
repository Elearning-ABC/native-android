package com.alva.codedelaroute.models

import com.alva.codedelaroute.utils.TestSetting
import com.alva.codedelaroute.utils.TestStatus
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class UITestProgress(
    var id: String = "",
    var testId: String = "",
    var userId: String = "",
    var testSettingId: TestSetting = TestSetting.Easy,
    var time: Int = 0,
    var status: TestStatus = TestStatus.None,
    var lastUpdate: Double = 0.0,
    var createDate: Double = 0.0,
    var answeredQuestion: MutableList<UITestQuestionChoice> = mutableListOf(),
    var currentQuestionId: String? = null,
    var totalQuestion: Int = 0,
    var correctNumber: Int = 0,
    var lock: Boolean = true
) {
    companion object {
        fun fromRealm(testProgress: TestProgress): UITestProgress {
            return UITestProgress(
                id = testProgress.id,
                testId = testProgress.testId,
                userId = testProgress.userId,
                testSettingId = when (testProgress.testSettingId) {
                    1 -> TestSetting.Easy
                    2 -> TestSetting.Medium
                    3 -> TestSetting.Hardest
                    else -> {
                        TestSetting.Easy
                    }
                },
                time = testProgress.time,
                status = when (testProgress.status) {
                    0 -> TestStatus.None
                    1 -> TestStatus.Playing
                    2 -> TestStatus.Done
                    else -> TestStatus.Done
                },
                lastUpdate = testProgress.lastUpdate,
                createDate = testProgress.createDate,
                answeredQuestion = testProgress.answeredQuestion.map {
                    UITestQuestionChoice.fromRealm(it)
                }.toMutableList(),
                currentQuestionId = testProgress.currentQuestionId,
                totalQuestion = testProgress.totalQuestion,
                correctNumber = testProgress.correctNumber,
                lock = testProgress.lock
            )
        }
    }
}

open class TestProgress : RealmObject {
    @PrimaryKey
    var id: String = ""
    var testId: String = ""
    var userId: String = ""
    var testSettingId: Int = 0
    var time: Int = 0
    var status: Int = 0
    var lastUpdate: Double = 0.0
    var createDate: Double = 0.0
    var answeredQuestion: RealmList<TestQuestionChoice> = realmListOf()
    var currentQuestionId: String? = null
    var totalQuestion: Int = 0
    var correctNumber: Int = 0
    var lock: Boolean = true
}
