package com.alva.codedelaroute.screens.practice_screen.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun StartButton(modifier: Modifier = Modifier) {
    FilledTonalButton(modifier = modifier.shadow(10.dp, RoundedCornerShape(corner = CornerSize(12.dp))),
        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
        contentPadding = PaddingValues(),
        onClick = { }) {
        Box(
            modifier = Modifier.background(Brush.radialGradient(listOf(Color(0xffFF6767), Color(0xffED2939))))
                .padding(horizontal = 16.dp, vertical = 12.dp).fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Text(text = "Start learning", color = Color.White)
        }
    }
}