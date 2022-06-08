package com.alva.codedelaroute.repositories

import android.app.Application
import android.util.Log
import com.alva.codedelaroute.models.*
import com.alva.codedelaroute.utils.byteKey
import io.realm.Realm
import io.realm.RealmConfiguration

object SqlRepo : Application() {
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

    fun getQuestionsByIdList(idList: MutableList<String>): MutableList<Question> {
        return questionRepo.getQuestionsByIdList(idList)
    }

    //QuestionProgress Queries
    fun getAnsweredQuestionsByTopicId(topicId: Long): MutableList<QuestionProgress> {
        return questionProgressRepo.getAnsweredQuestionsByTopicId(topicId)
    }

    fun getQuestionProgressByQuestionId(questionId: Long, topicId: Long, isInReviewScreen: Boolean): QuestionProgress {
        return questionProgressRepo.getQuestionProgressByQuestionId(questionId, topicId, isInReviewScreen)
    }

    fun getQuestionProgress(questionId: Long) : QuestionProgress? {
        return questionProgressRepo.getQuestionProgress(questionId)
    }

    suspend fun addOrUpdateQuestionProgressToRepo(questionProgress: QuestionProgress) {
        questionProgressRepo.addOrUpdateQuestionProgressToRepo(questionProgress)
    }

    suspend fun clearQuestionProgressData(subTopicId: Long) {
        questionProgressRepo.clearQuestionProgressData(subTopicId)
    }

    fun getAllAnsweredQuestions(): MutableList<Question> {
        return questionProgressRepo.getAllAnsweredQuestions()
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

    suspend fun clearQuestionProgressDataByQuestions(questionList: MutableList<Question>) {
        questionProgressRepo.clearQuestionProgressDataByQuestions(questionList)
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