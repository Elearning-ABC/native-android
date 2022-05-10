package com.alva.codedelaroute.repositories

import com.alva.codedelaroute.models.Question
import io.realm.Realm

class QuestionRepo(val realm: Realm) {
    fun getQuestionsByIdList(idList: MutableList<String>): MutableList<Question> {
        var results = mutableListOf<Question>()
        idList.forEach {
            val temp = realm.query(Question::class).query("id = '$it'").first().find()
            if (temp != null) results.add(temp)
        }
        return results
    }
}