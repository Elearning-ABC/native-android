package com.alva.codedelaroute.screens.practice_screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alva.codedelaroute.models.*
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.home_screen.widgets.TopicCard
import com.alva.codedelaroute.utils.byteKey
import com.alva.codedelaroute.view_models.TopicViewModel
import io.realm.Realm
import io.realm.RealmConfiguration

@Composable
fun PracticeScreen(navController: NavController, topicViewModel: TopicViewModel = viewModel()) {
    topicViewModel.getMainTopic()
    val mainTopics = topicViewModel.mainTopics

    LazyColumn{
        items(mainTopics.size) {
            TopicCard(mainTopics[it], Modifier.clickable {
                navController.navigate(Routes.ChildTopicListScreen.name)
            })
        }
    }
}