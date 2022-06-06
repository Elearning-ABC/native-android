package com.alva.codedelaroute.screens.question_screen.widgets

import android.graphics.drawable.Icon
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alva.codedelaroute.models.Answer

@Composable
fun AnswerItem(
    answer: Answer,
    panelColorState: MutableState<Color> = mutableStateOf(Color.White),
    borderAnswerColorState: MutableState<Color> = mutableStateOf(Color.Transparent),
    enabled: MutableState<Boolean> = mutableStateOf(true),
    iconState: MutableState<Map<String, Any>> = mutableStateOf(
        mapOf(
            "icon" to Icons.Default.Remove, "tint" to Color(0xFF4D4D4D)
        )
    ),
    explanation: String = "",
    onClick: () -> Unit = {}
) {

    val showExplanation = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp).clickable(enabled = enabled.value) {
            onClick()
        },
        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
        color = panelColorState.value,
        border = BorderStroke(1.dp, color = borderAnswerColorState.value)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    answer.text,
                    color = Color(0xFF4D4D4D),
                    fontSize = 16.sp,
                )

                if (!enabled.value) {
                    if (answer.isCorrect) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(16.dp))
                        AnimatedVisibility(visible = showExplanation.value) {
                            Column {
                                Text(
                                    explanation,
                                    color = Color(0xFF4D4D4D),
                                    fontSize = 14.sp,
                                    lineHeight = 18.62.sp,
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
                iconState.value["icon"] as ImageVector,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = iconState.value["tint"] as Color
            )
        }
    }
}