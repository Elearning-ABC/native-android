package com.alva.codedelaroute.screens.game_screen.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alva.codedelaroute.R

@Composable
fun QuestionAppBar(
    appBarTitle: String,
    fontSizeScale: MutableState<Float>,
    onBackPress: () -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }

    FontSizeDialog(openDialog, fontSizeScale)

    SmallTopAppBar(navigationIcon = {
        IconButton(onClick = {
            onBackPress()
        }) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
        }
    }, title = {
        Text(
            appBarTitle,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 24.sp
        )
    }, actions = {
        IconButton(onClick = {
            openDialog.value = true
        }) {
            Image(
                painter = painterResource(R.drawable.font_icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color.Black)
            )
        }
        IconButton(onClick = {}) {
            Image(
                painter = painterResource(R.drawable.practice_icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color.Black)
            )
        }
    })
}