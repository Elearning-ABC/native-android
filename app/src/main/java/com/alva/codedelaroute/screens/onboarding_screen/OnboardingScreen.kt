@file:OptIn(ExperimentalPagerApi::class)

package com.alva.codedelaroute.screens.onboarding_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.alva.codedelaroute.R
import com.alva.codedelaroute.navigations.Routes
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun OnboardingScreen(
    navController: NavController,
    listPage: List<Map<String, Any>> = listOf(
        mapOf(
            "backgroundImageId" to R.drawable.onboarding_background_1,
            "imageId" to R.drawable.onboarding_image_1,
            "title" to "Pass your ASVAB on the first try",
            "description" to "98% of our users pass their ASVAB on the first try",
            "buttonName" to "Next"
        ),
        mapOf(
            "backgroundImageId" to R.drawable.onboarding_background_2,
            "imageId" to R.drawable.onboarding_image_2,
            "title" to "Experience the best ASVAB simulated exams",
            "description" to "All our exam-like questions written by experts will best familiarize you with the real test format",
            "buttonName" to "Next"
        ),
        mapOf(
            "backgroundImageId" to R.drawable.onboarding_background_3,
            "imageId" to R.drawable.onboarding_image_3,
            "title" to "Get your personal study plan automatically",
            "description" to "Based on your current level, an in-depth study plan will be generated to save extremely your time and efforts",
            "buttonName" to "Finish"
        ),
    )
) {
    val pagerState = rememberPagerState(initialPage = 0)

    val coroutine = rememberCoroutineScope()

    Box {
        HorizontalPager(count = listPage.size, modifier = Modifier.fillMaxSize(), state = pagerState) { page ->
            OnBoardingTab(
                backgroundImageId = listPage[page]["backgroundImageId"] as Int,
                imageId = listPage[page]["imageId"] as Int,
                title = listPage[page]["title"] as String,
                description = listPage[page]["description"] as String,
                buttonName = listPage[page]["buttonName"] as String
            ) {
                if (page == 2) {
                    navController.popBackStack()
                    navController.navigate(Routes.HomeScreen.name)
                } else {
                    coroutine.launch {
                        pagerState.animateScrollToPage(page + 1)
                    }
                }
            }
        }
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.weight(1f))
            HorizontalPagerIndicator(pagerState, activeColor = Color(0xFF0B2EA0), inactiveColor = Color(0xffD7DDF3))
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun OnBoardingTab(
    imageId: Int, title: String, description: String, buttonName: String, backgroundImageId: Int,
    onButtonClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(backgroundImageId),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(120.dp))
            Image(
                painter = painterResource(imageId),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                title,
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                lineHeight = 34.sp,
                color = Color(0xFF0E31A3),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 80.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                description,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                lineHeight = 24.sp,
                color = Color(0xFF4D4D4D),
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            OnBoardingButton(title = buttonName, onClick = onButtonClick)
        }
    }
}

@Composable
fun OnBoardingButton(
    title: String, onClick: () -> Unit = {}
) {
    FilledTonalButton(modifier = Modifier.padding(horizontal = 24.dp, vertical = 30.dp).fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B2EA0)),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        elevation = ButtonDefaults.buttonElevation(10.dp),
        onClick = {
            onClick()
        }) {
        Text(
            text = title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp
        )
    }
}