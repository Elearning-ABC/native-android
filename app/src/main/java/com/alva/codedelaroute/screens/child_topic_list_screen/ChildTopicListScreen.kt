package com.alva.codedelaroute.screens.child_topic_list_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alva.codedelaroute.R
import com.alva.codedelaroute.models.Topic
import com.alva.codedelaroute.models.TopicProgress
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.child_topic_list_screen.widgets.ChildTopicCard
import com.alva.codedelaroute.screens.child_topic_list_screen.widgets.ChildTopicListAppBar
import com.alva.codedelaroute.screens.child_topic_list_screen.widgets.CustomAlertDialog
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.alva.codedelaroute.view_models.TopicViewModel
import com.alva.codedelaroute.widgets.CustomProgressBar
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun ChildTopicListScreen(
    navController: NavController, parentId: String, topicViewModel: TopicViewModel = viewModel(
        viewModelStoreOwner = TopicViewModel.viewModelStoreOwner,
        key = TopicViewModel.key
    ),
    questionViewModel: QuestionViewModel = viewModel(
        viewModelStoreOwner = QuestionViewModel.viewModelStoreOwner,
        key = QuestionViewModel.key
    )
) {
    val parentTopic = topicViewModel.getTopicById(parentId.toLong())
    val subTopics = topicViewModel.getSubTopic(parentId.toLong())

    val subTopicProgressList = mutableListOf<TopicProgress>()
    for (subTopic in subTopics) {
        subTopicProgressList.add(topicViewModel.getTopicProgressByTopicId(subTopic.id.toLong()))
    }

    val mainTopicProgress = topicViewModel.getTopicProgressByTopicId(parentTopic.id.toLong())

    val openDialog = remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_2),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        ChildTopicListPanel(
            navController,
            subTopics,
            parentTopic,
            subTopicProgressList,
            mainTopicProgress,
            questionViewModel,
            openDialog
        )
        if (openDialog.value) {
            CustomAlertDialog(openDialog)
        }
    }
}

@Composable
fun ChildTopicList(
    modifier: Modifier = Modifier,
    navController: NavController,
    subTopics: MutableList<Topic>,
    subTopicProgressList: MutableList<TopicProgress>,
    questionViewModel: QuestionViewModel,
    openDialog: MutableState<Boolean>
) {
    Surface(modifier = modifier, shape = RoundedCornerShape(corner = CornerSize(20.dp))) {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(subTopics.size) {
                ChildTopicCard(subTopics[it], Modifier.clickable {
                    if (!questionViewModel.checkFinishedTopic(
                            questionViewModel.getQuestionsByParentId(subTopics[it].id.toLong()),
                            subTopics[it].id.toLong()
                        )
                    ) {
                        navController.navigate(Routes.QuestionScreen.name + "/${subTopics[it].id}")
                    } else {
                        openDialog.value = true
                    }
                }, subTopicProgressList[it])
            }
        }
    }
}

@Composable
fun ChildTopicListPanel(
    navController: NavController,
    subTopics: MutableList<Topic>,
    parentTopic: Topic,
    subTopicProgressList: MutableList<TopicProgress>,
    mainTopicProgress: TopicProgress,
    questionViewModel: QuestionViewModel,
    openDialog: MutableState<Boolean>
) {
    val percentage = mainTopicProgress.correctNumber.toFloat() / mainTopicProgress.totalQuestionNumber

    ProvideWindowInsets {
        Scaffold(
            modifier = Modifier.fillMaxSize().systemBarsPadding(true),
            backgroundColor = Color.Transparent,
            topBar = { ChildTopicListAppBar(navController, parentTopic.name) }) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)) {
                    CustomProgressBar(
                        Modifier.height(8.dp).clip(shape = RoundedCornerShape(4.dp)),
                        Color(0xFFCAD1F5),
                        Color(0xFF2B5AF5),
                        percentage,
                    )
                }
                ProgressCheckTable(mainTopicProgress)
                Spacer(modifier = Modifier.height(100.dp))
                ChildTopicList(
                    Modifier.weight(1f).fillMaxWidth(),
                    navController,
                    subTopics = subTopics,
                    subTopicProgressList = subTopicProgressList,
                    questionViewModel = questionViewModel,
                    openDialog = openDialog
                )
            }
        }
    }
}

@Composable
fun ProgressCheckTable(mainTopicProgress: TopicProgress) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 30.dp).width(150.dp)) {
        ProgressRow("Total", mainTopicProgress.totalQuestionNumber)
        Spacer(modifier = Modifier.height(10.dp))
        ProgressRow("Answered", mainTopicProgress.correctNumber)
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
