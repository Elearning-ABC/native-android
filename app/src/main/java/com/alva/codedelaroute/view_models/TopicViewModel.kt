package com.alva.codedelaroute.view_models

import androidx.lifecycle.ViewModel
import com.alva.codedelaroute.models.Topic
import com.alva.codedelaroute.repositories.SqlRepo

class TopicViewModel : ViewModel() {

    fun getMainTopic(): MutableList<Topic> {
        return SqlRepo.getTopicsByParentId(5681717746597888)
    }

    fun getSubTopic(parentId: Long): MutableList<Topic> {
        var lists = SqlRepo.getTopicsByParentId(parentId)
        lists.sortBy { it.orderIndex }
        return lists
    }

    fun getTopicById(id: Long): Topic {
        return SqlRepo.getTopicById(id)
    }
}