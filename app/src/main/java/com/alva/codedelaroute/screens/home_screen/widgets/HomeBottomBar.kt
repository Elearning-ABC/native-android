@file:OptIn(ExperimentalPagerApi::class)
package com.alva.codedelaroute.screens.home_screen.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alva.codedelaroute.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.*

@Composable
fun HomeBottomBar(
    selectedItemState: PagerState

) {
    val coroutineScope = rememberCoroutineScope()

    val navigationBarItemDefaultColors = NavigationBarItemDefaults.colors(
        indicatorColor = Color(0xFFF7F7F7),
    )

    NavigationBar(
        containerColor = Color(0xFFF7F7F7),
        contentColor = Color(0xff002395),
        tonalElevation = 10.dp,
    ) {
        NavigationBarItem(onClick = {
            coroutineScope.launch {
                selectedItemState.animateScrollToPage(0)
            }
        },
            icon = {
                Image(
                    painterResource(R.drawable.practice_icon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(
                        if (selectedItemState.currentPage == 0) Color(0xFF002395) else Color(
                            0xFFABB1DB
                        )
                    ),
                )
            },
            selected = selectedItemState.currentPage == 0,
            alwaysShowLabel = false,
            enabled = true,
            colors = navigationBarItemDefaultColors
        )
        NavigationBarItem(onClick = {
            coroutineScope.launch {
                selectedItemState.animateScrollToPage(1)
            }
        },
            icon = {
                Image(
                    painterResource(R.drawable.test_icon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(
                        if (selectedItemState.currentPage == 1) Color(0xFF002395) else Color(
                            0xFFABB1DB
                        )
                    ),
                )
            },
            selected = selectedItemState.currentPage == 1,
            alwaysShowLabel = false,
            enabled = true,
            colors = navigationBarItemDefaultColors
        )
        NavigationBarItem(onClick = {
            coroutineScope.launch {
                selectedItemState.animateScrollToPage(2)
            }
        },
            icon = {
                Image(
                    painterResource(R.drawable.review_icon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(
                        if (selectedItemState.currentPage == 2) Color(0xFF002395) else Color(
                            0xFFABB1DB
                        )
                    ),
                )
            },
            selected = selectedItemState.currentPage == 2,
            alwaysShowLabel = false,
            enabled = true,
            colors = navigationBarItemDefaultColors
        )
    }
}