@file:OptIn(ExperimentalMaterial3Api::class)

package com.alva.codedelaroute.screens.test_done_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alva.codedelaroute.R
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.test_done_screen.widgets.ReviewDialog
import com.alva.codedelaroute.screens.test_done_screen.widgets.ThreadCard
import com.alva.codedelaroute.utils.PoppinsFont
import com.alva.codedelaroute.utils.TestSetting
import com.alva.codedelaroute.utils.TestStatus
import com.alva.codedelaroute.view_models.TestInfoViewModel
import com.alva.codedelaroute.view_models.TopicViewModel
import com.alva.codedelaroute.widgets.ArcProgressBar
import com.alva.codedelaroute.widgets.CustomAlertDialog
import com.alva.codedelaroute.widgets.CustomButton
import com.alva.codedelaroute.widgets.CustomToast
import com.google.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt

@Composable
fun TestDoneScreen(navController: NavController, testProgressId: String) {
    val testInfoViewModel: TestInfoViewModel = viewModel()

    val topicViewModel: TopicViewModel = viewModel()

    val testProgress = testInfoViewModel.getTestProgressById(testProgressId)

    val testInfo = testInfoViewModel.getTestInfoById(testProgress.testId)

    val openResetDialog = remember { mutableStateOf(false) }

    val openReviewDialog = remember { mutableStateOf(false) }

    val coroutine = rememberCoroutineScope()

    val checkPassed = (testInfoViewModel.getTestProgressTruePercentage(testProgress) * 100) >= testInfo.percentPassed

    val context = LocalContext.current

    if (openResetDialog.value) {
        CustomAlertDialog(
            openResetDialog,
            title = "PLAY AGAIN",
            description = "Do you want to reset all progress of this test?",
            buttonAcceptTitle = "Reset",
            buttonCancelTitle = "Not Now",
            buttonAcceptClick = {
                runBlocking {
                    testInfoViewModel.resetTestProgressData(testProgressId = testProgressId)
                    navController.popBackStack()
                    navController.navigate(Routes.TestGameScreen.name + "/${testProgress.testId}/${testProgressId}")
                }
                openResetDialog.value = false
            },
            buttonCancelClick = { openResetDialog.value = false },
        )
    }

    if (openReviewDialog.value) {
        ReviewDialog(openReviewDialog, testInfo = testInfo)
    }


    LaunchedEffect(Unit) {
        coroutine.launch {
            testProgress.status = TestStatus.Done
            testInfoViewModel.updateTestProgressStatus(testProgressId, status = TestStatus.Done)
            testInfoViewModel.unLockNextTest(testInfo, testProgress)
        }
    }

    ProvideWindowInsets {
        Surface(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
            Scaffold(topBar = {
                SmallTopAppBar(title = {
                    Text(
                        when (testProgress.testSettingId) {
                            TestSetting.Easy -> "Easy Test"
                            TestSetting.Medium -> "Medium Test"
                            TestSetting.Hardest -> "Hardest Test"
                        },
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        lineHeight = 24.sp,
                        fontFamily = PoppinsFont,
                    )
                }, navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                })
            }) {
                Surface(modifier = Modifier.padding(it).fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.background),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Spacer(modifier = Modifier.height(20.dp))
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                            ArcProgressBar(
                                percent = 1f,
                                thickness = 15.dp,
                                boxSize = 180.dp,
                                backgroundIndicatorColor = Color.Transparent,
                                foregroundIndicatorColor = Color(0xffED2939).copy(alpha = 0.7f),
                                isShowText = false
                            )
                            ArcProgressBar(
                                percent = testInfoViewModel.getTestProgressTruePercentage(testProgress = testProgress),
                                thickness = 15.dp,
                                boxSize = 180.dp,
                                backgroundIndicatorColor = Color.Transparent,
                                foregroundIndicatorColor = Color(0xff14CA9E)
                            )
                        }
                    }
                    Column(modifier = Modifier.padding(24.dp)) {
                        Spacer(modifier = Modifier.height(130.dp))
                        Row {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    if (checkPassed) "Such an excellent performance!" else "Not enough to pass :(",
                                    color = Color(0xff002395),
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 32.sp,
                                    fontFamily = PoppinsFont,
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    if (checkPassed) "Good job! You've successfully passed your test. Let's ace all the tests available to increase your passing possibility!" else "Yowch! That hurt. Failing an exam always does. But hey, that was just one try. Get your notes together and try again. You can do it!",
                                    color = Color(0xff272728),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    lineHeight = 21.28.sp,
                                    fontFamily = PoppinsFont,
                                )
                            }
                            Image(
                                painterResource(if (checkPassed) R.drawable.test_successful else R.drawable.test_failed),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.width(140.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        LazyColumn(modifier = Modifier.weight(1f).shadow(elevation = 0.dp)) {
                            items(testInfo.testQuestionData.size) {
                                ThreadCard(
                                    title = topicViewModel.getTopicById(testInfo.testQuestionData[it].topicId.toLong()).name,
                                    percentage = testInfoViewModel.calculateTestCorrectNumberPerTopic(
                                        topicId = testInfo.testQuestionData[it].topicId,
                                        testInfo = testInfo,
                                        testProgress = testProgress
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            CustomButton(
                                modifier = Modifier.weight(1f).padding(end = 5.dp, top = 5.dp, bottom = 5.dp),
                                title = "Review",
                                backgroundColor = Color(0xffE6EBF9),
                                textColor = Color.Black,
                            ) {
                                openReviewDialog.value = true
                            }
                            CustomButton(
                                modifier = Modifier.weight(1f).padding(start = 5.dp, top = 5.dp, bottom = 5.dp),
                                title = "Try Again",
                                backgroundColor = Color(0xffED2939),
                                textColor = Color.White
                            ) {
                                openResetDialog.value = true
                            }
                        }
                        CustomButton(
                            modifier = Modifier.padding(vertical = 5.dp).fillMaxWidth(),
                            title = "Continue",
                            backgroundColor = Color(0xff0B2EA0),
                            textColor = Color.White
                        ) {
                            val nextTestInfoId = testInfoViewModel.getNextTestInfoId(testInfo)
                            if (nextTestInfoId == null) {
                                CustomToast.showToast(
                                    context = context,
                                    message = "Function unavailable at this time",
                                    icon = R.drawable.alert_triangle,
                                    textColor = R.color.toast_alert_text_color,
                                    toastBackgroundColor = R.color.toast_alert_background_color
                                )
                            } else {
                                navController.popBackStack()
                                navController.navigate(Routes.TestOptionScreen.name + "/$nextTestInfoId")
                            }
                        }
                    }
                }
            }
        }
    }
}