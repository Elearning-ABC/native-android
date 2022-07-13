package com.alva.codedelaroute.screens.game_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.game_screen.widgets.*
import com.alva.codedelaroute.utils.GameType
import com.alva.codedelaroute.utils.TestSetting
import com.alva.codedelaroute.utils.TestStatus
import com.alva.codedelaroute.view_models.AppConfigurationViewModel
import com.alva.codedelaroute.view_models.GameViewModel
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.alva.codedelaroute.view_models.TestInfoViewModel
import com.alva.codedelaroute.widgets.CustomAlertDialog
import com.alva.codedelaroute.widgets.CustomToast
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.runBlocking

@Composable
fun TestGameScreen(
    navController: NavController, testInfoId: String, testProgressId: String
) {
    val questionViewModel: QuestionViewModel = viewModel()
    val testInfoViewModel: TestInfoViewModel = viewModel()
    val gameViewModel: GameViewModel = viewModel()

    val testInfo = testInfoViewModel.getTestInfoById(testInfoId)
    val testProgress = remember { testInfoViewModel.getTestProgressById(testProgressId) }

    val questions = remember {
        testInfoViewModel.getQuestionListByTestInfo(testInfo = testInfo)
    }

    val tempQuestionProgressList = remember {
        questionViewModel.getQuestionProgressForTest(questions, testProgress)
    }

    val currentQuestion = remember {
        mutableStateOf(
            gameViewModel.getTestGameQuestion(
                questionList = questions, testProgress = testProgress, testInfoViewModel = testInfoViewModel
            )
        )
    }

    var tempQuestionProgress = tempQuestionProgressList.first { it.questionId == currentQuestion.value.id }


    val checkFinishedQuestion = remember {
        mutableStateOf(
            gameViewModel.isFinishQuestion(
                currentQuestion.value,
                tempQuestionProgress,
            )
        )
    }

    val answerStatus = remember {
        mutableStateOf(
            gameViewModel.getAnswerStatus(
                question = currentQuestion.value,
                currentQuestionProgress = tempQuestionProgress,
                gameType = GameType.Test
            )
        )
    }

    val showExplanation = remember { mutableStateOf(false) }

    val bookmark = remember { mutableStateOf(tempQuestionProgress.bookmark) }

    val context = LocalContext.current

    val dataStore = AppConfigurationViewModel(context)

    val fontSize = dataStore.getFontSizeScale.collectAsState(1.0f)

    val sliderValue = remember { mutableStateOf(fontSize.value!!) }

    val testTimeUsed = remember { if (testProgress.testSettingId == TestSetting.Medium) testProgress.time else 0 }

    val countDownTime = remember {
        mutableStateOf(
            when (testProgress.testSettingId) {
                TestSetting.Easy -> 0
                TestSetting.Medium -> testInfo.duration
                TestSetting.Hardest -> testInfo.duration / testInfo.totalQuestion
            }
        )

    }

    val openSubmitDialog = remember { mutableStateOf(false) }

    val choiceListState = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        if (choiceListState.isEmpty()) {
            choiceListState.addAll(tempQuestionProgress.choiceSelectedIds)
        }
    }

    LaunchedEffect(Unit) {
        testProgress.status = TestStatus.Playing
        testInfoViewModel.updateTestProgressStatus(testProgressId, status = TestStatus.Playing)
    }

    if (openSubmitDialog.value) {
        CustomAlertDialog(
            openSubmitDialog,
            title = "SUBMIT TEST",
            description = "You answered ${testInfoViewModel.getTotalTestQuestionsAnsweredCount(testProgress)} of ${testProgress.totalQuestion} questions on this test",
            buttonAcceptTitle = "Submit",
            buttonCancelTitle = "Continue",
            buttonAcceptClick = {
                runBlocking {
                    navController.popBackStack()
                    navController.navigate(Routes.TestDoneScreen.name + "/$testProgressId")
                    openSubmitDialog.value = false

                }
            },
            buttonCancelClick = { openSubmitDialog.value = false },
        )
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        ProvideWindowInsets {
            Scaffold(topBar = {
                TestQuestionAppBar(appBarTitle = when (testProgress.testSettingId) {
                    TestSetting.Easy -> "Easy Test"
                    TestSetting.Medium -> "Medium Test"
                    TestSetting.Hardest -> "Hardest Test"
                }, fontSizeScale = sliderValue, onBackPress = { navController.popBackStack() }, onSubmit = {
                    openSubmitDialog.value = true
                })
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
                        if (testProgress.answeredQuestion.size == questions.size) {
                            navController.popBackStack()
                            navController.navigate(Routes.TestDoneScreen.name + "/$testProgressId")
                        } else {
                            runBlocking {
                                gameViewModel.onNextTestClick(
                                    questionList = questions,
                                    testInfoViewModel = testInfoViewModel,
                                    testProgress = testProgress
                                )

                                currentQuestion.value = gameViewModel.getTestGameQuestion(
                                    questionList = questions,
                                    testProgress = testProgress,
                                    testInfoViewModel = testInfoViewModel
                                )

                                tempQuestionProgress =
                                    tempQuestionProgressList.first { it.questionId == currentQuestion.value.id }

                                choiceListState.clear()

                                checkFinishedQuestion.value = gameViewModel.isFinishQuestion(
                                    currentQuestion.value,
                                    tempQuestionProgress,
                                )

                                answerStatus.value = gameViewModel.getAnswerStatus(
                                    question = currentQuestion.value,
                                    currentQuestionProgress = tempQuestionProgress,
                                    gameType = GameType.Test
                                )

                                bookmark.value = tempQuestionProgress.bookmark

                                if (testProgress.testSettingId == TestSetting.Hardest) {
                                    countDownTime.value = testInfo.duration / testInfo.totalQuestion
                                }
                            }
                        }
                    })
                }) { innerPadding ->
                Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    TestQuestionProgressBar(testProgress, checkFinishedQuestion = checkFinishedQuestion)
                    if (testProgress.testSettingId != TestSetting.Easy) TestClockPanel(
                        timeUsed = testTimeUsed,
                        duration = countDownTime.value,
                        countDownTime = countDownTime,
                        testProgressId = testProgressId
                    ) {
                        if (testProgress.testSettingId == TestSetting.Medium) {
                            navController.popBackStack()
                            navController.navigate(Routes.TestDoneScreen.name + "/$testProgressId")
                        } else if (testProgress.testSettingId == TestSetting.Hardest) {
                            if (testProgress.answeredQuestion.size == questions.size) {
                                navController.popBackStack()
                                navController.navigate(Routes.TestDoneScreen.name + "/$testProgressId")
                            } else {
                                runBlocking {
                                    gameViewModel.onNextTestClick(
                                        questionList = questions,
                                        testInfoViewModel = testInfoViewModel,
                                        testProgress = testProgress
                                    )

                                    currentQuestion.value = gameViewModel.getTestGameQuestion(
                                        questionList = questions,
                                        testProgress = testProgress,
                                        testInfoViewModel = testInfoViewModel
                                    )

                                    tempQuestionProgress =
                                        tempQuestionProgressList.first { it.questionId == currentQuestion.value.id }

                                    choiceListState.clear()

                                    checkFinishedQuestion.value = gameViewModel.isFinishQuestion(
                                        currentQuestion.value,
                                        tempQuestionProgress,
                                    )

                                    answerStatus.value = gameViewModel.getAnswerStatus(
                                        question = currentQuestion.value,
                                        currentQuestionProgress = tempQuestionProgress,
                                        gameType = GameType.Test
                                    )

                                    bookmark.value = tempQuestionProgress.bookmark

                                    countDownTime.value = testInfo.duration / testInfo.totalQuestion
                                }
                            }
                        }
                    }
                    GamePanel(
                        modifier = Modifier.weight(1f),
                        gameType = GameType.Test,
                        currentQuestion = currentQuestion,
                        questionList = questions,
                        answerStatus = answerStatus,
                        questionViewModel = questionViewModel,
                        gameViewModel = gameViewModel,
                        questionProgress = tempQuestionProgress,
                        checkFinishedQuestion = checkFinishedQuestion,
                        sliderValue = sliderValue,
                        showExplanation = showExplanation,
                        choiceListState = choiceListState,
                        testProgress = testProgress,
                        testInfoViewModel = testInfoViewModel,
                        testSettingId = testProgress.testSettingId
                    )
                }
            }
        }
    }
}