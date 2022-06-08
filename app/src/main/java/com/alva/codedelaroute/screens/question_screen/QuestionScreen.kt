package com.alva.codedelaroute.screens.question_screen

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alva.codedelaroute.R
import com.alva.codedelaroute.models.*
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.question_screen.widgets.*
import com.alva.codedelaroute.view_models.AnswerViewModel
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.alva.codedelaroute.view_models.TopicViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.runBlocking

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
    val subTopic: Topic = topicViewModel.getTopicById(subTopicId.toLong())
    val mainTopic: Topic = topicViewModel.getTopicById(subTopic.parentId.toLong())
    val subTopicProgress: TopicProgress = topicViewModel.getTopicProgressByTopicId(subTopicId.toLong())
    val mainTopicProgress: TopicProgress = topicViewModel.getTopicProgressByTopicId(mainTopic.id.toLong())

    val questions = questionViewModel.getQuestionsByParentId(subTopicId.toLong())

    val currentQuestion =
        remember { mutableStateOf(questionViewModel.goToNextQuestion(questions, subTopicId.toLong())) }

    var questionProgress =
        questionViewModel.getQuestionProgressByQuestionId(currentQuestion.value.id.toLong(), subTopicId.toLong())

    val coroutine = rememberCoroutineScope()

    val enabled = remember {
        mutableStateOf(
            !questionViewModel.isFinishQuestion(
                currentQuestion.value, currentQuestionProgress = questionProgress
            )
        )
    }

    val checkFinishedQuestion =
        remember { mutableStateOf(questionViewModel.isFinishQuestion(currentQuestion.value, questionProgress)) }

    val answerStatus = remember {
        mutableStateOf(
            questionViewModel.getAnswerStatus(
                question = currentQuestion.value, currentQuestionProgress = questionProgress
            )
        )
    }

    val bookmark = remember { mutableStateOf(questionProgress.bookmark) }

    val context = LocalContext.current

    ProvideWindowInsets {
        Surface(modifier = Modifier.systemBarsPadding(true).fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Scaffold(topBar = {
                QuestionAppBar(
                    appBarTitle = mainTopic.name + ": " + subTopic.name

                ) {
                    navController.popBackStack()
                }
            }, backgroundColor = Color.Transparent, contentColor = Color.Transparent, bottomBar = {
                QuestionBottomBar(bookmark = bookmark, onFlagClick = {}, onBookMarkClick = {
                    runBlocking {
                        bookmark.value = !bookmark.value
                        questionProgress = questionViewModel.getQuestionProgressByQuestionId(
                            currentQuestion.value.id.toLong(), subTopicId.toLong()
                        )
                        questionProgress.bookmark = !questionProgress.bookmark
                        questionViewModel.addOrUpdateQuestionProgressToRepo(questionProgress)
                        if (bookmark.value) {
                            Toast.makeText(context, "Added to your favorite", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Remove from your favorite", Toast.LENGTH_SHORT).show()
                        }
                    }
                }, onNextClick = {
                    if (topicViewModel.checkFinishedTopic(topicId = subTopicId)) {
                        navController.popBackStack()
                        navController.navigate(Routes.FinishTopicScreen.name + "/$subTopicId")
                    } else {
                        if (questionViewModel.isFinishQuestion(currentQuestion.value, questionProgress)) {
                            runBlocking {
                                currentQuestion.value =
                                    questionViewModel.goToNextQuestion(questions, subTopicId.toLong())

                                questionProgress = questionViewModel.getQuestionProgressByQuestionId(
                                    currentQuestion.value.id.toLong(), subTopicId.toLong()
                                )

                                enabled.value = !questionViewModel.isFinishQuestion(
                                    currentQuestion.value, currentQuestionProgress = questionProgress
                                )

                                checkFinishedQuestion.value =
                                    questionViewModel.isFinishQuestion(currentQuestion.value, questionProgress)

                                answerStatus.value = questionViewModel.getAnswerStatus(
                                    question = currentQuestion.value, currentQuestionProgress = questionProgress
                                )

                                bookmark.value = questionProgress.bookmark
                            }
//                            navController.popBackStack()
//                            navController.navigate(Routes.QuestionScreen.name + "/$subTopicId/${ReviewQuestionProperty.None.name}")
                        }
                    }
                })
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
                            QuestionContainer(currentQuestion.value, answerStatus)
                            Spacer(modifier = Modifier.height(20.dp))
                            AnswerPanel(
                                currentQuestion.value,
                                questionViewModel,
                                questionProgress,
                                enabled,
                                answerViewModel,
                                coroutine,
                                answerStatus,
                                checkFinishedQuestion,
                                subTopicProgress,
                                mainTopicProgress,
                                currentQuestion.value.explanation
                            )
                        }
                    }
                }
            }
        }
    }
}