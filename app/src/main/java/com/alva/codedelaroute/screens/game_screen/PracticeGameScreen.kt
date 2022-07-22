package com.alva.codedelaroute.screens.game_screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alva.codedelaroute.R
import com.alva.codedelaroute.models.*
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.game_screen.widgets.*
import com.alva.codedelaroute.utils.GameType
import com.alva.codedelaroute.view_models.AppConfigurationViewModel
import com.alva.codedelaroute.view_models.GameViewModel
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.alva.codedelaroute.view_models.TopicViewModel
import com.alva.codedelaroute.widgets.CustomToast
import kotlinx.coroutines.runBlocking

@Composable
fun PracticeGameScreen(
    navController: NavController,
    subTopicId: String,
) {
    val questionViewModel: QuestionViewModel = viewModel()
    val topicViewModel: TopicViewModel = viewModel()
    val gameViewModel: GameViewModel = viewModel()

    val subTopic: UITopic = remember { topicViewModel.getTopicById(subTopicId.toLong()) }
    val mainTopic: UITopic = remember { topicViewModel.getTopicById(subTopic.parentId.toLong()) }

    val subTopicProgress: UITopicProgress =
        remember { topicViewModel.getTopicProgressByTopicId(subTopicId.toLong()) }
    val mainTopicProgress: UITopicProgress =
        remember { topicViewModel.getTopicProgressByTopicId(mainTopic.id.toLong()) }

    val questions = remember { questionViewModel.getQuestionsByParentId(subTopicId.toLong()) }

    val currentQuestion = remember {
        mutableStateOf(
            gameViewModel.getNextGameQuestion(
                questionList = questions,
                questionProgressList = questionViewModel.getQuestionProgressListByTopicId(subTopicId.toLong())
            )
        )
    }

    var questionProgress =
        questionViewModel.getQuestionProgressByQuestionId(
            currentQuestion.value.id.toLong(),
            subTopicId.toLong()
        )

    val checkFinishedQuestion =
        remember {
            mutableStateOf(
                gameViewModel.isFinishQuestion(
                    currentQuestion.value,
                    questionProgress
                )
            )
        }

    val answerStatus = remember {
        mutableStateOf(
            gameViewModel.getAnswerStatus(
                question = currentQuestion.value,
                currentQuestionProgress = questionProgress,
                gameType = GameType.Practice
            )
        )
    }

    val showExplanation = remember { mutableStateOf(false) }

    val bookmark = remember { mutableStateOf(questionProgress.bookmark) }

    val context = LocalContext.current

    val dataStore = AppConfigurationViewModel(context)

    val sliderValue = dataStore.getFontSizeScale.collectAsState(1.0f) as MutableState<Float>

    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Scaffold(topBar = {
            QuestionAppBar(
                appBarTitle = mainTopic.name + ": " + subTopic.name, fontSizeScale = sliderValue
            ) {
                navController.popBackStack()
            }
        },
            backgroundColor = Color.Transparent,
            contentColor = Color.Transparent,
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxSize(),
            bottomBar = {
                QuestionBottomBar(bookmark = bookmark, onFlagClick = {}, onBookMarkClick = {
                    runBlocking {
                        bookmark.value = !bookmark.value
                        questionViewModel.saveBookmarkToRepo(
                            questionProgress.questionId.toLong(),
                            questionProgress.topicId.toLong(),
                            boolean = bookmark.value
                        )
                        if (bookmark.value) {
                            CustomToast.showToast(
                                context = context,
                                message = "Added to favorites!",
                                icon = R.drawable.check_circle,
                                textColor = R.color.toast_success_text_color,
                                toastBackgroundColor = R.color.toast_success_background_color,
                            )
                        } else {
                            CustomToast.showToast(
                                context = context,
                                message = "Removed from favorites!",
                                icon = R.drawable.check_circle,
                                textColor = R.color.toast_success_text_color,
                                toastBackgroundColor = R.color.toast_success_background_color,
                            )
                        }
                    }
                }, onNextClick = {
                    if (topicViewModel.checkFinishedTopic(topicId = subTopicId)) {
                        navController.popBackStack()
                        navController.navigate(Routes.TopicPassedScreen.name + "/$subTopicId")
                    } else {
                        if (gameViewModel.isFinishQuestion(
                                currentQuestion.value,
                                questionProgress
                            )
                        ) {
                            runBlocking {
                                currentQuestion.value = gameViewModel.getNextGameQuestion(
                                    questionList = questions,
                                    questionProgressList = questionViewModel.getQuestionProgressListByTopicId(
                                        subTopicId.toLong()
                                    )
                                )

                                questionProgress =
                                    questionViewModel.getQuestionProgressByQuestionId(
                                        currentQuestion.value.id.toLong(), subTopicId.toLong()
                                    )

                                checkFinishedQuestion.value =
                                    gameViewModel.isFinishQuestion(
                                        currentQuestion.value,
                                        questionProgress
                                    )

                                answerStatus.value = gameViewModel.getAnswerStatus(
                                    question = currentQuestion.value,
                                    currentQuestionProgress = questionProgress,
                                    gameType = GameType.Practice
                                )

                                bookmark.value = questionProgress.bookmark

                                showExplanation.value = false
                            }
                        }
                    }
                })
            }) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                QuestionProgressBar(
                    questionViewModel = questionViewModel,
                    subTopicId = subTopicId,
                    checkFinishedQuestion = checkFinishedQuestion
                )
                GamePanel(
                    modifier = Modifier.weight(1f),
                    gameType = GameType.Practice,
                    currentQuestion = currentQuestion,
                    questionList = questions,
                    answerStatus = answerStatus,
                    questionViewModel = questionViewModel,
                    gameViewModel = gameViewModel,
                    questionProgress = questionProgress,
                    subTopicProgress = subTopicProgress,
                    mainTopicProgress = mainTopicProgress,
                    checkFinishedQuestion = checkFinishedQuestion,
                    sliderValue = sliderValue,
                    showExplanation = showExplanation
                )
            }
        }
    }
}