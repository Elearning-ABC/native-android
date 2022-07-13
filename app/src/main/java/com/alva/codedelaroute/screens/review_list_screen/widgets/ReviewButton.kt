package com.alva.codedelaroute.screens.review_list_screen.widgets

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReviewButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FilledTonalButton(modifier = modifier.shadow(10.dp, RoundedCornerShape(corner = CornerSize(12.dp))),
        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
        contentPadding = PaddingValues(),
        onClick = { onClick() }) {
        Box(
            modifier = Modifier.background(
                Color(0xFF0B2EA0),
            )
                .padding(horizontal = 16.dp, vertical = 16.dp).fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Review These Questions",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
    }
}