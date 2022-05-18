package com.alva.codedelaroute.screens.practice_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.practice_screen.widgets.TopicCard
import com.alva.codedelaroute.view_models.TopicViewModel

@Composable
fun PracticeScreen(
    navController: NavController, topicViewModel: TopicViewModel = viewModel(
        viewModelStoreOwner = TopicViewModel.viewModelStoreOwner, key = TopicViewModel.key
    )
) {
    val mainTopics = topicViewModel.getMainTopic()

    LazyColumn {
        items(mainTopics.size) {
            TopicCard(mainTopics[it], Modifier.clickable {
                navController.navigate(Routes.ChildTopicListScreen.name + "/${mainTopics[it].id}")
            })
            if (it == mainTopics.size - 1) Spacer(modifier = Modifier.size(10.dp))
        }

    }
}