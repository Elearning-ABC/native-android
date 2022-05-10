package com.alva.codedelaroute.repositories

import com.alva.codedelaroute.models.TopicQuestion
import io.realm.Realm

class TopicQuestionRepo(val realm: Realm) {
    fun getQuestionIdListByParentId(parentId: Long): MutableList<String> {
        var result = realm.query(TopicQuestion::class).query("parentId = '${parentId}'").find()
        return result.map {
            it.questionId
        }.toMutableList()
    }
}