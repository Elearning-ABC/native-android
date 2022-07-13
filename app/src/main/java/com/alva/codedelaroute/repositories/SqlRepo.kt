package com.alva.codedelaroute.repositories

import com.alva.codedelaroute.models.*
import com.alva.codedelaroute.utils.TestStatus
import com.alva.codedelaroute.utils.byteKey
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.realmListOf

object SqlRepo {
    private const val REALM_SCHEMA_VERSION: Long = 2

    private val realm: Realm
    private val topicRepo: TopicRepo
    private val questionRepo: QuestionRepo
    private val topicQuestionRepo: TopicQuestionRepo
    private val questionProgressRepo: QuestionProgressRepo
    private val topicProgressRepo: TopicProgressRepo
    private val testInfoRepo: TestInfoRepo
    private val testProgressRepo: TestProgressRepo

    init {
        val configuration = RealmConfiguration.Builder(
            setOf(
                Answer::class,
                Topic::class,
                AppInfo::class,
                Paragraph::class,
                Question::class,
                StateInfo::class,
                TopicQuestion::class,
                QuestionProgress::class,
                TopicProgress::class,
                TestInfo::class,
                TestDataItem::class,
                TestProgress::class,
                TestQuestionChoice::class
            )
        ).schemaVersion(REALM_SCHEMA_VERSION).encryptionKey(byteKey).name("default.realm").build()
        realm = Realm.open(configuration)

        topicRepo = TopicRepo(realm)
        questionRepo = QuestionRepo(realm)
        topicQuestionRepo = TopicQuestionRepo(realm)
        questionProgressRepo = QuestionProgressRepo(realm)
        topicProgressRepo = TopicProgressRepo(realm)
        testInfoRepo = TestInfoRepo(realm)
        testProgressRepo = TestProgressRepo(realm)
    }

    //Topic Queries
    fun getAllTopic(): MutableList<UITopic> {
        return topicRepo.getAllTopic()
    }

    fun getTopicById(id: Long): UITopic {
        return topicRepo.getTopicById(id)
    }

    fun getTopParentId(): String {
        return topicRepo.getTopParentId()
    }

    //TopicQuestion Queries
    private fun getQuestionIdListByParentId(parentId: Long): MutableList<String> {
        return topicQuestionRepo.getQuestionIdListByParentId(parentId)
    }

    //Question Queries
    fun getQuestionsByParentId(parentId: Long): MutableList<UIQuestion> {
        return questionRepo.getQuestionsByIdList(getQuestionIdListByParentId(parentId))
    }

    fun getQuestionsByIdList(idList: MutableList<String>): MutableList<UIQuestion> {
        return questionRepo.getQuestionsByIdList(idList)
    }

    fun getQuestionByQuestionId(questionId: String): UIQuestion {
        return questionRepo.getQuestionByQuestionId(questionId)
    }

    //QuestionProgress Queries
    suspend fun addOrUpdateQuestionProgressToRepo(questionProgress: UIQuestionProgress) {
        questionProgressRepo.addOrUpdateQuestionProgressToRepo(questionProgress)
    }

    suspend fun clearQuestionProgressData(subTopicId: Long) {
        questionProgressRepo.clearQuestionProgressData(subTopicId)
    }

    fun getAllAnsweredQuestions(questionProgressList: MutableList<UIQuestionProgress>): MutableList<UIQuestion> {
        return questionProgressRepo.getAllAnsweredQuestions(questionProgressList)
    }

    fun getAllQuestionProgress(): MutableList<UIQuestionProgress> {
        return questionProgressRepo.getAllQuestionProgress()
    }

    fun getAnsweredQuestionsByQuestionProgressList(questionProgressList: MutableList<UIQuestionProgress>): MutableList<UIQuestion> {
        return questionProgressRepo.getAnsweredQuestionsByQuestionProgressList(questionProgressList)
    }

    fun getProgressByQuestionId(questionId: Long): MutableList<Int> {
        return questionProgressRepo.getProgressByQuestionId(questionId)
    }

    //TopicProgress Queries
    fun getTopicProgressByTopicId(topicId: Long): UITopicProgress {
        return topicProgressRepo.getTopicProgressByTopicId(topicId)
    }

    suspend fun addOrUpdateTopicProgressToRepo(topicProgress: UITopicProgress) {
        topicProgressRepo.addOrUpdateTopicProgressToRepo(topicProgress)
    }

    suspend fun clearSubTopicProgressData(subTopicId: Long, parentTopicId: Long) {
        topicProgressRepo.clearSubTopicProgressData(subTopicId, parentTopicId)
    }

    //TestInfoRepo
    fun getAllTestInfo(): MutableList<UITestInfo> {
        return testInfoRepo.getAllTestInfo()
    }

    //TestProgressRepo
    suspend fun createTestProgressTable(testInfo: UITestInfo) {
        testProgressRepo.createTestProgressTable(testInfo)
    }

    fun getAllTestProgressByTestInfoId(testInfoId: String): MutableList<UITestProgress> {
        return testProgressRepo.getAllTestProgressByTestInfoId(testInfoId)
    }

    fun getTestProgressById(id: String): UITestProgress {
        return testProgressRepo.getTestProgressById(id)
    }

    suspend fun updateTestProgressAnswerQuestion(
        testProgressId: String,
        listTestQuestionChoice: MutableList<UITestQuestionChoice>
    ) {
        testProgressRepo.updateTestProgressAnswerQuestion(
            testProgressId = testProgressId,
            listTestQuestionChoice = listTestQuestionChoice
        )
    }

    suspend fun updateTestProgressTime(testProgressId: String, time: Int) {
        testProgressRepo.updateTestProgressTime(testProgressId = testProgressId, time = time)
    }

    suspend fun updateTestProgressStatus(testProgressId: String, status: TestStatus) {
        testProgressRepo.updateTestProgressStatus(testProgressId = testProgressId, status = status)
    }

    suspend fun updateTestProgressCorrectNumber(testProgressId: String, correctNumber: Int) {
        testProgressRepo.updateTestProgressCorrectNumber(testProgressId = testProgressId, correctNumber = correctNumber)
    }

    suspend fun updateTestProgressCurrentQuestionId(testProgressId: String, currentQuestionId: String) {
        testProgressRepo.updateTestProgressCurrentQuestionId(
            testProgressId = testProgressId,
            currentQuestionId = currentQuestionId
        )
    }

    suspend fun updateTestProgressLockStatus(testProgressId: String, lock: Boolean) {
        testProgressRepo.updateTestProgressLockStatus(testProgressId, lock)
    }

    suspend fun resetTestProgressData(testProgressId: String) {
        testProgressRepo.resetTestProgressData(testProgressId)
    }
}