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
import com.alva.codedelaroute.models.UITopicProgress
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.home_screen.tabs.practice_tab.widgets.PracticeCard
import com.alva.codedelaroute.view_models.TopicViewModel

@Composable
fun PracticeTab(
    navController: NavController, topicViewModel: TopicViewModel = viewModel()
) {
    val mainTopics = topicViewModel.mainTopics

    val mainTopicProgressList = mutableListOf<UITopicProgress>()

    for (topic in mainTopics) {
        mainTopicProgressList.add(topicViewModel.getTopicProgressByTopicId(topic.id.toLong()))
    }

    LazyColumn {
        items(mainTopics.size) {
            PracticeCard(mainTopics[it], Modifier.clickable {
                navController.navigate(Routes.TopicScreen.name + "/${mainTopics[it].id}")
            }, mainTopicProgressList[it])
            if (it == mainTopics.size - 1) Spacer(modifier = Modifier.size(10.dp))
        }
    }
}