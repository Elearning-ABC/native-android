package com.alva.codedelaroute.screens.home_screen.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.view_models.TopicViewModel


@Composable
fun HomeStartButton(
    modifier: Modifier = Modifier, topicViewModel: TopicViewModel = viewModel(), navController: NavController
) {
    FilledTonalButton(modifier = modifier.shadow(10.dp, RoundedCornerShape(corner = CornerSize(12.dp))),
        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
        contentPadding = PaddingValues(),
        onClick = {
            val continueTopic = topicViewModel.getContinueTopic()
            if (continueTopic != null) {
                navController.navigate(Routes.TopicScreen.name + "/${continueTopic.parentId}")
                navController.navigate(Routes.PracticeGameScreen.name + "/${continueTopic.id}")
            }
        }) {
        Box(
            modifier = Modifier.background(Brush.radialGradient(listOf(Color(0xffFF6767), Color(0xffED2939))))
                .padding(horizontal = 16.dp, vertical = 16.dp).fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Start learning",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
    }
}