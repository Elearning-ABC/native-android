package com.alva.codedelaroute.screens.home_screen.tabs.test_tab

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.home_screen.tabs.test_tab.widgets.TestCard
import com.alva.codedelaroute.view_models.TestInfoViewModel
import com.alva.codedelaroute.view_models.TopicViewModel

@Composable
fun TestTab(
    navController: NavController, testInfoViewModel: TestInfoViewModel = viewModel()
) {
    val allTest = testInfoViewModel.allTestInfoList

    LazyColumn {
        items(allTest.size) {
            TestCard(testInfo = allTest[it]) {
                navController.navigate(Routes.TestOptionScreen.name + "/${allTest[it].id}")
            }
            if (it == allTest.size - 1) Spacer(modifier = Modifier.size(10.dp))
        }
    }
}