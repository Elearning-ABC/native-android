package com.alva.codedelaroute.screens.home_screen.tabs.test_tab.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alva.codedelaroute.utils.TitleCardColor
import com.alva.codedelaroute.widgets.CustomProgressBar

import com.alva.codedelaroute.R

@Composable
fun TestCard(modifier: Modifier = Modifier, number: Int) {
    Card(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp).fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        backgroundColor = Color.White,
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    "Practice test $number", style = TextStyle(
                        color = TitleCardColor, fontSize = 24.sp, fontWeight = FontWeight.SemiBold, lineHeight = 32.sp
                    ), modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Box(
                    modifier = Modifier.size(24.dp).clip(RoundedCornerShape(corner = CornerSize(8.dp))),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.lock_icon),
                        contentDescription = null,
                        modifier = Modifier.padding(1.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Row(modifier = Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1f)) {
                    CustomProgressBar(
                        Modifier.height(8.dp).clip(shape = RoundedCornerShape(4.dp)),
                        Color(0xFFCAD1F5),
                        Color(0xFF002395),
                        0f,
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("0%")
            }
        }
    }
}