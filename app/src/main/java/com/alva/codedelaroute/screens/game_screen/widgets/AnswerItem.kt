package com.alva.codedelaroute.screens.game_screen.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alva.codedelaroute.models.UIAnswer
import com.alva.codedelaroute.utils.TestSetting

@Composable
fun AnswerItem(
    answer: UIAnswer,
    panelColorState: Color = Color.White,
    borderAnswerColorState: Color = Color.Transparent,
    checkFinishedQuestion: MutableState<Boolean> = mutableStateOf(false),
    iconState: Map<String, Any> =
        mapOf(
            "icon" to Icons.Default.Remove, "tint" to Color(0xFF4D4D4D)
        ),
    explanation: String = "",
    fontSizeScale: MutableState<Float>,
    showExplanation: MutableState<Boolean>,
    testSetting: TestSetting? = null,
    onClick: () -> Unit = {}
) {

    Surface(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            .clickable(enabled = !checkFinishedQuestion.value || (testSetting != null && testSetting != TestSetting.Easy)) {
                onClick()
            },
        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
        color = panelColorState,
        border = BorderStroke(1.dp, color = borderAnswerColorState)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    answer.text,
                    color = Color(0xFF4D4D4D),
                    fontSize = (16 * fontSizeScale.value).sp,
                )

                if (checkFinishedQuestion.value && testSetting == null) {
                    if (answer.isCorrect) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(16.dp))
                        AnimatedVisibility(visible = showExplanation.value) {
                            Column {
                                Text(
                                    explanation,
                                    color = Color(0xFF4D4D4D),
                                    fontSize = (14 * fontSizeScale.value).sp,
                                    lineHeight = (18.62 * fontSizeScale.value).sp,
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }

                        Text(if (showExplanation.value) "Hide" else "Show Explanation",
                            color = Color(0xFF3478F5),
                            fontSize = 14.sp,
                            lineHeight = 18.62.sp,
                            modifier = Modifier.clickable { showExplanation.value = !showExplanation.value })
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                iconState["icon"] as ImageVector,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = iconState["tint"] as Color
            )
        }
    }
}