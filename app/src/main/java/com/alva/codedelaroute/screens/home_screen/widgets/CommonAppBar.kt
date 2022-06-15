@file:OptIn(ExperimentalMaterialApi::class)

package com.alva.codedelaroute.screens.home_screen.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

import com.alva.codedelaroute.R
import com.alva.codedelaroute.view_models.OnStartViewModel
import com.alva.codedelaroute.widgets.GifImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun CommonAppBar(
    bottomSheetState: ModalBottomSheetState,
    onStartViewModel: OnStartViewModel = viewModel(
        viewModelStoreOwner = LocalViewModelStoreOwner.current!!,
    ),
    onClickCallBack: (String) -> Unit = {}
) {
    val ticks = remember { mutableStateOf(onStartViewModel.getTickerValue()) }
    val coroutineScope = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(bottomSheetState) {
        snapshotFlow { bottomSheetState.isVisible }.collect { isVisible ->
            if (!isVisible) {
                keyboardController?.hide()
            } else {
            }
        }
    }

    SmallTopAppBar(navigationIcon = {
        IconButton(onClick = {
            coroutineScope.launch {
                onClickCallBack("menu")
                if (!bottomSheetState.isVisible) {
                    bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                } else {
                    bottomSheetState.animateTo(ModalBottomSheetValue.Hidden)
                }
            }
        }) {
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
            Surface(shape = RoundedCornerShape(corner = CornerSize(8.dp)), color = Color.White) {
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
            onClick = {
                coroutineScope.launch {
                    onClickCallBack("profile")
                    if (!bottomSheetState.isVisible) {
                        bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                    } else {
                        bottomSheetState.animateTo(ModalBottomSheetValue.Hidden)
                    }
                }
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