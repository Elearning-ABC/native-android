package com.alva.codedelaroute.screens.home_screen.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alva.codedelaroute.R
import com.alva.codedelaroute.view_models.OnStartViewModel
import com.alva.codedelaroute.widgets.GifImage
import com.alva.codedelaroute.widgets.TopUpDialog
import kotlinx.coroutines.delay

@ExperimentalMaterialApi
@Composable
fun HomeAppBar(
    onStartViewModel: OnStartViewModel = viewModel(),
    onClickCallBack: (String) -> Unit = {}
) {
    val ticks = remember { mutableStateOf(onStartViewModel.getTickerValue()) }

    val openTopUpDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (ticks.value <= 19) {
            delay(50)
            ticks.value++
            onStartViewModel.increaseTickerValue(1)
        }
    }

    if (openTopUpDialog.value) {
        TopUpDialog(openTopUpDialog)
    }

    SmallTopAppBar(navigationIcon = {
        IconButton(onClick = {
            onClickCallBack("menu")
        }) {
            Icon(
                imageVector = Icons.Default.Menu, contentDescription = null
            )
        }
    }, title = {
        if (ticks.value <= 19) {
            Surface(shape = RoundedCornerShape(corner = CornerSize(8.dp)), color = Color.White) {
                GifImage(
                    gifId = R.raw.button_get_pro,
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    contentScale = ContentScale.Crop
                )
            }
        } else {
            Surface(
                shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                modifier = Modifier.clickable { openTopUpDialog.value = true }) {
                Image(
                    painterResource(R.drawable.button_get_pro),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }, actions = {
        IconButton(
            onClick = {
                onClickCallBack("profile")
            }
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