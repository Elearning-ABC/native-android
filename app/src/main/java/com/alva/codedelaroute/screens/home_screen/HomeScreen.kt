package com.alva.codedelaroute.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.alva.codedelaroute.R
import com.alva.codedelaroute.screens.practice_screen.widgets.StartButton
import com.alva.codedelaroute.screens.home_screen.tabs.practice_tab.PracticeTab
import com.alva.codedelaroute.screens.home_screen.tabs.review_tab.ReviewTab
import com.alva.codedelaroute.screens.home_screen.tabs.test_tab.TestTab
import com.alva.codedelaroute.screens.home_screen.widgets.CommonAppBar
import com.alva.codedelaroute.widgets.MainBottomBar
import com.alva.codedelaroute.widgets.ProgressPanel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
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
            Scaffold(
                topBar = { CommonAppBar() },
                backgroundColor = Color.Transparent,
                contentColor = Color.Transparent,
                bottomBar = {
                    MainBottomBar(navController, pagerState)
                },
                modifier = Modifier.fillMaxHeight()
            ) { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding).fillMaxHeight()) {
                    ProgressPanel()
                    StartButton(Modifier.padding(horizontal = 47.dp, vertical = 16.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        HorizontalPager(count = 3, state = pagerState, verticalAlignment = Alignment.Top) { index ->
                            when (index) {
                                0 -> PracticeTab(navController)
                                1 -> TestTab(navController)
                                2 -> ReviewTab(navController)
                            }
                        }
                    }
                }
            }
        }

    }
}