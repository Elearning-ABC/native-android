package com.alva.codedelaroute.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                painterResource(R.drawable.practice_icon), contentDescription = null, modifier = Modifier.size(24.dp)
            )
        }, selected = selectedItemState.currentPage == 0, alwaysShowLabel = true, enabled = true, label = {
            Text(
                "Practice",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 22.sp,
                letterSpacing = 1.sp,
                color = Color(0xFF002395)
            )
        })
        NavigationBarItem(onClick = {
            coroutineScope.launch {
                selectedItemState.animateScrollToPage(1)
            }
        }, icon = {
            Image(
                painterResource(R.drawable.test_icon), contentDescription = null, modifier = Modifier.size(24.dp)
            )
        }, selected = selectedItemState.currentPage == 1, alwaysShowLabel = true, enabled = true, label = {
            Text(
                "Test",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 22.sp,
                letterSpacing = 1.sp,
                color = Color(0xFF002395)
            )
        })
        NavigationBarItem(onClick = {
            coroutineScope.launch {
                selectedItemState.animateScrollToPage(2)
            }
        }, icon = {
            Image(
                painterResource(R.drawable.review_icon), contentDescription = null, modifier = Modifier.size(24.dp)
            )
        }, selected = selectedItemState.currentPage == 2, alwaysShowLabel = true, enabled = true, label = {
            Text(
                "Review",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 22.sp,
                letterSpacing = 1.sp,
                color = Color(0xFF002395)
            )
        })
    }
}