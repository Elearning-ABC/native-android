package com.alva.codedelaroute.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.alva.codedelaroute.R

@Preview
@Composable
fun CommonAppBar() {
    SmallTopAppBar(navigationIcon = {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Menu, contentDescription = null
            )
        }
    }, title = {
        Card(
            Modifier
                .fillMaxWidth().height(40.dp),
            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
            elevation = 0.dp,
        ) {
            Image(
                painter = painterResource(id = R.drawable.premium_background),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                Text(
                    "GET PRO",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                )
            }
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 24.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.premium_icon),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.White
                )
            }
        }
    }, actions = {
        IconButton(
            onClick = {}
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_icon),
                contentDescription = null,
                modifier = Modifier.size(33.33.dp),
            )
        }
    }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White)
    )
}