package com.alva.codedelaroute.screens.game_screen.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel

import com.alva.codedelaroute.R
import com.alva.codedelaroute.view_models.GameViewModel
import com.alva.codedelaroute.view_models.TestInfoViewModel
import kotlinx.coroutines.delay

@Composable
fun TestClockPanel(
    timeUsed: Int,
    duration: Int,
    countDownTime: MutableState<Int>,
    testProgressId: String,
    timeOutHandler: () -> Unit = {}
) {
    val testInfoViewModel = viewModel<TestInfoViewModel>()

    val lifecycle: LifecycleOwner = LocalLifecycleOwner.current

    var hour = (countDownTime.value - timeUsed) / 3600
    var minute = ((countDownTime.value - timeUsed) - hour * 3600) / 60
    var second = (countDownTime.value - timeUsed) - hour * 3600 - minute * 60

    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> {
                    testInfoViewModel.updateTestProgressTime(
                        testProgressId = testProgressId, duration - (countDownTime.value - timeUsed)
                    )
                }
                else -> {}
            }
        }
        lifecycle.lifecycle.addObserver(observer)
        onDispose {
            lifecycle.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        while ((countDownTime.value - timeUsed) > 0) {
            delay(1000)
            countDownTime.value--
//            if (second.value > 0) {
//                second.value--
//            } else {
//                second.value = 59
//                if (minute.value > 0) {
//                    minute.value--
//                } else {
//                    minute.value = 59
//                    if (hour.value > 0) hour.value--
//                }
//            }
            if ((countDownTime.value - timeUsed) == 0) {
                timeOutHandler.invoke()

                countDownTime.value = duration

                hour = countDownTime.value / 3600
                minute = (countDownTime.value - hour * 3600) / 60
                second = countDownTime.value - hour * 3600 - minute * 60
            }
        }
    }

    val hourString = if (hour >= 10) hour.toString() else "0${hour}"
    val minuteString = if (minute >= 10) minute.toString() else "0${minute}"
    val secondString = if (second >= 10) second.toString() else "0${second}"

    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        tonalElevation = 10.dp,
        shadowElevation = 10.dp
    ) {
        Image(
            painter = painterResource(R.drawable.test_utility_panel_background),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painterResource(R.drawable.alarm_icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                "$hourString:$minuteString:$secondString",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 32.sp
            )
        }
    }
}