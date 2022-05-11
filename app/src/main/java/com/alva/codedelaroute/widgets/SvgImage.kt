package com.alva.codedelaroute.widgets

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun SvgImage(
    svgLink: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    color: Color = Color.Black
) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()
    CompositionLocalProvider(LocalImageLoader provides imageLoader) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(svgLink).crossfade(true)
                .build()
        )
        Image(
            painter = painter,
            contentDescription = "SVG Image",
            contentScale = contentScale,
            colorFilter = ColorFilter.tint(color),
            modifier = modifier
        )
    }
}