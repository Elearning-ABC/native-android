package com.alva.codedelaroute.widgets

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun ArcProgressBar(
    percent: Float,
    boxSize: Dp = 200.dp,
    thickness: Dp = 15.dp,
    animationDuration: Int = 1000,
    foregroundIndicatorColor: Color = Color(0xFF35898f),
    backgroundIndicatorColor: Color = Color.LightGray.copy(alpha = 0.2f),
    isShowText: Boolean = true
) {
    // It remembers the number value
    var dataR by remember {
        mutableStateOf(0f)
    }

    // Number Animation
    val animateNumber = animateFloatAsState(
        targetValue = dataR, animationSpec = tween(
            durationMillis = animationDuration
        )
    )

    // This is to start the animation when the activity is opened
    LaunchedEffect(Unit) {
        dataR = percent
    }

    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.width(boxSize)
    ) {
        Canvas(
            modifier = Modifier.size(boxSize)
        ) {
            // Background Arc
            drawArc(
                color = backgroundIndicatorColor,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round),
                size = Size(size.width, size.height)
            )

            // Foreground circle
            if (percent != 0f) {
                drawArc(
                    color = foregroundIndicatorColor,
                    startAngle = 180f,
                    sweepAngle = 180f * animateNumber.value,
                    useCenter = false,
                    style = Stroke(thickness.toPx(), cap = StrokeCap.Round),
                    size = Size(size.width, size.height),
                )
            }
        }

        if (isShowText) {
            DisplayText(
                animateNumber = animateNumber
            )
        }
    }
}

@Composable
private fun DisplayText(
    animateNumber: State<Float>
) {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                ) {
                    append((animateNumber.value * 100).roundToInt().toString())
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                        )
                    ) {
                        append("%")
                    }
                }
            },
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}