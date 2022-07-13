package com.alva.codedelaroute.repositories

import com.alva.codedelaroute.models.Question
import com.alva.codedelaroute.models.UIQuestion
import io.realm.kotlin.Realm

class QuestionRepo(private val realm: Realm) {
    fun getQuestionsByIdList(idList: MutableList<String>): MutableList<UIQuestion> {
        val results = mutableListOf<UIQuestion>()
        idList.forEach {
            val temp = realm.query(Question::class, "id = '$it'").first().find()
            if (temp != null) results.add(UIQuestion.fromRealm(temp))
        }
        return results
    }

    fun getQuestionByQuestionId(questionId: String): UIQuestion {
        val result = realm.query(Question::class, "id = '$questionId'").first().find()!!
        return UIQuestion.fromRealm(result)
    }
}