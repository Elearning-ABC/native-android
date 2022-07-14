package com.alva.codedelaroute.screens.game_screen.widgets

import android.graphics.BitmapFactory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alva.codedelaroute.R
import com.alva.codedelaroute.models.UIQuestion
import com.alva.codedelaroute.utils.AnswerStatus
import com.alva.codedelaroute.utils.TestSetting
import java.io.IOException

@Composable
fun QuestionContainer(
    question: UIQuestion,
    answerStatus: MutableState<AnswerStatus>,
    fontSizeScale: MutableState<Float>,
    testSettingId: TestSetting?,
) {
    val context = LocalContext.current
    val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }

    try {
        with(context.assets.open("images/${question.image}")) {
            imageBitmap.value = BitmapFactory.decodeStream(this).asImageBitmap()
        }
    } catch (e: IOException) {
        imageBitmap.value = null
    }

    val borderColor = when (answerStatus.value) {
        AnswerStatus.None -> Color.LightGray
        AnswerStatus.True -> Color(0xFF00C17C)
        AnswerStatus.False -> Color(227, 30, 24).copy(alpha = 0.52f)
        AnswerStatus.TryAgainWithTrue -> Color(0xFF00C17C)
        AnswerStatus.TryAgainWithFalse -> Color(0xFFEBAD34)
    }

    val questionHeader = when (answerStatus.value) {
        AnswerStatus.None -> "NEW QUESTION"
        AnswerStatus.True -> "CORRECT"
        AnswerStatus.False -> "INCORRECT"
        AnswerStatus.TryAgainWithTrue -> "REVIEWING"
        AnswerStatus.TryAgainWithFalse -> "LEARNING"
    }

    val remindingText = when (answerStatus.value) {
        AnswerStatus.None -> null
        AnswerStatus.True -> "You will not see this question in a while"
        AnswerStatus.False -> "You will see this question soon"
        AnswerStatus.TryAgainWithTrue -> "You got this question last time"
        AnswerStatus.TryAgainWithFalse -> "You got this wrong last time"
    }

    val textColor = when (answerStatus.value) {
        AnswerStatus.None -> Color(0xFF4D4D4D)
        AnswerStatus.True -> Color(0xFF00C17C)
        AnswerStatus.False -> Color(227, 30, 24).copy(alpha = 0.52f)
        AnswerStatus.TryAgainWithTrue -> Color(0xFF00C17C)
        AnswerStatus.TryAgainWithFalse -> Color(0xFFEBAD34)
    }

    val alertIcon = when (answerStatus.value) {
        AnswerStatus.None -> null
        AnswerStatus.True -> painterResource(R.drawable.check_circle)
        AnswerStatus.False -> painterResource(R.drawable.error_circle)
        AnswerStatus.TryAgainWithTrue -> painterResource(R.drawable.check_circle)
        AnswerStatus.TryAgainWithFalse -> painterResource(R.drawable.error_circle)
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.padding(16.dp).fillMaxWidth().clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                .border(
                    BorderStroke(
                        1.dp, color = borderColor
                    ), RoundedCornerShape(corner = CornerSize(16.dp))
                )
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(question.text, modifier = Modifier.weight(1f), fontSize = (18 * fontSizeScale.value).sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    imageBitmap.value?.apply {
                        Image(
                            bitmap = this, contentDescription = null, modifier = Modifier.size(80.dp)
                        )
                    }
                }
                if (testSettingId == null) {
                    Row(modifier = Modifier.padding(top = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                        if (answerStatus.value != AnswerStatus.None) Icon(
                            alertIcon as Painter,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = textColor
                        )
                        if (answerStatus.value != AnswerStatus.None) {
                            Text(
                                remindingText!!,
                                color = textColor,
                                fontSize = 16.sp,
                                lineHeight = 22.sp,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }
                    }
                }
            }
        }
        if (testSettingId == null) {
            Surface(
                modifier = Modifier.padding(start = 32.dp).width(140.dp).background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(226, 228, 238), Color(235, 235, 240)
                        )
                    )
                ), color = Color.Transparent
            ) {
                val fontSize = remember { mutableStateOf(16.sp) }

                Text(
                    questionHeader,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = fontSize.value,
                    lineHeight = 22.sp,
                    color = textColor,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    softWrap = false,
                    onTextLayout = { textLayoutResult ->
                        if (textLayoutResult.didOverflowWidth) {
                            fontSize.value = fontSize.value.times(0.9)
                        }
                    })
            }
        }
    }
}