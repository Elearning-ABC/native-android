package com.alva.codedelaroute.view_models

import androidx.lifecycle.ViewModel
import com.alva.codedelaroute.models.Question
import com.alva.codedelaroute.repositories.SqlRepo

class QuestionViewModel : ViewModel() {
    fun getQuestionsByParentId(parentId: Long): MutableList<Question> {
        val results = SqlRepo.getQuestionsByParentId(parentId)
        results.sortedBy { it.level }
        return results
    }
}