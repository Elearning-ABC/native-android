package com.alva.codedelaroute.repositories

import com.alva.codedelaroute.models.Topic
import com.alva.codedelaroute.models.UITopic
import io.realm.kotlin.Realm

class TopicRepo(private val realm: Realm) {

    fun getAllTopic(): MutableList<UITopic> {
        val results = realm.query(Topic::class).find()
        return results.toMutableList().map { it -> UITopic.fromRealm(it) }.toMutableList()
    }

    fun getTopicById(id: Long): UITopic {
        val result = realm.query(Topic::class, "id = '$id'").find()
        return UITopic.fromRealm(result[0])
    }

    fun getTopParentId(): String {
        val result = realm.query(Topic::class, "parentId = '-1'").first().find()
        return result!!.id
    }
}