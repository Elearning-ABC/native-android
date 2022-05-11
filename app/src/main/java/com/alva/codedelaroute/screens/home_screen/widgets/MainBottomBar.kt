package com.alva.codedelaroute.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.alva.codedelaroute.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainBottomBar(
    navController: NavController, selectedItemState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()
    var navigationBarItemDefaultColors = NavigationBarItemDefaults.colors(
        indicatorColor = Color(226, 228, 238),
    )

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xff002395),
        tonalElevation = 10.dp,
    ) {
        NavigationBarItem(onClick = {
            coroutineScope.launch {
                selectedItemState.animateScrollToPage(0)
            }
        }, icon = {
            Image(
                painterResource(R.drawable.practice_icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color(0xFF002395)),
            )
        }, selected = selectedItemState.currentPage == 0, alwaysShowLabel = false, enabled = true,/* label = {
            Text(
                "Practice",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 22.sp,
                letterSpacing = 1.sp
            )
        },*/ colors = navigationBarItemDefaultColors
        )
        NavigationBarItem(onClick = {
            coroutineScope.launch {
                selectedItemState.animateScrollToPage(1)
            }
        }, icon = {
            Image(
                painterResource(R.drawable.test_icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color(0xFF002395))
            )
        }, selected = selectedItemState.currentPage == 1, alwaysShowLabel = false, enabled = true,/* label = {
            Text(
                "Test",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 22.sp,
                letterSpacing = 1.sp,
            )
        },*/ colors = navigationBarItemDefaultColors
        )
        NavigationBarItem(onClick = {
            coroutineScope.launch {
                selectedItemState.animateScrollToPage(2)
            }
        }, icon = {
            Image(
                painterResource(R.drawable.review_icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color(0xFF002395))
            )
        }, selected = selectedItemState.currentPage == 2, alwaysShowLabel = false, enabled = true,/* label = {
            Text(
                "Review", fontWeight = FontWeight.SemiBold, fontSize = 12.sp, lineHeight = 22.sp, letterSpacing = 1.sp
            )
        }*/ colors = navigationBarItemDefaultColors
        )
    }
}