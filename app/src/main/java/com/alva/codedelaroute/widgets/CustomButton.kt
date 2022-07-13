package com.alva.codedelaroute.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
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
fun CustomButton(
    modifier: Modifier = Modifier, title: String, backgroundColor: Color, textColor: Color, onClick: () -> Unit
) {
    FilledTonalButton(modifier = modifier,
        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        onClick = {
            onClick()
        }) {
        Text(
            text = title, color = textColor, fontSize = 16.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp
        )
    }
}