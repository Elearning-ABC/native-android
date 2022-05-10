package com.alva.codedelaroute.screens.practice_screen.widgets

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ComponentRegistry
import coil.ImageLoader
import coil.compose.*
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.alva.codedelaroute.models.Topic
import com.alva.codedelaroute.ui.theme.TitleCardColor
import com.alva.codedelaroute.widgets.CustomProgressBar

@Composable
fun TopicCard(topic: Topic, modifier: Modifier = Modifier) {
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
                    topic.name, style = TextStyle(
                        color = TitleCardColor, fontSize = 24.sp, fontWeight = FontWeight.SemiBold, lineHeight = 32.sp
                    ), modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Box(
                    modifier = Modifier.size(30.dp).clip(RoundedCornerShape(corner = CornerSize(8.dp))).background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xff969BFF), Color(0xff002395)),
                            radius = 100f,
                            center = Offset(60f, 1f)
                        )
                    ), contentAlignment = Alignment.Center
                ) {
                    val svgLink = topic.icon
                    val imageLoader = ImageLoader.Builder(LocalContext.current)
                        .components {
                            add(SvgDecoder.Factory())
                        }
                        .build()
                    CompositionLocalProvider(LocalImageLoader provides imageLoader) {
                        val painter = rememberImagePainter(svgLink)
                        Image(
                            painter = painter,
                            contentDescription = "SVG Image",
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Row(modifier = Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1f)) {
                    CustomProgressBar(
                        Modifier.height(8.dp).clip(shape = RoundedCornerShape(4.dp)),
                        Color(0xFFCAD1F5),
                        Color(0xFF2B5AF5),
                        0.3f,
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("34%")
            }
        }
    }
}