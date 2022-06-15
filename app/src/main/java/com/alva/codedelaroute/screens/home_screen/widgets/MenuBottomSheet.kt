package com.alva.codedelaroute.screens.home_screen.widgets

import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.alva.codedelaroute.R

@Preview
@Composable
fun MenuBottomSheet() {
    val context = LocalContext.current

    Surface(color = Color(0xFFF1F1F1)) {
        LazyColumn {
            item {
                Box(contentAlignment = Alignment.TopCenter) {
                    BottomSheetHeader()
                    Divider(modifier = Modifier.padding(vertical = 4.dp).width(100.dp), thickness = 3.dp)
                }
                Spacer(modifier = Modifier.height(24.dp))
                BottomSheetItem(
                    icon = R.drawable.profile,
                    title = "Login",
                    trailingComposable = {
                        Icon(
                            Icons.Default.ArrowForwardIos,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFFC3C3C5)
                        )
                    },
                )
                Divider(color = Color(0xFFF1F1F1))
                BottomSheetItem(
                    icon = R.drawable.faqs,
                    title = "FAQS",
                    trailingComposable = {
                        Icon(
                            Icons.Default.ArrowForwardIos,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFFC3C3C5)
                        )
                    },
                )
                Spacer(modifier = Modifier.height(24.dp))
                BottomSheetItem(
                    icon = R.drawable.category,
                    title = "Select vehicle",
                    trailingComposable = {
                        Icon(
                            Icons.Default.ArrowForwardIos,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFFC3C3C5)
                        )
                    },
                )
                Divider(color = Color(0xFFF1F1F1))
                BottomSheetItem(
                    icon = R.drawable.location,
                    title = "Change State",
                    trailingComposable = {
                        Icon(
                            Icons.Default.ArrowForwardIos,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFFC3C3C5)
                        )
                    },
                )
                Divider(color = Color(0xFFF1F1F1))
                BottomSheetItem(
                    icon = R.drawable.calendar,
                    title = "Study Plan",
                    trailingComposable = {
                        Icon(
                            Icons.Default.ArrowForwardIos,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFFC3C3C5)
                        )
                    },
                )
                Spacer(modifier = Modifier.height(24.dp))
                BottomSheetItem(
                    icon = R.drawable.notification,
                    title = "Notification",
                    trailingComposable = {
                        val checkState = remember { mutableStateOf(true) }
                        Switch(
                            checked = checkState.value,
                            onCheckedChange = { checkState.value = !checkState.value },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFF0E31A3),
                                uncheckedThumbColor = Color(0xFF868687),
                            )
                        )
                    },
                )
                Divider(color = Color(0xFFF1F1F1))
                BottomSheetItem(
                    icon = R.drawable.time_circle,
                    title = "Remind me at",
                    trailingComposable = {
                        val time = remember { mutableStateOf("") }
                        val timePickerDialog = TimePickerDialog(
                            context,
                            { _, hour: Int, minute: Int ->
                                time.value = "${"%02d".format(hour)}:${
                                    "%02d".format(minute)
                                }"
                            }, 0, 0, true
                        )
                        Text(
                            text = if (time.value.equals("")) "17:34" else time.value,
                            textDecoration = TextDecoration.Underline,
                            color = Color(0xFF272728),
                            fontSize = 16.sp,
                            lineHeight = 26.sp,
                            modifier = Modifier.padding(horizontal = 10.dp).clickable {
                                timePickerDialog.show()
                            })
                    },
                )
                Divider(color = Color(0xFFF1F1F1))
                BottomSheetItem(
                    icon = R.drawable.discount,
                    title = "Dark mode",
                    trailingComposable = {
                        val checkState = remember { mutableStateOf(false) }
                        Switch(
                            checked = checkState.value,
                            onCheckedChange = { checkState.value = !checkState.value },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFF0E31A3),
                                uncheckedThumbColor = Color(0xFF868687),
                            )
                        )
                    },
                )
                Spacer(modifier = Modifier.height(24.dp))
                BottomSheetItem(
                    icon = R.drawable.info_square,
                    title = "Privacy Policy",
                    trailingComposable = {
                        Icon(
                            Icons.Default.ArrowForwardIos,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFFC3C3C5)
                        )
                    },
                )
                Divider(color = Color(0xFFF1F1F1))
                BottomSheetItem(
                    icon = R.drawable.team,
                    title = "Tell a friend",
                    trailingComposable = {},
                )
                Divider(color = Color(0xFFF1F1F1))
                BottomSheetItem(
                    icon = R.drawable.ticket,
                    title = "Rating",
                    trailingComposable = {},
                )
                Spacer(modifier = Modifier.height(24.dp))
                BottomSheetItem(
                    icon = R.drawable.more,
                    title = "Get PRO",
                    trailingComposable = {
                        Icon(
                            Icons.Default.ArrowForwardIos,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFFC3C3C5)
                        )
                    },
                )
                Divider(color = Color(0xFFF1F1F1))
                BottomSheetItem(
                    icon = R.drawable.more,
                    title = "Restore purchases",
                    trailingComposable = {},
                )
            }
        }
    }
}

@Composable
fun BottomSheetHeader() {
    Surface {
        Row(modifier = Modifier.padding(24.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier.size(50.dp), shape = RoundedCornerShape(CornerSize(6.dp))
            ) {
                Image(
                    painterResource(R.drawable.logo_bottom_sheet),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(50.dp),
                )
            }

            Column {
                Text("DMV Test", fontSize = 16.sp, lineHeight = 18.sp, fontWeight = FontWeight.SemiBold)
                Text(
                    "Version 3.2.4 (110)",
                    color = Color(0xFFB7B7B8),
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Thin,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@Composable
fun BottomSheetItem(
    icon: Int = R.drawable.profile, title: String = "Login", trailingComposable: @Composable () -> Unit = {
        Icon(
            Icons.Default.ArrowForwardIos,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color(0xFFC3C3C5)
        )
    }, onClick: () -> Unit = {}
) {
    Surface(modifier = Modifier.clickable {
        onClick.invoke()
    }) {
        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(Color(0xFF0E31A3))
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(title, fontSize = 16.sp, lineHeight = 26.sp)
            Spacer(modifier = Modifier.weight(1f))
            trailingComposable.invoke()
        }
    }
}