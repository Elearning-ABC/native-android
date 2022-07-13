package com.alva.codedelaroute.screens.test_done_screen.widgets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alva.codedelaroute.utils.PoppinsFont
import com.alva.codedelaroute.widgets.CustomLinearProgressBar
import kotlin.math.roundToInt

@Composable
fun ThreadCard(
    modifier: Modifier = Modifier,
    title: String,
    percentage: Float
) {
    val percentageIndicator = remember { Animatable(0f) }

    LaunchedEffect(true) {
        percentageIndicator.animateTo(
            percentage,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
    }

    Surface(
        modifier = modifier.padding(start = 5.dp, end = 5.dp, bottom = 16.dp).fillMaxWidth().shadow(
            elevation = 10.dp,
            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
            ambientColor = Color(0xff002395).copy(alpha = 0.3f),
            spotColor = Color(0xff002395).copy(alpha = 0.3f),
        ),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        color = Color.White,
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row {
                Text(
                    title,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f),
                    lineHeight = 26.sp,
                    letterSpacing = 0.54.sp,
                    color = Color.Black,
                    fontFamily = PoppinsFont,
                )
                Text(
                    (percentage * 100).roundToInt().toString() + "%",
                    fontSize = 16.sp,
                    lineHeight = 26.sp,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 0.54.sp,
                    color = Color.Black,
                    fontFamily = PoppinsFont,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box {
                CustomLinearProgressBar(
                    Modifier.height(8.dp).clip(shape = RoundedCornerShape(4.dp)),
                    Color(0xFFCAD1F5),
                    Color(0xFF002395),
                    percentageIndicator.value,
                )
            }
        }
    }
}