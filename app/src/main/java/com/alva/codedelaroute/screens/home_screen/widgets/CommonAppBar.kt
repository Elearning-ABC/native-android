package com.alva.codedelaroute.screens.home_screen.widgets

import android.os.Build.VERSION.SDK_INT
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest

import com.alva.codedelaroute.R
import com.alva.codedelaroute.view_models.OnStartViewModel
import com.alva.codedelaroute.widgets.GifImage
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Preview
@Composable
fun CommonAppBar(
    onStartViewModel: OnStartViewModel = viewModel(
        viewModelStoreOwner = OnStartViewModel.viewModelStoreOwner,
        key = OnStartViewModel.key
    )
) {
    val ticks = remember { mutableStateOf(onStartViewModel.getTickerValue()) }

    SmallTopAppBar(navigationIcon = {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Menu, contentDescription = null
            )
        }
    }, title = {
        LaunchedEffect(Unit) {
            while (ticks.value <= 19) {
                delay(50)
                ticks.value++
                onStartViewModel.increaseTickerValue(1)
            }
        }
        if (ticks.value <= 19) {
            Surface(shape = RoundedCornerShape(corner = CornerSize(8.dp))) {
                GifImage(
                    gifId = R.raw.button_get_pro,
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    contentScale = ContentScale.Crop
                )
            }
        } else {
            Surface(shape = RoundedCornerShape(corner = CornerSize(8.dp))) {
                Image(
                    painterResource(R.drawable.button_get_pro),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
//        Card(
//            Modifier
//                .fillMaxWidth().height(40.dp),
//            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
//            elevation = 0.dp,
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.premium_background),
//                contentDescription = null,
//                contentScale = ContentScale.Crop
//            )
//            Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
//                Text(
//                    "GET PRO",
//                    style = TextStyle(
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 16.sp,
//                        color = Color.White,
//                    )
//                )
//            }
//            Box(
//                contentAlignment = Alignment.CenterStart,
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .padding(start = 24.dp),
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.premium_icon),
//                    contentDescription = null,
//                    modifier = Modifier.size(18.dp),
//                    tint = Color.White
//                )
//            }
//        }
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