package com.alva.codedelaroute.screens.splash_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController

import com.alva.codedelaroute.R
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.view_models.AppConfigurationViewModel
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController, questionViewModel: QuestionViewModel = viewModel()
) {
    val context = LocalContext.current

    val dataStore = AppConfigurationViewModel(context)

    val isFirstLaunch = dataStore.getIsFirstLaunchApp.collectAsState(true)

    val startAnim = remember { mutableStateOf(false) }

    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnim.value) 1f else 0f, animationSpec = tween(
            durationMillis = 3000
        )
    )

    LaunchedEffect(true) {
        startAnim.value = true
        delay(4000)
        navController.popBackStack()
        if (isFirstLaunch.value!!) {
            navController.navigate(Routes.OnBoardingScreen.name)
        } else {
            navController.navigate(Routes.HomeScreen.name)
        }
    }

    ProvideWindowInsets {
        Surface(modifier = Modifier.fillMaxSize().alpha(alpha = alphaAnim.value)) {
            Image(
                painter = painterResource(R.drawable.splash_image),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}