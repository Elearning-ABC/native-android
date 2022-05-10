package com.alva.codedelaroute.repositories

import android.app.Application
import com.alva.codedelaroute.models.*
import com.alva.codedelaroute.utils.byteKey
import io.realm.Realm
import io.realm.RealmConfiguration

object SqlRepo : Application() {
    var realm: Realm
    var topicRepo: TopicRepo
    var questionRepo: QuestionRepo
    var topicQuestionRepo: TopicQuestionRepo

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
                TopicQuestion::class
            )
        ).encryptionKey(byteKey).name("default.realm").build()
        realm = Realm.open(configuration)

        topicRepo = TopicRepo(realm)
        questionRepo = QuestionRepo(realm)
        topicQuestionRepo = TopicQuestionRepo(realm)
    }

    fun getTopicsByParentId(parentId: Long): MutableList<Topic> {
        return topicRepo.getTopicsByParentId(parentId)
    }

    fun getTopicById(id: Long): Topic {
        return topicRepo.getTopicById(id)
    }

    fun getQuestionsByParentId(parentId: Long): MutableList<Question> {
        return questionRepo.getQuestionsByIdList(getQuestionIdListByParentId(parentId))
    }

    fun getQuestionIdListByParentId(parentId: Long): MutableList<String> {
        return topicQuestionRepo.getQuestionIdListByParentId(parentId)
    }
}