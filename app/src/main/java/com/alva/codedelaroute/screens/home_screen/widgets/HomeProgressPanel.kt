package com.alva.codedelaroute.screens.home_screen.widgets

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alva.codedelaroute.R
import com.alva.codedelaroute.utils.PoppinsFont
import com.alva.codedelaroute.widgets.BarChartCanvas
import com.alva.codedelaroute.widgets.CustomLinearProgressBar
import com.alva.codedelaroute.widgets.CustomToast
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@Preview
@Composable
fun HomeProgressPanel() {
    val today = LocalDateTime.now()

    val listDaysOfWeek = remember {
        mutableListOf<Map<String, Any>>(
            mapOf("date" to today.minusDays(3), "data" to 30f),
            mapOf("date" to today.minusDays(2), "data" to 30f),
            mapOf("date" to today.minusDays(1), "data" to 30f),
            mapOf("date" to today, "data" to 30f),
            mapOf("date" to today.plusDays(1), "data" to 0f),
            mapOf("date" to today.plusDays(2), "data" to 0f),
            mapOf("date" to today.plusDays(3), "data" to 0f),
        )
    }

    val list = remember {
        mutableStateOf(listOf(98, 22, 42, 51, 24, 61, 23, 61, 1, 25, 21))
    }
    val selectedItem = remember { mutableStateOf(list.value.first()) }

    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxWidth().shadow(
            elevation = 10.dp,
            shape = RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp),
            ambientColor = Color(0xff002395).copy(alpha = 0.3f),
            spotColor = Color(0xff002395).copy(alpha = 0.8f),
        ), color = Color.White, shape = RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp)
    ) {
        val state = remember {
            MutableTransitionState(false)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)) {
                AnimatedVisibility(!state.currentState) {
                    Row(modifier = Modifier.weight(1f)) {
                        Image(
                            painter = painterResource(id = R.drawable.cup_icon),
                            contentDescription = null,
                            modifier = Modifier.size(height = 55.dp, width = 52.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        ProgressInfoColumn(modifier = Modifier.weight(1f))
                        Spacer(modifier = Modifier.size(20.dp))
                        DateProgress(
                            dateTime = today, progress = -30f, isToday = true
                        )
                    }
                }
                listDaysOfWeek.map {
                    AnimatedVisibility(state.currentState) {
                        DateProgress(
                            dateTime = it["date"] as LocalDateTime,
                            progress = -30f,
                            isToday = (it["date"] as LocalDateTime) == today
                        )
                    }
                }
            }

            val density = LocalDensity.current

            AnimatedVisibility(visibleState = state, enter = slideInVertically {
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(
                initialAlpha = 0.3f
            ), exit = shrinkVertically() + fadeOut()) {
                Column {
                    Surface(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp).fillMaxWidth(),
                        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
                        color = Color(0xffEDF0FF),
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                "Total questions 65/200",
                                fontSize = 16.sp,
                                lineHeight = 21.28.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xff002395),
                            )
                            Box(modifier = Modifier.padding(vertical = 12.dp)) {
                                CustomLinearProgressBar(
                                    Modifier.height(4.dp).clip(shape = RoundedCornerShape(4.dp)),
                                    Color(0xFFCAD1F5),
                                    Color(0xff002395),
                                    percent = (65f / 200),
                                )
                            }
                        }
                    }
                    BarChartCanvas(list.value) {
                        selectedItem.value = it
                        CustomToast.showToast(
                            context,
                            "You have clicked ${selectedItem.value}!",
                            icon = R.drawable.check_circle,
                            textColor = R.color.toast_success_text_color,
                            toastBackgroundColor = R.color.toast_success_background_color
                        )
                    }
                }
            }

            Icon(if (state.currentState) painterResource(R.drawable.arrow_up) else painterResource(R.drawable.arrow_down),
                contentDescription = null,
                modifier = Modifier.padding(5.dp).size(24.dp).clickable {
                    state.targetState = !state.currentState
                })
        }
    }
}

@Composable
fun DateProgress(dateTime: LocalDateTime, progress: Float, isToday: Boolean) {
    val dateFormatToWeek = DateTimeFormatter.ofPattern("EEE")
    val dateFormatToDay = DateTimeFormatter.ofPattern("dd")
    val today = LocalDateTime.now()

    Surface(
        color = Color.White,
        modifier = Modifier.size(height = 86.dp, width = 50.dp),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(
            width = 1.dp, if (isToday) Color(0xFF97A8E9) else Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                dateTime.format(dateFormatToWeek),
                color = if (dateTime.dayOfMonth == today.dayOfMonth) Color(0xFF0934C0) else Color(0xff83889F),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            if (dateTime.isAfter(today)) {
                Surface(shape = CircleShape, modifier = Modifier.size(32.dp), color = Color(0xffE4E4E4)) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = dateTime.format(dateFormatToDay), style = TextStyle(
                                color = Color(0xff83889F),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = PoppinsFont
                            )
                        )
                    }
                }
            } else {
                DateProgressCircularBar(
                    number = progress,
                    numberStyle = TextStyle(
                        color = if (dateTime.dayOfMonth == today.dayOfMonth) Color(0xFF0934C0) else Color(0xff83889F),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = PoppinsFont
                    ),
                    dateNum = dateTime.format(dateFormatToDay).toInt(),
                    size = 32.dp,
                    indicatorThickness = 2.dp,
                    foregroundIndicatorColor = Color(0xff0066FF),
                    backgroundIndicatorColor = Color(0xffCDD2FD)
                )
            }
        }
    }
}

@Composable
fun ProgressInfoColumn(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Column {
            Text(
                "Complete your today plan to get a 91% passing rate",
                color = Color(0xFF002395),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Today plan: 6/20",
                fontSize = 16.sp,
                lineHeight = 21.28.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF2B5AF5),
            )
            Box(modifier = Modifier.padding(vertical = 12.dp)) {
                CustomLinearProgressBar(
                    Modifier.height(4.dp).clip(shape = RoundedCornerShape(4.dp)),
                    Color(0xFFCAD1F5),
                    Color(0xFF2B5AF5),
                    0.3f,
                )
            }
        }
    }
}