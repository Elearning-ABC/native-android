@file:OptIn(ExperimentalMaterialApi::class)

package com.alva.codedelaroute.screens.home_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.alva.codedelaroute.R
import com.alva.codedelaroute.screens.home_screen.widgets.StartButton
import com.alva.codedelaroute.screens.home_screen.tabs.practice_tab.PracticeTab
import com.alva.codedelaroute.screens.home_screen.tabs.review_tab.ReviewTab
import com.alva.codedelaroute.screens.home_screen.tabs.test_tab.TestTab
import com.alva.codedelaroute.screens.home_screen.widgets.CommonAppBar
import com.alva.codedelaroute.screens.home_screen.widgets.MenuBottomSheet
import com.alva.codedelaroute.screens.home_screen.widgets.ProfileBottomSheet
import com.alva.codedelaroute.view_models.FirstLaunchAppViewModel
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.alva.codedelaroute.widgets.MainBottomBar
import com.alva.codedelaroute.widgets.ProgressPanel
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
    questionViewModel: QuestionViewModel = viewModel(
        viewModelStoreOwner = LocalViewModelStoreOwner.current!!
    )
) {
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded })

    val pagerState = rememberPagerState(initialPage = 0)

    val bottomSheetName = remember { mutableStateOf("") }

    val context = LocalContext.current

    val dataStore = FirstLaunchAppViewModel(context)

    val coroutine = rememberCoroutineScope()

    LaunchedEffect(true) {
        coroutine.launch {
            dataStore.saveBool(false)
            questionViewModel.initQuestionProgressList()
        }
    }

    ProvideWindowInsets {
        Surface(modifier = Modifier.systemBarsPadding(true).fillMaxSize()) {
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
                    CommonAppBar(bottomSheetState) {
                        bottomSheetName.value = it
                    }
                }, backgroundColor = Color.Transparent, contentColor = Color.Transparent, bottomBar = {
                    MainBottomBar(pagerState)
                }, modifier = Modifier.fillMaxHeight()
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding).fillMaxHeight()) {
                        ProgressPanel()
                        StartButton(
                            Modifier.padding(horizontal = 47.dp, vertical = 16.dp),
                            navController = navController
                        )
                        Box(modifier = Modifier.weight(1f)) {
                            HorizontalPager(
                                count = 3,
                                state = pagerState,
                                verticalAlignment = Alignment.Top,
                                flingBehavior = ScrollableDefaults.flingBehavior()
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