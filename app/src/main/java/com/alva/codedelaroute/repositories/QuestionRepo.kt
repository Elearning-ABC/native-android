package com.alva.codedelaroute.repositories

import android.util.Log
import com.alva.codedelaroute.models.Question
import com.alva.codedelaroute.models.QuestionProgress
import io.realm.Realm
import java.time.LocalDate

class QuestionRepo(val realm: Realm) {
    fun getQuestionsByIdList(idList: MutableList<String>): MutableList<Question> {
        val results = mutableListOf<Question>()
        idList.forEach {
            val temp = realm.query(Question::class, "id = '$it'").first().find()
            if (temp != null) results.add(temp)
        }
        return results
    }
}