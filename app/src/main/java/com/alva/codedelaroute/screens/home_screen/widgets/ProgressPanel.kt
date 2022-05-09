package com.alva.codedelaroute.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alva.codedelaroute.R

@Preview
@Composable
fun ProgressPanel() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        elevation = 10.dp,
        shape = RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp)
    ) {
        val state = remember {
            MutableTransitionState(false)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.cup_icon),
                    contentDescription = null,
                    modifier = Modifier.size(height = 55.dp, width = 52.dp),


                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.size(10.dp))
                ProgressInfoColumn(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.size(20.dp))
                Surface(
                    modifier = Modifier.size(height = 86.dp, width = 50.dp),
                    shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                    border = BorderStroke(width = 1.dp, Color(0xFF97A8E9))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            "Web", color = Color(0xFF0934C0), fontSize = 12.sp, fontWeight = FontWeight.Medium
                        )
                        Box(
                            modifier = Modifier.size(32.dp).clip(CircleShape)
                                .border(BorderStroke(width = 1.dp, Color(0xFF97A8E9)), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "11", color = Color(0xFF0934C0), fontSize = 12.sp, fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(visibleState = state) {
                Image(
                    painterResource(R.drawable.graph), contentDescription = null, modifier = Modifier.width(
                        LocalConfiguration.current.screenWidthDp.dp
                    ).height(LocalConfiguration.current.screenWidthDp.dp - 40.dp)
                )
            }

            Icon(
                if (state.currentState) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.clickable {
                    state.targetState = !state.currentState
                })
        }
    }
}

@Composable
fun ProgressInfoColumn(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Column {
            Text(
                "Complete your today plan to get a 91% passing rate",
                color = Color(0xFF002395),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                "Today plan: 6/20",
                fontSize = 16.sp,
                lineHeight = 21.28.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF2B5AF5),
            )
            Box(modifier = Modifier.padding(vertical = 12.dp)) {
                CustomProgressBar(
                    Modifier.height(4.dp).clip(shape = RoundedCornerShape(4.dp)),
                    Color(0xFFCAD1F5),
                    Color(0xFF2B5AF5),
                    0.3f,
                )
            }
        }
    }
}