package com.alva.codedelaroute.screens.game_screen.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.alva.codedelaroute.view_models.AppConfigurationViewModel
import com.smarttoolfactory.slider.LabelPosition
import com.smarttoolfactory.slider.MaterialSliderDefaults
import com.smarttoolfactory.slider.SliderBrushColor
import com.smarttoolfactory.slider.SliderWithLabel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun FontSizeDialog(
    openDialog: MutableState<Boolean>,
    fontSizeScale: MutableState<Float>,
) {

    val context = LocalContext.current

    val dataStore = AppConfigurationViewModel(context)

    val coroutine = rememberCoroutineScope()

    if (openDialog.value) {
        Dialog(
            onDismissRequest = { openDialog.value = false },
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true,
                securePolicy = SecureFlagPolicy.SecureOn
            )
        ) {

            Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(corner = CornerSize(8.dp))) {
                Column(
                    modifier = Modifier.padding(horizontal = 30.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Change Font Size",
                        color = Color.Black,
                        fontSize = 18.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    SliderWithLabel(
                        value = fontSizeScale.value,
                        onValueChange = {
                            coroutine.launch {
                                fontSizeScale.value = it
                                dataStore.saveFontSizeScale(it)
                            }
                        },
                        thumbRadius = 10.dp,
                        trackHeight = 10.dp,
                        valueRange = 0.8f..2.4f,
                        colors = MaterialSliderDefaults.materialColors(
                            thumbColor = SliderBrushColor(color = Color(0xFF0E31A3)),
                            inactiveTrackColor = SliderBrushColor(color = Color(0xFF868687)),
                            activeTrackColor = SliderBrushColor(color = Color(0xff2752a3))
                        ),
                        borderStroke = BorderStroke(1.dp, Color.Black),
                        labelPosition = LabelPosition.Top,
                        label = {
                            Text(
                                text = "%.1f".format(fontSizeScale.value),
                                modifier = Modifier
                                    .shadow(1.dp, shape = CircleShape)
                                    .background(Color(0xFF0E31A3), shape = CircleShape)
                                    .padding(5.dp),
                                color = Color.White
                            )
                        },
                        coerceThumbInTrack = true
                    )
                }
            }
        }
    }
}