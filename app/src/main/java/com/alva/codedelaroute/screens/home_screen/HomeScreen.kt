@file:OptIn(ExperimentalMaterialApi::class)

package com.alva.codedelaroute.screens.home_screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.alva.codedelaroute.R
import com.alva.codedelaroute.screens.home_screen.widgets.HomeStartButton
import com.alva.codedelaroute.screens.home_screen.tabs.practice_tab.PracticeTab
import com.alva.codedelaroute.screens.home_screen.tabs.review_tab.ReviewTab
import com.alva.codedelaroute.screens.home_screen.tabs.test_tab.TestTab
import com.alva.codedelaroute.screens.home_screen.widgets.HomeAppBar
import com.alva.codedelaroute.screens.home_screen.widgets.MenuBottomSheet
import com.alva.codedelaroute.screens.home_screen.widgets.ProfileBottomSheet
import com.alva.codedelaroute.view_models.AppConfigurationViewModel
import com.alva.codedelaroute.screens.home_screen.widgets.HomeBottomBar
import com.alva.codedelaroute.screens.home_screen.widgets.HomeProgressPanel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
) {
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded })

    val pagerState = rememberPagerState(initialPage = 0)

    val bottomSheetName = remember { mutableStateOf("") }

    val context = LocalContext.current

    val dataStore = AppConfigurationViewModel(context)

    val coroutine = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(bottomSheetState) {
        snapshotFlow { bottomSheetState.isVisible }.collect { isVisible ->
            if (!isVisible) {
                keyboardController?.hide()
            }
        }
    }

    LaunchedEffect(true) {
        coroutine.launch {
            dataStore.saveIsFirstLaunchApp(false)
        }
    }

    ProvideWindowInsets {
        Surface(modifier = Modifier.systemBarsPadding(true).fillMaxSize()) {
            val configuration = LocalConfiguration.current

            val screenHeight = configuration.screenHeightDp
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            ModalBottomSheetLayout(
                sheetShape = if (bottomSheetName.value == "menu") RectangleShape else RoundedCornerShape(
                    topStart = 32.dp, topEnd = 32.dp
                ),
                sheetContent = {
                    if (bottomSheetName.value == "menu") MenuBottomSheet() else ProfileBottomSheet()
                },
                sheetState = bottomSheetState,
                sheetElevation = 10.dp,
            ) {
                Scaffold(topBar = {
                    HomeAppBar {
                        coroutine.launch {
                            bottomSheetName.value = it
                            if (!bottomSheetState.isVisible) {
                                bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                            } else {
                                bottomSheetState.animateTo(ModalBottomSheetValue.Hidden)
                            }
                        }
                    }
                }, backgroundColor = Color.Transparent, contentColor = Color.Transparent, bottomBar = {
                    HomeBottomBar(pagerState)
                }, modifier = Modifier.fillMaxHeight()
                ) { innerPadding ->
                    val canvasHeight = remember { mutableStateOf(240.dp) }

                    Column(modifier = Modifier.padding(innerPadding).fillMaxHeight()) {
                        HomeProgressPanel(modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                            if (layoutCoordinates.size.height > screenHeight.times(0.7)
                                    .times(layoutCoordinates.size.width.toDouble() / configuration.screenWidthDp)
                            ) {
                                canvasHeight.value = 160.dp
                            }
                        }, canvasHeight = canvasHeight)
                        Column(modifier = Modifier.weight(1f)) {
                            HomeStartButton(
                                Modifier.padding(horizontal = 47.dp, vertical = 16.dp).wrapContentHeight(),
                                navController = navController
                            )
                            Box(modifier = Modifier.weight(1f)) {
                                HorizontalPager(
                                    count = 3,
                                    state = pagerState,
                                    verticalAlignment = Alignment.Top,
                                ) { index ->
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
    }
}