package com.alva.codedelaroute.screens.test_option_screen.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alva.codedelaroute.models.UITestProgress
import com.alva.codedelaroute.view_models.TestInfoViewModel

@Composable
fun OverallTestProgressBar(
    listTestProgress: MutableList<UITestProgress>, testInfoViewModel: TestInfoViewModel = viewModel()
) {
    val indicatorProgress = testInfoViewModel.getOverallTestProgress(listTestProgress)

    Surface(color = Color.Transparent, modifier = Modifier.fillMaxWidth()) {
        LinearProgressIndicator(
            color = Color(0xFF00E291), backgroundColor = Color.LightGray, progress = indicatorProgress / 100
        )
    }
}