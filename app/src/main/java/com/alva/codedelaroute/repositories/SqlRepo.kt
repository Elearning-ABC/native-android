package com.alva.codedelaroute.repositories

import android.app.Application
import com.alva.codedelaroute.R
import com.alva.codedelaroute.models.*
import com.alva.codedelaroute.utils.byteKey
import io.realm.Realm
import io.realm.RealmConfiguration
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object SqlRepo : Application() {
    var realm: Realm
    var topicRepo: TopicRepo

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
    }

    fun getTopicsByParentId(parentId: Long): MutableList<Topic> {
        return topicRepo.getTopicsByParentId(parentId)
    }
}