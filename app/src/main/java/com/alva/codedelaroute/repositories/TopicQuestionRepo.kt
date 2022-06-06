package com.alva.codedelaroute.repositories

import com.alva.codedelaroute.models.TopicQuestion
import io.realm.Realm

class TopicQuestionRepo(val realm: Realm) {
    fun getQuestionIdListByParentId(parentId: Long): MutableList<String> {
        val result = realm.query(TopicQuestion::class, "parentId = '${parentId}'").find()
        return result.map {
            it.questionId
        }.toMutableList()
    }
}