package com.alva.codedelaroute.repositories

import android.app.Application
import android.util.Log
import com.alva.codedelaroute.models.*
import com.alva.codedelaroute.utils.byteKey
import io.realm.Realm
import io.realm.RealmConfiguration

object SqlRepo : Application() {
    private var realm: Realm
    private var topicRepo: TopicRepo
    private var questionRepo: QuestionRepo
    private var topicQuestionRepo: TopicQuestionRepo
    private var questionProgressRepo: QuestionProgressRepo
    private var topicProgressRepo: TopicProgressRepo

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
        ).encryptionKey(byteKey).name("default.realm").build()
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
    fun getTopicsByParentId(parentId: Long): MutableList<Topic> {
        return topicRepo.getTopicsByParentId(parentId)
    }

    fun getTopicById(id: Long): Topic {
        return topicRepo.getTopicById(id)
    }

    //TopicQuestion Queries
    fun getQuestionIdListByParentId(parentId: Long): MutableList<String> {
        return topicQuestionRepo.getQuestionIdListByParentId(parentId)
    }

    //Question Queries
    fun getQuestionsByParentId(parentId: Long): MutableList<Question> {
        return questionRepo.getQuestionsByIdList(getQuestionIdListByParentId(parentId))
    }

    //QuestionProgress Queries
    fun getAnsweredQuestionsByTopicId(topicId: Long): MutableList<QuestionProgress> {
        return questionProgressRepo.getAnsweredQuestionsByTopicId(topicId)
    }

    fun getQuestionProgressByQuestionId(questionId: Long, topicId: Long): QuestionProgress {
        return questionProgressRepo.getQuestionProgressByQuestionId(questionId, topicId)
    }

    suspend fun addOrUpdateQuestionProgressToRepo(questionProgress: QuestionProgress) {
        questionProgressRepo.addOrUpdateQuestionProgressToRepo(questionProgress)
    }

    //TopicProgress Queries
    fun getTopicProgressByTopicId(topicId: Long): TopicProgress {
        return topicProgressRepo.getTopicProgressByTopicId(topicId)
    }

    suspend fun addOrUpdateTopicProgressToRepo(topicProgress: TopicProgress) {
        topicProgressRepo.addOrUpdateTopicProgressToRepo(topicProgress)
    }
}