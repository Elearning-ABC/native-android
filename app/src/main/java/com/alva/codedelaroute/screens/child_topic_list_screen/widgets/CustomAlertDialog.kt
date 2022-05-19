package com.alva.codedelaroute.screens.child_topic_list_screen.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy

@Composable
fun CustomAlertDialog(
    openDialog: MutableState<Boolean>
) {
    AlertDialog(
        modifier = Modifier.padding(12.dp).fillMaxWidth().wrapContentHeight().padding(12.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        onDismissRequest = {
            openDialog.value = false
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "PLAY AGAIN",
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 28.sp,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                "Do you want to reset all progress of this test?", color = Color.Black,
                fontSize = 16.sp,
                lineHeight = 26.sp,
                textAlign = TextAlign.Center,
                letterSpacing = 0.32.sp
            )
        },
        buttons = {
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    shape = RoundedCornerShape(corner = CornerSize(12.dp)),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black),
                    onClick = { openDialog.value = false },
                    modifier = Modifier.padding(start = 12.dp, end = 6.dp).weight(1f)
                ) {
                    Text("Not Now")
                }
                OutlinedButton(
                    shape = RoundedCornerShape(corner = CornerSize(12.dp)),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF002395),
                        contentColor = Color.White
                    ),
                    onClick = { openDialog.value = false },
                    modifier = Modifier.padding(start = 6.dp, end = 12.dp).weight(1f)
                ) {
                    Text("Reset")
                }
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            securePolicy = SecureFlagPolicy.SecureOn
        ),
    )
}