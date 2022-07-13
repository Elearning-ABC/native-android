package com.alva.codedelaroute.repositories

import android.util.Log
import com.alva.codedelaroute.models.UITestInfo
import com.alva.codedelaroute.models.TestProgress
import com.alva.codedelaroute.models.UITestProgress
import com.alva.codedelaroute.models.UITestQuestionChoice
import com.alva.codedelaroute.utils.TestStatus
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList

class TestProgressRepo(private val realm: Realm) {
    suspend fun createTestProgressTable(testInfo: UITestInfo) {
        val result = realm.query(TestProgress::class, "testId = '${testInfo.id}'").first().find()
        if (result == null) {
            realm.write {
                for (i in 1..3) {
                    val testProgress = TestProgress().apply {
                        id = (System.currentTimeMillis() + (0..10000).random()).toString()
                        testId = testInfo.id
                        testSettingId = i
                        time = 0
                        status = 0
                        lastUpdate = System.currentTimeMillis().toDouble()
                        createDate = System.currentTimeMillis().toDouble()
                        answeredQuestion = realmListOf()
                        totalQuestion = testInfo.totalQuestion
                        correctNumber = 0
                        lock = testInfo.index != 0
                    }
                    this.copyToRealm(testProgress)
                }
            }
        }
    }

    fun getAllTestProgressByTestInfoId(testInfoId: String): MutableList<UITestProgress> {
        return realm.query(TestProgress::class, "testId == '$testInfoId'").find().map { UITestProgress.fromRealm(it) }
            .toMutableList()
    }

    fun getTestProgressById(id: String): UITestProgress {
        val result = realm.query(TestProgress::class, "id = '$id'").first().find()!!
        return UITestProgress.fromRealm(result)
    }

    suspend fun updateTestProgressAnswerQuestion(
        testProgressId: String, listTestQuestionChoice: MutableList<UITestQuestionChoice>
    ) {
        val result = realm.query(TestProgress::class, "id = '$testProgressId'").first().find()
        realm.write {
            findLatest(result!!)?.also {
                Log.d("Hello", it.correctNumber.toString())
                it.answeredQuestion =
                    listTestQuestionChoice.map { testQuestion -> testQuestion.toRealm() }.toRealmList()
                it.lastUpdate = System.currentTimeMillis().toDouble()
            }
        }
    }

    suspend fun updateTestProgressTime(testProgressId: String, time: Int) {
        val result = realm.query(TestProgress::class, "id = '$testProgressId'").first().find()
        realm.write {
            findLatest(result!!)?.also {
                it.time = time
                it.lastUpdate = System.currentTimeMillis().toDouble()
            }
        }
    }

    suspend fun updateTestProgressStatus(testProgressId: String, status: TestStatus) {
        val result = realm.query(TestProgress::class, "id = '$testProgressId'").first().find()
        realm.write {
            findLatest(result!!)?.also {
                it.status = when (status) {
                    TestStatus.None -> 0
                    TestStatus.Playing -> 1
                    TestStatus.Done -> 2
                }
                it.lastUpdate = System.currentTimeMillis().toDouble()
            }
        }
    }

    suspend fun resetTestProgressData(testProgressId: String) {
        val result = realm.query(TestProgress::class, "id = '$testProgressId'").first().find()
        realm.write {
            findLatest(result!!)?.also {
                it.time = 0
                it.currentQuestionId = null
                it.correctNumber = 0
                it.answeredQuestion = realmListOf()
                it.lastUpdate = System.currentTimeMillis().toDouble()
            }
        }
    }

    suspend fun updateTestProgressCorrectNumber(testProgressId: String, correctNumber: Int) {
        val result = realm.query(TestProgress::class, "id = '$testProgressId'").first().find()
        realm.write {
            findLatest(result!!)?.also {
                it.correctNumber = correctNumber
                it.lastUpdate = System.currentTimeMillis().toDouble()
            }
        }
    }

    suspend fun updateTestProgressCurrentQuestionId(testProgressId: String, currentQuestionId: String) {
        val result = realm.query(TestProgress::class, "id = '$testProgressId'").first().find()
        realm.write {
            findLatest(result!!)?.also {
                it.currentQuestionId = currentQuestionId
                it.lastUpdate = System.currentTimeMillis().toDouble()
            }
        }
    }

    suspend fun updateTestProgressLockStatus(testProgressId: String, lock: Boolean) {
        val result = realm.query(TestProgress::class, "id = '$testProgressId'").first().find()
        realm.write {
            findLatest(result!!)?.also {
                it.lock = lock
                it.lastUpdate = System.currentTimeMillis().toDouble()
            }
        }
    }
}