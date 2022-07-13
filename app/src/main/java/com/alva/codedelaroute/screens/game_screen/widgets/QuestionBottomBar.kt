package com.alva.codedelaroute.screens.game_screen.widgets

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alva.codedelaroute.R

@Composable
fun QuestionBottomBar(
    bookmark: MutableState<Boolean>,
    onFlagClick: () -> Unit,
    onBookMarkClick: () -> Unit,
    onNextClick: () -> Unit
) {


    NavigationBar(contentColor = Color.Black, containerColor = Color.White) {
        NavigationBarItem(onClick = {
            onFlagClick()
        }, selected = false, icon = {
            Icon(
                painterResource(R.drawable.flag_icon), contentDescription = null, modifier = Modifier.size(24.dp)
            )
        })
        NavigationBarItem(onClick = {
            onBookMarkClick()
        }, selected = false, icon = {
            Icon(
                if (bookmark.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        })
        NavigationBarItem(onClick = {
            onNextClick()
        }, selected = false, icon = {
            Icon(
                painterResource(R.drawable.arrow_forward_icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        })
    }
}