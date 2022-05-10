package com.alva.codedelaroute.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alva.codedelaroute.R
import com.alva.codedelaroute.models.Topic
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.child_topic_list_screen.widgets.ChildTopicCard
import com.alva.codedelaroute.screens.child_topic_list_screen.widgets.ChildTopicListAppBar
import com.alva.codedelaroute.ui.theme.TitleCardColor
import com.alva.codedelaroute.view_models.TopicViewModel
import com.alva.codedelaroute.widgets.CommonAppBar
import com.alva.codedelaroute.widgets.CustomProgressBar

@Composable
fun ChildTopicListScreen(navController: NavController, parentId: String, topicViewModel: TopicViewModel = viewModel()) {
    val parentTopic = topicViewModel.getTopicById(parentId.toLong())
    val subTopics = topicViewModel.getSubTopic(parentId.toLong())

    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_2),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        ChildTopicListPanel(navController, subTopics, parentTopic)
    }
}

@Composable
fun ChildTopicList(modifier: Modifier = Modifier, navController: NavController, subTopics: MutableList<Topic>) {
    Surface(modifier = modifier, shape = RoundedCornerShape(corner = CornerSize(20.dp))) {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(subTopics.size) {
                ChildTopicCard(subTopics[it], Modifier.clickable {
                    navController.navigate(Routes.QuestionScreen.name + "/${subTopics[it].id}")
                })
            }
        }
    }
}

@Composable
fun ChildTopicListPanel(navController: NavController, subTopics: MutableList<Topic>, parentTopic: Topic) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(60.dp))
        ChildTopicListAppBar(navController, parentTopic.name)
        Box(modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)) {
            CustomProgressBar(
                Modifier.height(8.dp).clip(shape = RoundedCornerShape(4.dp)),
                Color(0xFFCAD1F5),
                Color(0xFF2B5AF5),
                0.3f,
            )
        }
        ProgressCheckTable(parentTopic.totalQuestion)
        Spacer(modifier = Modifier.height(100.dp))
        ChildTopicList(Modifier.weight(1f).fillMaxWidth(), navController, subTopics = subTopics)
    }
}

@Composable
fun ProgressCheckTable(totalQuestions: Int) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 30.dp).width(150.dp)) {
        ProgressRow("Total", totalQuestions)
        Spacer(modifier = Modifier.height(10.dp))
        ProgressRow("Answered", 0)
    }
}

@Composable
fun ProgressRow(title: String, count: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            letterSpacing = (-0.33).sp,
            color = Color(0xff5F75BB)

        )
        Text(
            count.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = (-0.33).sp,
            color = Color(0xff00259C)
        )
    }
}
