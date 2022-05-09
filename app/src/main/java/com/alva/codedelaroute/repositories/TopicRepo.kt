package com.alva.codedelaroute.repositories

import com.alva.codedelaroute.models.Topic
import io.realm.Realm
import io.realm.RealmResults

class TopicRepo (val realm: Realm) {
    fun getTopicsByParentId(parentId: Long): MutableList<Topic> {
        var results = realm.query(Topic::class).query("parentId = '$parentId'").find()
        return results.toMutableList()
    }
}