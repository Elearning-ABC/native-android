@file:OptIn(ExperimentalPagerApi::class)

package com.alva.codedelaroute.screens.question_screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alva.codedelaroute.R
import com.alva.codedelaroute.models.Answer
import com.alva.codedelaroute.models.Question
import com.alva.codedelaroute.models.QuestionProgress
import com.alva.codedelaroute.models.Topic
import com.alva.codedelaroute.screens.question_screen.widgets.AnswerItem
import com.alva.codedelaroute.screens.question_screen.widgets.QuestionBottomBar
import com.alva.codedelaroute.screens.question_screen.widgets.QuestionProgressBar
import com.alva.codedelaroute.utils.AnswerStatus
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.alva.codedelaroute.view_models.TopicViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import java.io.IOException

@Composable
fun QuestionScreen(
    navController: NavController = rememberNavController(),
    subTopicId: String,
    questionViewModel: QuestionViewModel = viewModel(),
    topicViewModel: TopicViewModel = viewModel()
) {
    val subTopic = topicViewModel.getTopicById(subTopicId.toLong())
    val questions = questionViewModel.getQuestionsByParentId(subTopicId.toLong())
    val mainTopic = topicViewModel.getTopicById(subTopic.parentId.toLong())

    val coroutine = rememberCoroutineScope()

    ProvideWindowInsets {
        Surface(modifier = Modifier.systemBarsPadding(true).fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Scaffold(topBar = {
                SmallTopAppBar(navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }, title = {
                    Text(
                        mainTopic.name + ": " + subTopic.name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        lineHeight = 24.sp
                    )
                }, actions = {
                    IconButton(onClick = {}) {
                        Image(
                            painter = painterResource(R.drawable.font_icon),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(Color.Black)
                        )
                    }
                    IconButton(onClick = {}) {
                        Image(
                            painter = painterResource(R.drawable.practice_icon),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(Color.Black)
                        )
                    }
                })
            },
                backgroundColor = Color.Transparent,
                contentColor = Color.Transparent,
                bottomBar = { QuestionBottomBar() }) { innerPadding ->
                Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    QuestionProgressBar()
                    HorizontalPager(
                        count = questions.size,
                        modifier = Modifier.weight(1f),
                    ) { index ->

                        var questionProgress = QuestionProgress()
                        questionProgress.id = System.currentTimeMillis().toString()
                        questionProgress.questionId = questions[index].id
                        questionProgress.topicId = subTopicId
                        questionProgress.lastUpdate = System.currentTimeMillis().toDouble()

                        val borderColorState = remember { mutableStateOf(Color.LightGray) }
                        val enabled = remember { mutableStateOf(true) }

                        Column(modifier = Modifier.fillMaxSize()) {
                            QuestionContainer(questions[index])
                            Spacer(modifier = Modifier.height(20.dp))
                            LazyColumn {
                                items(items = questions[index].choices) {
                                    val panelColorState = remember { mutableStateOf(Color.White) }
                                    val borderAnswerColorState = remember { mutableStateOf(Color.Transparent) }
                                    val visibilityState = remember {
                                        MutableTransitionState(
                                            !questionViewModel.checkClickedAnswerList(
                                                it.id, questionProgress
                                            )
                                        )
                                    }

                                    AnimatedVisibility(visibleState = visibilityState) {
                                        AnswerItem(
                                            it,
                                            panelColorState = panelColorState,
                                            borderAnswerColorState = borderAnswerColorState,
                                            enabled = enabled
                                        ) {
                                            coroutine.launch {
                                                questionViewModel.onAnswerClick(questions[index].id, questionProgress)
                                                if (!questionViewModel.isFinishQuestion(
                                                        questions[index], currentQuestionProgress = questionProgress
                                                    )
                                                ) {
                                                    panelColorState.value = Color(0xffF6F6F6)
                                                } else {
                                                    if (it.isCorrect) borderAnswerColorState.value = Color(0xFF00C17C)
                                                    else borderAnswerColorState.value =
                                                        Color(227, 30, 24).copy(alpha = 0.52f)
                                                    enabled.value = false
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun QuestionContainer(question: Question) {
    val context = LocalContext.current
    val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }

    try {
        with(context.assets.open("images/${question.image}")) {
            imageBitmap.value = BitmapFactory.decodeStream(this).asImageBitmap()
        }
    } catch (e: IOException) {
        imageBitmap.value = null
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.padding(16.dp).fillMaxWidth().clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                .border(
                    BorderStroke(
                        1.dp, color = Color.LightGray
                    ), RoundedCornerShape(corner = CornerSize(16.dp))
                )
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(question.text, modifier = Modifier.weight(1f), fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    imageBitmap.value?.apply {
                        Image(
                            bitmap = this, contentDescription = null, modifier = Modifier.size(80.dp)
                        )
                    }
                }
//                if (check == QuestionCheck.True) Spacer(modifier = Modifier.height(10.dp))
//                if (check == QuestionCheck.True) Text(
//                    "You will not see this question in a while",
//                    color = Color(0xFF00C17C),
//                    fontSize = 16.sp,
//                    lineHeight = 22.sp
//                )
            }
        }
        Surface(
            modifier = Modifier.padding(start = 32.dp).background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(226, 228, 238), Color(235, 235, 240)
                    )
                )
            ), color = Color.Transparent
        ) {
            Text(
                "NEW QUESTION",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                lineHeight = 22.sp,
                color = Color(0xFF4D4D4D),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}