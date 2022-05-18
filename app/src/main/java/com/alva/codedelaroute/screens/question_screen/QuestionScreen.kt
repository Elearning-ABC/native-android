@file:OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)

package com.alva.codedelaroute.screens.question_screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
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
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.question_screen.widgets.AnswerItem
import com.alva.codedelaroute.screens.question_screen.widgets.QuestionAppBar
import com.alva.codedelaroute.screens.question_screen.widgets.QuestionBottomBar
import com.alva.codedelaroute.screens.question_screen.widgets.QuestionProgressBar
import com.alva.codedelaroute.utils.AnswerStatus
import com.alva.codedelaroute.view_models.AnswerViewModel
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.alva.codedelaroute.view_models.TopicViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.pager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import java.io.IOException

@Composable
fun QuestionScreen(
    navController: NavController = rememberNavController(),
    subTopicId: String,
    questionViewModel: QuestionViewModel = viewModel(
        viewModelStoreOwner = QuestionViewModel.viewModelStoreOwner, key = QuestionViewModel.key
    ),
    topicViewModel: TopicViewModel = viewModel(
        viewModelStoreOwner = TopicViewModel.viewModelStoreOwner, key = TopicViewModel.key
    ),
    answerViewModel: AnswerViewModel = viewModel(
        viewModelStoreOwner = AnswerViewModel.viewModelStoreOwner, key = AnswerViewModel.key
    )
) {
    val subTopic = topicViewModel.getTopicById(subTopicId.toLong())
    val questions = questionViewModel.getQuestionsByParentId(subTopicId.toLong())
    val mainTopic = topicViewModel.getTopicById(subTopic.parentId.toLong())

    val currentQuestion = questionViewModel.goToNextQuestion(questions, subTopicId.toLong())

    if (currentQuestion == null) {
        navController.popBackStack()
    }

    val questionProgress = questionViewModel.getQuestionProgressByQuestionId(
        currentQuestion!!.id.toLong(), subTopicId.toLong()
    )

    val coroutine = rememberCoroutineScope()

    val checkFinishedQuestion =
        remember { mutableStateOf(questionViewModel.isFinishQuestion(currentQuestion, questionProgress)) }

    ProvideWindowInsets {
        Surface(modifier = Modifier.systemBarsPadding(true).fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Scaffold(topBar = {
                QuestionAppBar(navController, mainTopic, subTopic)
            }, backgroundColor = Color.Transparent, contentColor = Color.Transparent, bottomBar = {
                QuestionBottomBar(
                    navController = navController,
                    questionViewModel = questionViewModel,
                    question = currentQuestion,
                    questionProgress = questionProgress,
                    subTopicId = subTopicId,
                    questionList = questions
                )
            }) { innerPadding ->
                Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    QuestionProgressBar(
                        questionViewModel = questionViewModel,
                        subTopicId = subTopicId,
                        checkFinishedQuestion = checkFinishedQuestion
                    )
                    Box(
                        modifier = Modifier.weight(1f),
                    ) {

                        val answerStatus = remember {
                            mutableStateOf(
                                if (!questionViewModel.isFinishQuestion(
                                        currentQuestion, currentQuestionProgress = questionProgress
                                    )
                                ) {
                                    AnswerStatus.None
                                } else {
                                    if (questionProgress.choiceSelectedIds.any { answerId -> !currentQuestion.choices.filter { it.id == answerId }[0].isCorrect }) AnswerStatus.False
                                    else AnswerStatus.True
                                }
                            )
                        }


                        val enabled = remember {
                            mutableStateOf(
                                !questionViewModel.isFinishQuestion(
                                    currentQuestion, currentQuestionProgress = questionProgress
                                )
                            )
                        }

                        Column(modifier = Modifier.fillMaxSize()) {
                            QuestionContainer(currentQuestion, answerStatus)
                            Spacer(modifier = Modifier.height(20.dp))
                            Crossfade(
                                targetState = enabled, animationSpec = tween(
                                    durationMillis = 300, delayMillis = 0, easing = LinearOutSlowInEasing
                                )
                            ) {
                                if (it.value) {
                                    AnswerPanel(
                                        currentQuestion,
                                        questionViewModel,
                                        questionProgress,
                                        enabled,
                                        answerViewModel,
                                        coroutine,
                                        answerStatus,
                                        checkFinishedQuestion
                                    )
                                } else FinishedAnswerPanel(
                                    currentQuestion, questionViewModel, questionProgress, enabled
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnswerPanel(
    question: Question,
    questionViewModel: QuestionViewModel,
    questionProgress: QuestionProgress,
    enabled: MutableState<Boolean>,
    answerViewModel: AnswerViewModel,
    coroutine: CoroutineScope,
    answerStatus: MutableState<AnswerStatus>,
    checkFinishedQuestion: MutableState<Boolean>
) {
    LazyColumn {
        items(items = question.choices, key = { item: Answer -> item.id }) {
            val panelColorState = remember { mutableStateOf(Color.White) }

            AnswerItem(
                it,
                panelColorState = panelColorState,
                enabled = enabled,
            ) {
                answerViewModel.onAnswerClickHandler(
                    answer = it,
                    question = question,
                    questionProgress = questionProgress,
                    questionViewModel = questionViewModel,
                    panelColorState = panelColorState,
                    enabled = enabled,
                    coroutine = coroutine,
                    answerStatus = answerStatus
                )
                checkFinishedQuestion.value = true
            }
        }
    }
}

@Composable
fun FinishedAnswerPanel(
    question: Question,
    questionViewModel: QuestionViewModel,
    questionProgress: QuestionProgress,
    enabled: MutableState<Boolean>,
) {
    LazyColumn {
        items(items = question.choices, key = { item: Answer -> item.id }) {
            val borderAnswerColorState = remember {
                mutableStateOf(
                    if (!questionViewModel.isFinishQuestion(
                            question, currentQuestionProgress = questionProgress
                        )
                    ) {
                        Color.Transparent
                    } else {
                        if (it.isCorrect) Color(0xFF00C17C)
                        else Color(227, 30, 24).copy(alpha = 0.52f)
                    }
                )
            }
            val iconState = remember {
                mutableStateOf(
                    if (!questionViewModel.isFinishQuestion(
                            question, currentQuestionProgress = questionProgress
                        )
                    ) {
                        mapOf(
                            "icon" to Icons.Default.Remove, "tint" to Color(0xFF4D4D4D)
                        )
                    } else {
                        if (it.isCorrect) mapOf(
                            "icon" to Icons.Default.Check, "tint" to Color(0xFF00C17C)

                        )
                        else mapOf(
                            "icon" to Icons.Default.Close, "tint" to Color(227, 30, 24).copy(alpha = 0.52f)
                        )

                    }
                )
            }

            val visibilityState = remember {
                mutableStateOf(
                    if (!questionViewModel.isFinishQuestion(
                            question, currentQuestionProgress = questionProgress
                        )
                    ) true
                    else {
                        !(!questionViewModel.checkClickedAnswerList(
                            it.id, questionProgress
                        ) && !it.isCorrect)
                    }
                )
            }

            AnimatedVisibility(
                visible = visibilityState.value, enter = scaleIn()
            ) {
                AnswerItem(
                    it, borderAnswerColorState = borderAnswerColorState, enabled = enabled, iconState = iconState
                )
            }
        }
    }
}

@Composable
fun QuestionContainer(question: Question, answerStatus: MutableState<AnswerStatus>) {
    val context = LocalContext.current
    val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }

    try {
        with(context.assets.open("images/${question.image}")) {
            imageBitmap.value = BitmapFactory.decodeStream(this).asImageBitmap()
        }
    } catch (e: IOException) {
        imageBitmap.value = null
    }

    val borderColor = when (answerStatus.value) {
        AnswerStatus.None -> Color.LightGray
        AnswerStatus.True -> Color(0xFF00C17C)
        AnswerStatus.False -> Color(227, 30, 24).copy(alpha = 0.52f)
    }

    val questionHeader = when (answerStatus.value) {
        AnswerStatus.None -> "NEW QUESTION"
        AnswerStatus.True -> "CORRECT"
        AnswerStatus.False -> "INCORRECT"
    }

    val remindingText = when (answerStatus.value) {
        AnswerStatus.None -> ""
        AnswerStatus.True -> "You will not see this question in a while"
        AnswerStatus.False -> "You will see this question soon"
    }

    val textColor = when (answerStatus.value) {
        AnswerStatus.None -> Color(0xFF4D4D4D)
        AnswerStatus.True -> Color(0xFF00C17C)
        AnswerStatus.False -> Color(227, 30, 24).copy(alpha = 0.52f)
    }

    val alertIcon = when (answerStatus.value) {
        AnswerStatus.None -> null
        AnswerStatus.True -> painterResource(R.drawable.check_circle)
        AnswerStatus.False -> painterResource(R.drawable.alert_circle)
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.padding(16.dp).fillMaxWidth().clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                .border(
                    BorderStroke(
                        1.dp, color = borderColor
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
                Row(modifier = Modifier.padding(top = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                    if (answerStatus.value != AnswerStatus.None) Icon(
                        alertIcon as Painter,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = textColor
                    )
                    Text(
                        remindingText,
                        color = textColor,
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
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
                questionHeader,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                lineHeight = 22.sp,
                color = textColor,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}