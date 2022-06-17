package com.alva.codedelaroute.view_models

import androidx.lifecycle.ViewModel
import com.alva.codedelaroute.models.Topic
import com.alva.codedelaroute.models.TopicProgress
import com.alva.codedelaroute.repositories.SqlRepo

class TopicViewModel : ViewModel() {

    var mainTopics = mutableListOf<Topic>()
    private var allTopic = mutableListOf<Topic>()

    init {
        allTopic = SqlRepo.getAllTopic()
        mainTopics = getMainTopic()

    }

    private fun getMainTopic(): MutableList<Topic> {
        val result = allTopic.filter { it.parentId == "5681717746597888" }.toMutableList()
        result.sortBy { it.orderIndex }
        return result
    }

    fun getSubTopic(parentId: Long): MutableList<Topic> {
        val lists = allTopic.filter { it.parentId == "$parentId" }.toMutableList()
        lists.sortBy { it.orderIndex }
        return lists
    }

    fun getTopicById(id: Long): Topic {
        return allTopic.first { it.id == "$id" }
    }

    fun getTopicProgressByTopicId(topicId: Long): TopicProgress {
        return SqlRepo.getTopicProgressByTopicId(topicId)
    }

    fun checkFinishedTopic(topicId: String): Boolean {
        val topicProgress = getTopicProgressByTopicId(topicId = topicId.toLong())
        if (topicProgress.correctNumber == topicProgress.totalQuestionNumber) return true
        return false
    }

    suspend fun clearSubTopicProgressData(subTopicId: Long, parentTopicId: Long) {
        SqlRepo.clearSubTopicProgressData(subTopicId, parentTopicId)
    }

    fun getNextTopicId(currentTopic: Topic): String? {
        var result: String? = null
        val firstResults = getSubTopic(currentTopic.parentId.toLong()).sortedBy { it.orderIndex }
        if (firstResults.last().orderIndex > currentTopic.orderIndex) {
            var tmpOrderIndex = currentTopic.orderIndex + 1
            while (tmpOrderIndex <= firstResults.last().orderIndex) {
                val tmpResult = firstResults.first { it.orderIndex == tmpOrderIndex }
                if (!checkFinishedTopic(tmpResult.id)) {
                    result = tmpResult.id
                    break
                } else tmpOrderIndex++
            }
        }
        return result
    }

    fun getContinueTopic(): Topic? {
        var result: Topic? = null
        for (topic in mainTopics.sortedBy { it.orderIndex }) {
            val firstResults = getSubTopic(topic.id.toLong()).sortedBy { it.orderIndex }
            for (subTopic in firstResults) {
                if (!checkFinishedTopic(subTopic.id)) {
                    result = subTopic
                    break
                }
            }
            if (result != null) break
        }
        return result
    }
}