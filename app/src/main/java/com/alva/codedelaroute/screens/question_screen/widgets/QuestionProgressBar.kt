package com.alva.codedelaroute.screens.question_screen.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun QuestionProgressBar() {
    Surface(color = Color.Transparent, modifier = Modifier.fillMaxWidth()) {
        LinearProgressIndicator(
            color = Color(0xFFEBAD34),
            backgroundColor = Color.LightGray,
            progress = 0.6f,
        )
        LinearProgressIndicator(
            color = Color(0xFF00E291),
            backgroundColor = Color.Transparent,
            progress = 0.4f,
        )
    }
}