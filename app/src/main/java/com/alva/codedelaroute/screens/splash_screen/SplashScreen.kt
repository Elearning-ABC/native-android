package com.alva.codedelaroute.screens.splash_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.datastore.core.DataStore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController

import com.alva.codedelaroute.R
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.view_models.FirstLaunchAppViewModel
import com.alva.codedelaroute.view_models.OnStartViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current

    val dataStore = FirstLaunchAppViewModel(context)

    val isFirstLaunch = dataStore.getBool.collectAsState(true)

    val startAnim = remember { mutableStateOf(false) }

    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnim.value) 1f else 0f,
        animationSpec = tween(
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

    Surface(modifier = Modifier.fillMaxSize().alpha(alpha = alphaAnim.value)) {
        Image(
            painter = painterResource(R.drawable.splash_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}