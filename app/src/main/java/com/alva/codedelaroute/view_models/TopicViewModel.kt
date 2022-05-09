package com.alva.codedelaroute.view_models

import androidx.lifecycle.ViewModel
import com.alva.codedelaroute.models.Topic
import com.alva.codedelaroute.repositories.SqlRepo

class TopicViewModel : ViewModel() {
    var mainTopics = mutableListOf<Topic>()

    fun getMainTopic() {
        mainTopics = SqlRepo.getTopicsByParentId(5681717746597888)
    }

    fun getSubTopic(parentId: Long): MutableList<Topic> {
        return SqlRepo.getTopicsByParentId(parentId)
    }
}