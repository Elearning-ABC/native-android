package com.alva.codedelaroute.screens.game_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alva.codedelaroute.R
import com.alva.codedelaroute.screens.game_screen.widgets.*
import com.alva.codedelaroute.utils.GameType
import com.alva.codedelaroute.utils.ReviewQuestionProperty
import com.alva.codedelaroute.view_models.AppConfigurationViewModel
import com.alva.codedelaroute.view_models.GameViewModel
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.alva.codedelaroute.widgets.CustomToast
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.runBlocking

@Composable
fun ReviewGameScreen(
    navController: NavController,
    reviewQuestionProperty: ReviewQuestionProperty,
) {
    val questionViewModel: QuestionViewModel = viewModel()
    val gameViewModel: GameViewModel = viewModel()

    val questions = remember {
        when (reviewQuestionProperty) {
            ReviewQuestionProperty.YourFavoriteQuestions -> questionViewModel.getAllFavoriteQuestions()
            ReviewQuestionProperty.AllFamiliarQuestions -> questionViewModel.getAllFamiliarQuestionsList()
            else -> questionViewModel.getQuestionListByReviewProperty(reviewQuestionProperty)
        }
    }

    val tempQuestionProgressList = remember {
        questionViewModel.getEmptyQuestionProgressListProgress(questions)
    }

    val currentQuestion = remember {
        mutableStateOf(
            gameViewModel.getNextGameQuestion(
                questionList = questions, questionProgressList = tempQuestionProgressList
            )
        )
    }

    var tempQuestionProgress = tempQuestionProgressList.first { it.questionId == currentQuestion.value.id }


    val checkFinishedQuestion =
        remember { mutableStateOf(gameViewModel.isFinishQuestion(currentQuestion.value, tempQuestionProgress)) }

    val answerStatus = remember {
        mutableStateOf(
            gameViewModel.getAnswerStatus(
                question = currentQuestion.value, currentQuestionProgress = tempQuestionProgress, GameType.Review
            )
        )
    }

    val showExplanation = remember { mutableStateOf(false) }

    val bookmark = remember { mutableStateOf(tempQuestionProgress.bookmark) }

    val context = LocalContext.current

    val dataStore = AppConfigurationViewModel(context)

    val fontSize = dataStore.getFontSizeScale.collectAsState(1.0f)

    val sliderValue = remember { mutableStateOf(fontSize.value!!) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        ProvideWindowInsets {
            Scaffold(topBar = {
                QuestionAppBar(
                    appBarTitle = reviewQuestionProperty.name + "(${questions.size})", fontSizeScale = sliderValue
                ) {
                    navController.popBackStack()
                }
            },
                backgroundColor = Color.Transparent,
                contentColor = Color.Transparent,
                modifier = Modifier.fillMaxSize().systemBarsPadding(true),
                bottomBar = {
                    QuestionBottomBar(bookmark = bookmark, onFlagClick = {}, onBookMarkClick = {
                        runBlocking {
                            bookmark.value = !bookmark.value
                            questionViewModel.saveBookmarkToRepo(
                                tempQuestionProgress.questionId.toLong(),
                                tempQuestionProgress.topicId.toLong(),
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
                        if (tempQuestionProgressList.filter { it.boxNum == 1 }.size == questions.size) {
                            navController.popBackStack()
                        } else {
                            if (gameViewModel.isFinishQuestion(currentQuestion.value, tempQuestionProgress)) {
                                runBlocking {
                                    currentQuestion.value =
                                        gameViewModel.getNextGameQuestion(questions, tempQuestionProgressList)

                                    tempQuestionProgress =
                                        tempQuestionProgressList.first { it.questionId == currentQuestion.value.id }

                                    if (tempQuestionProgress.boxNum != 0) tempQuestionProgress.choiceSelectedIds =
                                        realmListOf()

                                    checkFinishedQuestion.value =
                                        gameViewModel.isFinishQuestion(currentQuestion.value, tempQuestionProgress)

                                    answerStatus.value = gameViewModel.getAnswerStatus(
                                        question = currentQuestion.value,
                                        currentQuestionProgress = tempQuestionProgress,
                                        gameType = GameType.Review
                                    )

                                    bookmark.value = tempQuestionProgress.bookmark
                                }
                            }
                        }
                    })
                }) { innerPadding ->
                Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    ReviewQuestionProgressBar(
                        questionViewModel = questionViewModel,
                        questionProgressList = tempQuestionProgressList,
                        checkFinishedQuestion = checkFinishedQuestion
                    )
                    GamePanel(
                        modifier = Modifier.weight(1f),
                        gameType = GameType.Review,
                        currentQuestion = currentQuestion,
                        questionList = questions,
                        answerStatus = answerStatus,
                        questionViewModel = questionViewModel,
                        gameViewModel = gameViewModel,
                        questionProgress = tempQuestionProgress,
                        checkFinishedQuestion = checkFinishedQuestion,
                        sliderValue = sliderValue,
                        showExplanation = showExplanation
                    )
                }
            }
        }
    }
}