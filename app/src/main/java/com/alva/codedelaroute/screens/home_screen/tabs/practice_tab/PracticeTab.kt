package com.alva.codedelaroute.screens.home_screen.tabs.practice_tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alva.codedelaroute.models.TopicProgress
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.home_screen.widgets.TopicCard
import com.alva.codedelaroute.view_models.TopicViewModel

@Composable
fun PracticeTab(
    navController: NavController, topicViewModel: TopicViewModel = viewModel(
        viewModelStoreOwner = TopicViewModel.viewModelStoreOwner, key = TopicViewModel.key
    )
) {
    val mainTopics = topicViewModel.getMainTopic()

    val mainTopicProgressList = mutableListOf<TopicProgress>()
    for (topic in mainTopics) {
        mainTopicProgressList.add(topicViewModel.getTopicProgressByTopicId(topic.id.toLong()))
    }

    LazyColumn {
        items(mainTopics.size) {
            TopicCard(mainTopics[it], Modifier.clickable {
                navController.navigate(Routes.ChildTopicListScreen.name + "/${mainTopics[it].id}")
            }, mainTopicProgressList[it])
            if (it == mainTopics.size - 1) Spacer(modifier = Modifier.size(10.dp))
        }
    }
}