package com.alva.codedelaroute.screens.question_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alva.codedelaroute.R
import com.alva.codedelaroute.models.QuestionProgress
import com.alva.codedelaroute.screens.question_screen.widgets.*
import com.alva.codedelaroute.utils.ReviewQuestionProperty
import com.alva.codedelaroute.view_models.AnswerViewModel
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.runBlocking

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun ReviewQuestionScreen(
    navController: NavController = rememberNavController(),
    reviewQuestionProperty: ReviewQuestionProperty,
    questionViewModel: QuestionViewModel = viewModel(
        viewModelStoreOwner = LocalViewModelStoreOwner.current!!
    ),
    answerViewModel: AnswerViewModel = viewModel(
        viewModelStoreOwner = LocalViewModelStoreOwner.current!!
    )
) {
    val questions = remember {
        when (reviewQuestionProperty) {
            ReviewQuestionProperty.YourFavoriteQuestions -> questionViewModel.getAllFavoriteQuestions()
            ReviewQuestionProperty.AllFamiliarQuestions -> questionViewModel.getAllAnsweredQuestion()
            else -> questionViewModel.getQuestionListByReviewProperty(reviewQuestionProperty)
        }
    }

    val tempQuestionProgressList = remember {
        questionViewModel.getEmptyQuestionProgressListProgress(questions)
    }

    val currentQuestion =
        remember { mutableStateOf(questionViewModel.goToNextReviewQuestion(questions, tempQuestionProgressList)) }

    var actualQuestionProgress = questionViewModel.getQuestionProgressByQuestionId(
        currentQuestion.value.id.toLong(), isInReviewScreen = true
    )

    var tempQuestionProgress = tempQuestionProgressList.first { it.questionId == currentQuestion.value.id }

    val coroutine = rememberCoroutineScope()

    val enabled = remember {
        mutableStateOf(
            !questionViewModel.isFinishQuestion(
                currentQuestion.value, currentQuestionProgress = tempQuestionProgress
            )
        )
    }

    val checkFinishedQuestion =
        remember { mutableStateOf(questionViewModel.isFinishQuestion(currentQuestion.value, tempQuestionProgress)) }

    val answerStatus = remember {
        mutableStateOf(
            questionViewModel.getAnswerStatus(
                question = currentQuestion.value, currentQuestionProgress = tempQuestionProgress
            )
        )
    }

    val bookmark = remember { mutableStateOf(tempQuestionProgress.bookmark) }

    val context = LocalContext.current



    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        ProvideWindowInsets {
            Scaffold(
                topBar = {
                    QuestionAppBar(
                        appBarTitle = reviewQuestionProperty.name + "(${questions.size})"

                    ) {
                        navController.popBackStack()
                    }
                },
                backgroundColor = Color.Transparent,
                contentColor = Color.Transparent,
                modifier = Modifier.fillMaxSize().systemBarsPadding(true),
                bottomBar = {
                    QuestionBottomBar(bookmark = bookmark, onFlagClick = {
                        for (tmp in tempQuestionProgressList) {
                            Log.d("Hello", tmp.questionId + ": " + tmp.boxNum)
                        }
                    }, onBookMarkClick = {
                        runBlocking {
                            bookmark.value = !bookmark.value
                            tempQuestionProgress.bookmark = !tempQuestionProgress.bookmark
                            actualQuestionProgress.bookmark = !actualQuestionProgress.bookmark
                            questionViewModel.addOrUpdateQuestionProgressToRepo(actualQuestionProgress)
                            if (bookmark.value) {
                                Toast.makeText(context, "Added to your favorite", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Remove from your favorite", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }, onNextClick = {
                        if (tempQuestionProgressList.filter { it.boxNum == 1 }.size == questions.size) {
                            navController.popBackStack()
                        } else {
                            if (questionViewModel.isFinishQuestion(currentQuestion.value, tempQuestionProgress)) {
                                runBlocking {
                                    currentQuestion.value =
                                        questionViewModel.goToNextReviewQuestion(questions, tempQuestionProgressList)

                                    actualQuestionProgress = questionViewModel.getQuestionProgressByQuestionId(
                                        currentQuestion.value.id.toLong(), isInReviewScreen = true
                                    )

                                    tempQuestionProgress =
                                        tempQuestionProgressList.first { it.questionId == currentQuestion.value.id }

                                    if (tempQuestionProgress.boxNum != 0) tempQuestionProgress.choiceSelectedIds =
                                        realmListOf()

                                    enabled.value = !questionViewModel.isFinishQuestion(
                                        currentQuestion.value, currentQuestionProgress = tempQuestionProgress
                                    )

                                    checkFinishedQuestion.value =
                                        questionViewModel.isFinishQuestion(currentQuestion.value, tempQuestionProgress)

                                    answerStatus.value = questionViewModel.getAnswerStatus(
                                        question = currentQuestion.value, currentQuestionProgress = tempQuestionProgress
                                    )

                                    bookmark.value = tempQuestionProgress.bookmark
//                            navController.popBackStack()
//                            navController.navigate(Routes.QuestionScreen.name + "/$subTopicId/${ReviewQuestionProperty.None.name}")
                                }
                            }
                        }
                    })
                }) { innerPadding ->
                Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    ReviewQuestionProgressBar(
                        questionProgressList = tempQuestionProgressList,
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
                                tempQuestionProgress,
                                enabled,
                                answerViewModel,
                                coroutine,
                                answerStatus,
                                checkFinishedQuestion,
                                explanation = currentQuestion.value.explanation,
                                isReviewScreen = true
                            )
                        }
                    }
                }
            }
        }
    }
}