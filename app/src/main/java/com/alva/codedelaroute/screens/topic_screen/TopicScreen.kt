package com.alva.codedelaroute.screens.topic_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alva.codedelaroute.R
import com.alva.codedelaroute.models.UITopic
import com.alva.codedelaroute.models.UITopicProgress
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.topic_screen.widgets.TopicCard
import com.alva.codedelaroute.screens.topic_screen.widgets.TopicAppBar
import com.alva.codedelaroute.widgets.CustomAlertDialog
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.alva.codedelaroute.view_models.TopicViewModel
import com.alva.codedelaroute.widgets.CustomLinearProgressBar
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.runBlocking

@Composable
fun TopicScreen(
    navController: NavController,
    parentId: String,
    topicViewModel: TopicViewModel = viewModel(),
    questionViewModel: QuestionViewModel = viewModel(),
) {
    val parentTopic = topicViewModel.getTopicById(parentId.toLong())

    val subTopics = topicViewModel.getSubTopic(parentId.toLong())

    val subTopicProgressList = mutableListOf<UITopicProgress>()

    for (subTopic in subTopics) {
        subTopicProgressList.add(topicViewModel.getTopicProgressByTopicId(subTopic.id.toLong()))
    }

    val mainTopicProgress = topicViewModel.getTopicProgressByTopicId(parentTopic.id.toLong())

    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_2),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        TopicListPanel(
            navController = navController,
            subTopics = subTopics,
            parentTopic = parentTopic,
            subTopicProgressList = subTopicProgressList,
            mainTopicProgress = mainTopicProgress,
            topicViewModel = topicViewModel,
            questionViewModel = questionViewModel
        )
    }
}

@Composable
fun TopicListPanel(
    navController: NavController,
    subTopics: MutableList<UITopic>,
    parentTopic: UITopic,
    subTopicProgressList: MutableList<UITopicProgress>,
    mainTopicProgress: UITopicProgress,
    topicViewModel: TopicViewModel,
    questionViewModel: QuestionViewModel
) {
    val percentage = mainTopicProgress.correctNumber.toFloat() / mainTopicProgress.totalQuestionNumber

    ProvideWindowInsets {
        Scaffold(
            modifier = Modifier.fillMaxSize().systemBarsPadding(true),
            backgroundColor = Color.Transparent,
            topBar = { TopicAppBar(navController, parentTopic.name) }) {
            Column(modifier = Modifier.padding(it).fillMaxSize()) {
                Box(modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)) {
                    CustomLinearProgressBar(
                        Modifier.height(8.dp).clip(shape = RoundedCornerShape(4.dp)),
                        Color(0xFFCAD1F5),
                        Color(0xFF002395),
                        percentage,
                    )
                }
                TopicProgressPanel(mainTopicProgress)
                Spacer(modifier = Modifier.height(100.dp))
                TopicList(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    navController = navController,
                    mainTopicId = parentTopic.id,
                    subTopics = subTopics,
                    subTopicProgressList = subTopicProgressList,
                    topicViewModel = topicViewModel,
                    questionViewModel = questionViewModel
                )
            }
        }
    }
}

@Composable
fun TopicList(
    modifier: Modifier = Modifier,
    navController: NavController,
    mainTopicId: String,
    subTopics: MutableList<UITopic>,
    subTopicProgressList: MutableList<UITopicProgress>,
    topicViewModel: TopicViewModel,
    questionViewModel: QuestionViewModel
) {
    Surface(modifier = modifier, shape = RoundedCornerShape(corner = CornerSize(20.dp))) {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(subTopics.size) {
                val openDialog = remember { mutableStateOf(false) }

                if (openDialog.value) {
                    CustomAlertDialog(
                        openDialog,
                        title = "LEARN AGAIN",
                        description = "Do you want to reset all progress of this practice?",
                        buttonAcceptTitle = "Reset",
                        buttonCancelTitle = "Not Now",
                        buttonAcceptClick = {
                            runBlocking {
                                topicViewModel.clearSubTopicProgressData(
                                    subTopicId = subTopics[it].id.toLong(),
                                    parentTopicId = mainTopicId.toLong()
                                )
                                questionViewModel.clearQuestionProgressData(
                                    subTopics[it].id.toLong()
                                )
                                navController.navigate(Routes.PracticeGameScreen.name + "/${subTopics[it].id.toLong()}")
                            }
                            openDialog.value = false
                        },
                        buttonCancelClick = { openDialog.value = false },
                    )
                }

                TopicCard(subTopics[it], Modifier.clickable {
                    if (!topicViewModel.checkFinishedTopic(subTopics[it].id)) {
                        navController.navigate(Routes.PracticeGameScreen.name + "/${subTopics[it].id}")
                    } else {
                        openDialog.value = true
                    }
                }, subTopicProgressList[it])
            }
        }
    }
}

@Composable
fun TopicProgressPanel(mainTopicProgress: UITopicProgress) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 30.dp).width(150.dp)) {
        TopicProgressRow("Total", mainTopicProgress.totalQuestionNumber)
        Spacer(modifier = Modifier.height(10.dp))
        TopicProgressRow("Answered", mainTopicProgress.correctNumber)
    }
}

@Composable
fun TopicProgressRow(title: String, count: Int) {
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
