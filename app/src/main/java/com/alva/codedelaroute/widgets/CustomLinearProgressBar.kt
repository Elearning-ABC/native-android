package com.alva.codedelaroute.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CustomLinearProgressBar(
    modifier: Modifier, backgroundColor: Color, foregroundColor: Color, percent: Float
) {

    BoxWithConstraints(
        modifier = modifier.background(backgroundColor).fillMaxWidth()
    ) {
        Box(
            modifier = modifier.background(foregroundColor).width(maxWidth * percent)
        )
    }
}