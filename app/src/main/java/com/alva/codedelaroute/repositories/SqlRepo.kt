package com.alva.codedelaroute.repositories

import android.app.Application
import com.alva.codedelaroute.models.*
import com.alva.codedelaroute.utils.byteKey
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object SqlRepo : Application() {
    private const val REALM_SCHEMA_VERSION: Long = 1

    private val realm: Realm
    private val topicRepo: TopicRepo
    private val questionRepo: QuestionRepo
    private val topicQuestionRepo: TopicQuestionRepo
    private val questionProgressRepo: QuestionProgressRepo
    private val topicProgressRepo: TopicProgressRepo

    init {
        val configuration = RealmConfiguration.Builder(
            setOf(
                Answer::class,
                Topic::class,
                AppInfo::class,
                Paragraph::class,
                Question::class,
                StateInfo::class,
                Topic::class,
                TopicQuestion::class,
                QuestionProgress::class,
                TopicProgress::class
            )
        ).schemaVersion(REALM_SCHEMA_VERSION).encryptionKey(byteKey).name("default.realm").build()
        realm = Realm.open(configuration)

        topicRepo = TopicRepo(realm)
        questionRepo = QuestionRepo(realm)
        topicQuestionRepo = TopicQuestionRepo(realm)
        questionProgressRepo = QuestionProgressRepo(realm)
        topicProgressRepo = TopicProgressRepo(realm)
    }

    override fun onTerminate() {
        realm.close()
        super.onTerminate()
    }

    //Topic Queries
    fun getAllTopic(): MutableList<Topic> {
        return topicRepo.getAllTopic()
    }

    fun getTopicById(id: Long): Topic {
        return topicRepo.getTopicById(id)
    }

    //TopicQuestion Queries
    private fun getQuestionIdListByParentId(parentId: Long): MutableList<String> {
        return topicQuestionRepo.getQuestionIdListByParentId(parentId)
    }

    //Question Queries
    fun getQuestionsByParentId(parentId: Long): MutableList<Question> {
        return questionRepo.getQuestionsByIdList(getQuestionIdListByParentId(parentId))
    }

    //QuestionProgress Queries
    suspend fun addOrUpdateQuestionProgressToRepo(questionProgress: QuestionProgress) {
        questionProgressRepo.addOrUpdateQuestionProgressToRepo(questionProgress)
    }

    suspend fun clearQuestionProgressData(subTopicId: Long) {
        questionProgressRepo.clearQuestionProgressData(subTopicId)
    }

    fun getAllAnsweredQuestions(questionProgressList: MutableList<QuestionProgress>): MutableList<Question> {
        return questionProgressRepo.getAllAnsweredQuestions(questionProgressList)
    }

    fun getAllQuestionProgress(): MutableList<QuestionProgress> {
        return questionProgressRepo.getAllQuestionProgress()
    }

    fun getAnsweredQuestionsByQuestionProgressList(questionProgressList: MutableList<QuestionProgress>): MutableList<Question> {
        return questionProgressRepo.getAnsweredQuestionsByQuestionProgressList(questionProgressList)
    }

    fun getProgressByQuestionId(questionId: Long): MutableList<Int> {
        return questionProgressRepo.getProgressByQuestionId(questionId)
    }

    //TopicProgress Queries
    fun getTopicProgressByTopicId(topicId: Long): TopicProgress {
        return topicProgressRepo.getTopicProgressByTopicId(topicId)
    }

    suspend fun addOrUpdateTopicProgressToRepo(topicProgress: TopicProgress) {
        topicProgressRepo.addOrUpdateTopicProgressToRepo(topicProgress)
    }

    suspend fun clearSubTopicProgressData(subTopicId: Long, parentTopicId: Long) {
        topicProgressRepo.clearSubTopicProgressData(subTopicId, parentTopicId)
    }
}