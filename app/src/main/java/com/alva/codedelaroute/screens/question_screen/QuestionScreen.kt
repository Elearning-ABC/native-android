@file:OptIn(ExperimentalAnimationApi::class)

package com.alva.codedelaroute.screens.question_screen

import android.graphics.BitmapFactory
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alva.codedelaroute.R
import com.alva.codedelaroute.models.*
import com.alva.codedelaroute.screens.question_screen.widgets.AnswerItem
import com.alva.codedelaroute.screens.question_screen.widgets.QuestionAppBar
import com.alva.codedelaroute.screens.question_screen.widgets.QuestionBottomBar
import com.alva.codedelaroute.screens.question_screen.widgets.QuestionProgressBar
import com.alva.codedelaroute.utils.AnswerStatus
import com.alva.codedelaroute.utils.ReviewQuestionProperty
import com.alva.codedelaroute.view_models.AnswerViewModel
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.alva.codedelaroute.view_models.TopicViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.CoroutineScope
import java.io.IOException

@Composable
fun QuestionScreen(
    navController: NavController = rememberNavController(),
    subTopicId: String,
    reviewQuestionProperty: ReviewQuestionProperty,
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

    val questionProgress = questionViewModel.getQuestionProgressByQuestionId(
        currentQuestion.id.toLong(), subTopicId.toLong()
    )

    val subTopicProgress = topicViewModel.getTopicProgressByTopicId(subTopicId.toLong())

    val mainTopicProgress = topicViewModel.getTopicProgressByTopicId(mainTopic.id.toLong())

    val coroutine = rememberCoroutineScope()

    val enabled = remember {
        mutableStateOf(
            !questionViewModel.isFinishQuestion(
                currentQuestion, currentQuestionProgress = questionProgress
            )
        )
    }

    val checkFinishedQuestion =
        remember { mutableStateOf(questionViewModel.isFinishQuestion(currentQuestion, questionProgress)) }

    val answerStatus = remember {
        mutableStateOf(
            questionViewModel.getAnswerStatus(
                question = currentQuestion, currentQuestionProgress = questionProgress
            )
        )
    }

    ProvideWindowInsets {
        Surface(modifier = Modifier.systemBarsPadding(true).fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Scaffold(topBar = {
                QuestionAppBar(navController, appBarTitle = mainTopic.name + ": " + subTopic.name)
            }, backgroundColor = Color.Transparent, contentColor = Color.Transparent, bottomBar = {
                QuestionBottomBar(
                    navController = navController,
                    questionViewModel = questionViewModel,
                    question = currentQuestion,
                    questionProgress = questionProgress,
                    subTopicId = subTopicId,
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
                        Column(modifier = Modifier.fillMaxSize()) {
                            QuestionContainer(currentQuestion, answerStatus)
                            Spacer(modifier = Modifier.height(20.dp))
                            AnswerPanel(
                                currentQuestion,
                                questionViewModel,
                                questionProgress,
                                enabled,
                                answerViewModel,
                                coroutine,
                                answerStatus,
                                checkFinishedQuestion,
                                subTopicProgress,
                                mainTopicProgress,
                                currentQuestion.explanation
                            )
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
    checkFinishedQuestion: MutableState<Boolean>,
    subTopicProgress: TopicProgress,
    mainTopicProgress: TopicProgress,
    explanation: String
) {
    LazyColumn {

        items(items = question.choices, key = { item: Answer -> item.id }) {

            val visibilityState = if (!enabled.value) {
                it.isCorrect || questionProgress.choiceSelectedIds.contains(it.id)
            } else true

            val panelColorState = remember { mutableStateOf(Color.White) }

            val borderAnswerColorState = if (enabled.value) {
                Color.Transparent
            } else {
                if (it.isCorrect) Color(0xFF00C17C)
                else Color(227, 30, 24).copy(alpha = 0.52f)
            }

            val iconState = if (enabled.value
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


            AnimatedVisibility(
                visible = visibilityState, enter = scaleIn()
            ) {
                AnswerItem(
                    it,
                    borderAnswerColorState = borderAnswerColorState,
                    panelColorState = panelColorState,
                    enabled = enabled,
                    iconState = iconState,
                    explanation = explanation
                ) {
                    answerViewModel.onAnswerClickHandler(
                        answer = it,
                        question = question,
                        questionProgress = questionProgress,
                        questionViewModel = questionViewModel,
                        panelColorState = panelColorState,
                        enabled = enabled,
                        coroutine = coroutine,
                        answerStatus = answerStatus,
                        checkFinishedQuestion = checkFinishedQuestion,
                        subTopicProgress = subTopicProgress,
                        mainTopicProgress = mainTopicProgress,
                    )
                }
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
        AnswerStatus.TryAgainWithTrue -> Color(0xFF00C17C)
        AnswerStatus.TryAgainWithFalse -> Color(0xFFEBAD34)
    }

    val questionHeader = when (answerStatus.value) {
        AnswerStatus.None -> "NEW QUESTION"
        AnswerStatus.True -> "CORRECT"
        AnswerStatus.False -> "INCORRECT"
        AnswerStatus.TryAgainWithTrue -> "REVIEWING"
        AnswerStatus.TryAgainWithFalse -> "LEARNING"
    }

    val remindingText = when (answerStatus.value) {
        AnswerStatus.None -> null
        AnswerStatus.True -> "You will not see this question in a while"
        AnswerStatus.False -> "You will see this question soon"
        AnswerStatus.TryAgainWithTrue -> "You got this question last time"
        AnswerStatus.TryAgainWithFalse -> "You got this wrong last time"
    }

    val textColor = when (answerStatus.value) {
        AnswerStatus.None -> Color(0xFF4D4D4D)
        AnswerStatus.True -> Color(0xFF00C17C)
        AnswerStatus.False -> Color(227, 30, 24).copy(alpha = 0.52f)
        AnswerStatus.TryAgainWithTrue -> Color(0xFF00C17C)
        AnswerStatus.TryAgainWithFalse -> Color(0xFFEBAD34)
    }

    val alertIcon = when (answerStatus.value) {
        AnswerStatus.None -> null
        AnswerStatus.True -> painterResource(R.drawable.check_circle)
        AnswerStatus.False -> painterResource(R.drawable.alert_circle)
        AnswerStatus.TryAgainWithTrue -> painterResource(R.drawable.check_circle)
        AnswerStatus.TryAgainWithFalse -> painterResource(R.drawable.alert_circle)
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
                    verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.SpaceBetween
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
                    if (answerStatus.value != AnswerStatus.None) {
                        Text(
                            remindingText!!,
                            color = textColor,
                            fontSize = 16.sp,
                            lineHeight = 22.sp,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
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