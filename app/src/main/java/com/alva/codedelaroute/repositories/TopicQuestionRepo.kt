package com.alva.codedelaroute.repositories

import com.alva.codedelaroute.models.TopicQuestion
import com.alva.codedelaroute.models.UITopicQuestion
import io.realm.kotlin.Realm

class TopicQuestionRepo(private val realm: Realm) {
    fun getQuestionIdListByParentId(parentId: Long): MutableList<String> {
        val result = realm.query(TopicQuestion::class, "parentId = '${parentId}'").find()
        return result.map {
            it.questionId
        }.toMutableList()
    }
}