@file:OptIn(ExperimentalMaterial3Api::class)

package com.alva.codedelaroute.screens.test_option_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alva.codedelaroute.R
import com.alva.codedelaroute.models.UITestInfo
import com.alva.codedelaroute.models.UITestProgress
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.test_option_screen.widgets.OverallTestProgressBar
import com.alva.codedelaroute.utils.PoppinsFont
import com.alva.codedelaroute.utils.TestStatus
import com.alva.codedelaroute.view_models.TestInfoViewModel
import com.alva.codedelaroute.widgets.CustomCircularProgressbar
import kotlin.math.roundToInt

@Composable
fun TestOptionScreen(
    navController: NavController, testInfoId: String
) {
    val testInfoViewModel = viewModel<TestInfoViewModel>()
    val testInfo = testInfoViewModel.getTestInfoById(testInfoId)
    val allTestInfoProgress =
        testInfoViewModel.getAllTestProgressByTestInfoId(testInfoId = testInfoId)

    Scaffold(
        topBar = {
            SmallTopAppBar(navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }, title = {
                Text(
                    testInfo.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    lineHeight = 24.sp,
                    fontFamily = PoppinsFont
                )
            }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White))
        }, modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
    ) {
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Column(modifier = Modifier.fillMaxSize()) {
                OverallTestProgressBar(allTestInfoProgress)
                BoxScore(testInfo = testInfo)
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(modifier = Modifier.weight(1f)) {
                    item {
                        DifficultyChosenCard(
                            title = "Easy Test",
                            description = "Instant feedback",
                            status = allTestInfoProgress[0].status,
                            testProgress = allTestInfoProgress[0],
                            testInfo = testInfo
                        ) {
                            if (allTestInfoProgress[0].status == TestStatus.Done) {
                                navController.navigate(Routes.TestDoneScreen.name + "/${allTestInfoProgress[0].id}")
                            } else {
                                navController.navigate(Routes.TestGameScreen.name + "/$testInfoId/${allTestInfoProgress[0].id}")
                            }
                        }
                        DifficultyChosenCard(
                            title = "Medium Test",
                            description = "${testInfo.duration / 60} minute test",
                            status = allTestInfoProgress[1].status,
                            testProgress = allTestInfoProgress[1],
                            testInfo = testInfo
                        ) {
                            if (allTestInfoProgress[1].status == TestStatus.Done) {
                                navController.navigate(Routes.TestDoneScreen.name + "/${allTestInfoProgress[1].id}")
                            } else {
                                navController.navigate(Routes.TestGameScreen.name + "/$testInfoId/${allTestInfoProgress[1].id}")
                            }
                        }
                        DifficultyChosenCard(
                            title = "Hardest Test",
                            description = "${testInfo.duration / testInfo.totalQuestion} seconds / question",
                            status = allTestInfoProgress[2].status,
                            testProgress = allTestInfoProgress[2],
                            testInfo = testInfo
                        ) {
                            if (allTestInfoProgress[2].status == TestStatus.Done) {
                                navController.navigate(Routes.TestDoneScreen.name + "/${allTestInfoProgress[2].id}")
                            } else {
                                navController.navigate(Routes.TestGameScreen.name + "/$testInfoId/${allTestInfoProgress[2].id}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DifficultyChosenCard(
    title: String,
    description: String,
    status: TestStatus,
    testProgress: UITestProgress,
    testInfo: UITestInfo,
    onClick: () -> Unit = {}
) {
    val testInfoViewModel = viewModel<TestInfoViewModel>()

    ElevatedCard(
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 16.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(corner = CornerSize(12.dp)),
                ambientColor = Color(0xff002395).copy(alpha = 0.3f),
                spotColor = Color(0xff002395).copy(alpha = 0.3f),
            )
            .clickable { onClick.invoke() },
        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 21.28.sp,
                    color = Color(0xff272728),
                    fontFamily = PoppinsFont
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    description,
                    fontSize = 14.sp,
                    lineHeight = 21.28.sp,
                    color = Color(0xff4D4D4D),
                    fontFamily = PoppinsFont
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            when (status) {
                TestStatus.None -> Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Color.LightGray
                )
                TestStatus.Playing -> {
                    CustomCircularProgressbar(
                        number = (testInfoViewModel.getTestProgressPercentage(testProgress = testProgress)).roundToInt()
                            .toFloat(),
                        numberStyle = TextStyle(
                            fontSize = 11.sp
                        ),
                        size = 32.dp,
                        indicatorThickness = 4.dp,
                        backgroundIndicatorColor = Color.LightGray,
                        foregroundIndicatorColor = Color(0xff2B5AF5),
                    )
                }
                else -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "${testProgress.correctNumber} / ${testProgress.totalQuestion}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Light,
                        lineHeight = 21.28.sp,
                        fontFamily = PoppinsFont,
                        color = if (testInfoViewModel.getTestProgressTruePercentage(testProgress) * 100 < testInfo.percentPassed.toFloat()) Color(
                            0xFFEE7874
                        ) else Color(0xFF00C17C)
                    )
                    if (testInfoViewModel.getTestProgressTruePercentage(testProgress) * 100 < testInfo.percentPassed.toFloat()) Text(
                        "Failed",
                        fontSize = 13.sp,
                        lineHeight = 21.28.sp,
                        fontWeight = FontWeight.Light,
                        fontFamily = PoppinsFont,
                        color = Color(0xFFEE7874)
                    )
                    else Text(
                        "Passed",
                        fontSize = 13.sp,
                        lineHeight = 21.28.sp,
                        fontWeight = FontWeight.Light,
                        fontFamily = PoppinsFont,
                        color = Color(0xFF00C17C)
                    )
                }
            }
        }
    }
}

@Composable
fun BoxScore(testInfo: UITestInfo) {
    val correctAnswerCount = (testInfo.totalQuestion.toDouble() * 70 / 100).roundToInt()
    val mistakeAnswerAllowedCount = testInfo.totalQuestion - correctAnswerCount

    ElevatedCard(
        modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 16.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(corner = CornerSize(12.dp)),
                ambientColor = Color(0xff002395).copy(alpha = 0.3f),
                spotColor = Color(0xff002395).copy(alpha = 0.3f),
            ),
        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Box {
            Surface(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)) {
                Image(
                    painterResource(R.drawable.test_utility_panel_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ScoreInfoColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                    title = "Total questions",
                    number = "${testInfo.totalQuestion}"
                )
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp),
                    color = Color(217, 219, 243).copy(alpha = 0.5f)
                )
                ScoreInfoColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                    title = "Passing score",
                    number = "${testInfo.percentPassed}%"
                )
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp),
                    color = Color(217, 219, 243).copy(alpha = 0.5f).copy(alpha = 0.5f)
                )
                ScoreInfoColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                    title = "Correct answer",
                    number = "$correctAnswerCount"
                )
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp),
                    color = Color(217, 219, 243).copy(alpha = 0.5f).copy(alpha = 0.5f)
                )
                ScoreInfoColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                    title = "Mistake allowed",
                    number = "$mistakeAnswerAllowedCount"
                )
            }
        }
    }
}

@Composable
fun ScoreInfoColumn(
    modifier: Modifier,
    title: String,
    number: String,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            title,
            fontSize = 16.sp,
            letterSpacing = (-0.33).sp,
            lineHeight = 17.29.sp,
            color = Color.Black,
            fontFamily = PoppinsFont,
            textAlign = TextAlign.Center
        )
        Text(
            number,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.33).sp,
            lineHeight = 18.62.sp,
            fontFamily = PoppinsFont,
            color = Color.Black
        )
    }
}