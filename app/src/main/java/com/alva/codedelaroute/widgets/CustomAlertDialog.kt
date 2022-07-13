@file:OptIn(DelicateCoroutinesApi::class, ExperimentalComposeUiApi::class)

package com.alva.codedelaroute.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.utils.PoppinsFont
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.alva.codedelaroute.view_models.TopicViewModel
import kotlinx.coroutines.*

@Composable
fun CustomAlertDialog(
    openDialog: MutableState<Boolean>,
    title: String,
    description: String,
    buttonCancelTitle: String,
    buttonAcceptTitle: String,
    buttonCancelClick: () -> Unit,
    buttonAcceptClick: () -> Unit
) {
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
            modifier = Modifier.padding(30.dp).fillMaxWidth().wrapContentHeight(),
            shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        ) {
            Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    fontFamily = PoppinsFont,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(32.dp))
                Text(
                    description,
                    color = Color.Black,
                    fontSize = 16.sp,
                    lineHeight = 26.sp,
                    fontFamily = PoppinsFont,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.32.sp
                )
                Spacer(modifier = Modifier.size(24.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black),
                        onClick = { buttonCancelClick() },
                        modifier = Modifier.padding(end = 6.dp).weight(1f)
                    ) {
                        Text(buttonCancelTitle, fontFamily = PoppinsFont)
                    }
                    OutlinedButton(
                        shape = RoundedCornerShape(corner = CornerSize(12.dp)), colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF002395), contentColor = Color.White
                        ), onClick = { buttonAcceptClick() }, modifier = Modifier.padding(start = 6.dp).weight(1f)
                    ) {
                        Text(buttonAcceptTitle, fontFamily = PoppinsFont)
                    }
                }
            }
        }
    }
}