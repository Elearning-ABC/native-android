package com.alva.codedelaroute.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.alva.codedelaroute.models.QuestionProgress
import com.alva.codedelaroute.models.Topic
import com.alva.codedelaroute.models.TopicProgress
import com.alva.codedelaroute.repositories.SqlRepo

class TopicViewModel : ViewModel() {
    companion object {
        var viewModelStoreOwner = ViewModelStoreOwner { ViewModelStore() }
        var key = "TopicViewModel"
    }

    fun getMainTopic(): MutableList<Topic> {
        val result = SqlRepo.getTopicsByParentId(5681717746597888)
        result.sortBy { it.orderIndex }
        return result
    }

    fun getSubTopic(parentId: Long): MutableList<Topic> {
        var lists = SqlRepo.getTopicsByParentId(parentId)
        lists.sortBy { it.orderIndex }
        return lists
    }

    fun getTopicById(id: Long): Topic {
        return SqlRepo.getTopicById(id)
    }

    fun getTopicProgressByTopicId(topicId: Long): TopicProgress {
        return SqlRepo.getTopicProgressByTopicId(topicId)
    }

    private suspend fun addOrUpdateTopicProgressToRepo(
        topicProgress: TopicProgress,
    ) {
        SqlRepo.addOrUpdateTopicProgressToRepo(topicProgress)
    }
}