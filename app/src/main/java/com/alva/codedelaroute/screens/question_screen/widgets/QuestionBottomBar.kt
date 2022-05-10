package com.alva.codedelaroute.screens.question_screen.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alva.codedelaroute.R
import com.alva.codedelaroute.models.Question

@Preview(showBackground = true)
@Composable
fun QuestionBottomBar() {
    NavigationBar(contentColor = Color.Black, containerColor = Color.White) {
        NavigationBarItem(onClick = {}, selected = false, icon = {
            Icon(
                painterResource(R.drawable.flag_icon), contentDescription = null, modifier = Modifier.size(24.dp)
            )
        })
        NavigationBarItem(onClick = {}, selected = false, icon = {
            Icon(
                painterResource(R.drawable.favorite_border_rounded_icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        })
        NavigationBarItem(onClick = {}, selected = false, icon = {
            Icon(
                painterResource(R.drawable.arrow_forward_icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        })
    }
}