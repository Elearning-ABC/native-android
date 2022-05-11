package com.alva.codedelaroute.screens.question_screen.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alva.codedelaroute.models.Answer

@Composable
fun AnswerItem(
    answer: Answer,
    panelColorState: MutableState<Color>,
    borderAnswerColorState: MutableState<Color>,
    enabled: MutableState<Boolean>,
    onClick: () -> Unit = {}
) {

    Surface(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp).clickable (enabled = enabled.value) {
            onClick()
        },
        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
        color = panelColorState.value,
        border = BorderStroke(1.dp, color = borderAnswerColorState.value)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                answer.text,
                modifier = Modifier.weight(1f),
                color = Color(0xFF4D4D4D),
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = {}, enabled = false) {
                Icon(Icons.Default.Remove, contentDescription = null)
            }
        }
    }
}