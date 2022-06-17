package com.alva.codedelaroute.repositories

import com.alva.codedelaroute.models.Topic
import io.realm.kotlin.Realm

class TopicRepo(val realm: Realm) {

    fun getAllTopic(): MutableList<Topic> {
        val results = realm.query(Topic::class).find()
        return results.toMutableList()
    }

    fun getTopicById(id: Long): Topic {
        val result = realm.query(Topic::class, "id = '$id'").find()
        return result[0]
    }
}