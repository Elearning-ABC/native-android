package com.alva.codedelaroute.screens.question_screen.widgets

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.alva.codedelaroute.R
import com.alva.codedelaroute.models.Question
import com.alva.codedelaroute.models.QuestionProgress
import com.alva.codedelaroute.models.Topic
import com.alva.codedelaroute.models.TopicProgress
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.utils.ReviewQuestionProperty
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.alva.codedelaroute.view_models.TopicViewModel
import kotlinx.coroutines.runBlocking
import java.util.Locale

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