package com.alva.codedelaroute.screens.game_screen.widgets

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alva.codedelaroute.models.TestProgress
import com.alva.codedelaroute.models.UITestProgress
import com.alva.codedelaroute.view_models.TestInfoViewModel

@Composable
fun TestQuestionProgressBar(
    testProgress: UITestProgress,
    testInfoViewModel: TestInfoViewModel = viewModel(),
    checkFinishedQuestion: MutableState<Boolean>
) {
    val indicatorProgress = remember { Animatable(testInfoViewModel.getTestProgressPercentage(testProgress)) }

    Surface(color = Color.Transparent, modifier = Modifier.fillMaxWidth()) {
        LinearProgressIndicator(
            color = Color(0xFF00E291), backgroundColor = Color.LightGray, progress = indicatorProgress.value / 100
        )
    }
    LaunchedEffect(checkFinishedQuestion.value) {
        indicatorProgress.animateTo(testInfoViewModel.getTestProgressPercentage(testProgress))
    }
}