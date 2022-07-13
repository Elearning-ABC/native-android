@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalPagerApi::class)

package com.alva.codedelaroute.widgets

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy

import com.alva.codedelaroute.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@Composable
fun TopUpDialog(
    openDialog: MutableState<Boolean>,
) {
    val pagerState = rememberPagerState(initialPage = 0)

    Dialog(
        onDismissRequest = {
            openDialog.value = false
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            securePolicy = SecureFlagPolicy.SecureOn,
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            modifier = Modifier.padding(50.dp).fillMaxWidth().wrapContentHeight(),
            shape = RoundedCornerShape(corner = CornerSize(25.dp)),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                HorizontalPager(count = 4, state = pagerState, verticalAlignment = Alignment.Top) {
                    when (it) {
                        0 -> TopUpMainPage()
                        1 -> SubTopUpPage(
                            R.drawable.no_ads_image,
                            title = "No more ads",
                            description = "Remove all ads to learn and practice tests without any distractions",
                            openDialog = openDialog
                        )
                        2 -> SubTopUpPage(
                            R.drawable.dark_theme_top_up_image,
                            title = "Attractive dark mode UI",
                            description = "Beauty, relaxing your eyes and reducing battery drain significantly",
                            openDialog = openDialog
                        )
                        3 -> SubTopUpPage(
                            R.drawable.unlock_full_test_image,
                            title = "Unlock full test modes",
                            description = "1000+ practice questions are available with 3 interesting test modes from easy to hardest mode.",
                            openDialog = openDialog
                        )
                    }
                }
                HorizontalPagerIndicator(
                    modifier = Modifier.padding(30.dp),
                    pagerState = pagerState,
                    activeColor = Color(0xff0B2EA0),
                    indicatorWidth = 6.dp,
                    indicatorHeight = 6.dp,
                    inactiveColor = Color(0xffD7DDF3)
                )
                Surface(modifier = Modifier.fillMaxWidth().wrapContentHeight(), color = Color(0xffD7DDF3)) {
                    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontSize = 16.sp,
                                    letterSpacing = (0.02 * 16).sp,
                                    color = Color.Black
                                )
                            ) {
                                append("Upgrade for ")
                                withStyle(
                                    SpanStyle(
                                        fontSize = 14.sp,
                                        letterSpacing = (0.02 * 14).sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append("2.99\$")
                                }
                            }
                        })
                        Spacer(modifier = Modifier.height(15.dp))
                        FilledTonalButton(
                            onClick = {},
                            shape = RoundedCornerShape(corner = CornerSize(36.dp)),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = Color(0xFFEE2A3A)
                            ),
                            modifier = Modifier.padding(horizontal = 30.dp).fillMaxWidth()
                        ) {
                            Text(
                                if (pagerState.currentPage == 0)
                                    "UPGRADE NOW" else "UPGRADE",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 24.sp,
                                modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp),
                                color = Color.White
                            )
                        }
                        if (pagerState.currentPage == 0)
                            Text(
                                "Restore purchases",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 26.sp,
                                letterSpacing = (0.02 * 16).sp,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.padding(20.dp),
                                color = Color(0xFF0B2EA0)
                            )
                    }
                }
            }
        }
    }
}

@Composable
fun TopUpMainPage() {
    Column(modifier = Modifier.wrapContentHeight().fillMaxWidth()) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.top_header_panel),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            Text(
                "HAPPIER STUDY, EASIER PASS!",
                fontSize = 16.sp,
                color = Color.White,
                lineHeight = 21.28.sp,
                letterSpacing = (-0.33).sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(20.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "Experience Pro version with multiple amazing features Type a message",
            fontSize = 14.sp,
            color = Color(0xff7390A8),
            lineHeight = 18.62.sp,
            modifier = Modifier.padding(20.dp).fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        AdvertisingRow(text = "No more ads")
        AdvertisingRow(text = "Attractive dark mode UI")
        AdvertisingRow(text = "Unlock full test modes")
    }
}

@Composable
fun SubTopUpPage(
    imageId: Int,
    title: String,
    description: String,
    openDialog: MutableState<Boolean>,
) {
    Column(modifier = Modifier.wrapContentHeight().fillMaxWidth()) {
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            Image(
                painter = painterResource(imageId),
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 77.dp).padding(top = 15.dp).fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            IconButton(onClick = { openDialog.value = false }) {
                Icon(
                    painterResource(R.drawable.cancel_icon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Text(
            title,
            fontSize = 16.sp,
            color = Color.Black,
            lineHeight = 23.94.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp).fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            description,
            fontSize = 14.sp,
            color = Color(0xff7390A8),
            lineHeight = 18.62.sp,
            modifier = Modifier.padding(20.dp).fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AdvertisingRow(
    text: String
) {
    Row(modifier = Modifier.padding(horizontal = 15.dp).padding(bottom = 10.dp)) {
        Icon(
            painterResource(R.drawable.check_circle),
            contentDescription = null,
            tint = Color(0xff0B2EA0),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            lineHeight = 26.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (0.02 * 16).sp,
            color = Color(0xff0B2EA0),
        )
    }
}