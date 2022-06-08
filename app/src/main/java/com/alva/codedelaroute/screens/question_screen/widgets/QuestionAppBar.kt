package com.alva.codedelaroute.screens.question_screen.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.alva.codedelaroute.R
import com.alva.codedelaroute.models.Topic

@Composable
fun QuestionAppBar(
    appBarTitle: String,
    onBackPress: () -> Unit
) {
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
        IconButton(onClick = {}) {
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