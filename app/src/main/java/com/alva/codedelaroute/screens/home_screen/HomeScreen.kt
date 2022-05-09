package com.alva.codedelaroute.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.alva.codedelaroute.R
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.practice_screen.widgets.StartButton
import com.alva.codedelaroute.screens.home_screen.widgets.TopicCard
import com.alva.codedelaroute.screens.practice_screen.PracticeScreen
import com.alva.codedelaroute.widgets.CommonAppBar
import com.alva.codedelaroute.widgets.MainBottomBar
import com.alva.codedelaroute.widgets.ProgressPanel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun HomeScreen(
    navController: NavController = rememberNavController()
) {
    val pagerState = rememberPagerState(initialPage = 0)

    ProvideWindowInsets {
        Surface(modifier = Modifier.systemBarsPadding(true).fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Scaffold(topBar = { CommonAppBar() },
                backgroundColor = Color.Transparent,
                contentColor = Color.Transparent,
                bottomBar = {
                    MainBottomBar(navController, pagerState)
                }) { innerPadding ->
                Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    ProgressPanel()
                    StartButton(Modifier.padding(horizontal = 47.dp, vertical = 16.dp))
                    HorizontalPager(count = 3, modifier = Modifier.weight(1f), state = pagerState) { index ->
                        PracticeScreen(navController)
                    }
                }
            }
        }

    }
}