package com.alva.codedelaroute.widgets

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.consumeDownChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alva.codedelaroute.models.BarArea
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun BarChartCanvas(list: List<Int>, barSelected: (Int) -> Unit) {
    val density = LocalDensity.current
    val skyBlue400 = Color(0xff57A5FF)
    val horizontalPadding = with(density) { 20.dp.toPx() }
    val distance = with(density) { 50.dp.toPx() }
    val calculatedWidth = with(density) { (distance.times(list.size) + horizontalPadding.times(2)).toDp() }
    Box {
        Box(modifier = Modifier.fillMaxWidth().padding(24.dp).height(260.dp).align(Alignment.BottomCenter)) {
            val dividerHorizontalPadding = with(density) {
                12.dp.toPx()
            }
            val dividerVerticalPadding = with(density) {
                20.dp.toPx()
            }
            val textPositionY = with(density) {
                5.dp.toPx()
            }
            val textPaintLeft = Paint().apply {
                color = 0xffff47586B.toInt()
                textAlign = Paint.Align.LEFT
                this.textSize = 30f
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
            val textPaintRight = Paint().apply {
                color = 0xffff47586B.toInt()
                textAlign = Paint.Align.RIGHT
                this.textSize = 30f
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
            val numberTextPaintLeft = Paint().apply {
                color = 0xffff47586B.toInt()
                textAlign = Paint.Align.CENTER
                this.textSize = 24f
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
            val numberTextPaintRight = Paint().apply {
                color = 0xffff47586B.toInt()
                textAlign = Paint.Align.CENTER
                this.textSize = 24f
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
            Canvas(modifier = Modifier.fillMaxSize()) {
                val lineDistance = (size.height - dividerVerticalPadding).div(4)

                drawLine(
                    color = Color.Gray,
                    start = Offset(dividerHorizontalPadding, dividerVerticalPadding),
                    end = Offset(dividerHorizontalPadding, size.height)
                )

                drawLine(
                    color = Color.Gray,
                    start = Offset(size.width - dividerHorizontalPadding, dividerVerticalPadding),
                    end = Offset(size.width - dividerHorizontalPadding, size.height)
                )
                this.drawIntoCanvas { canvas ->
                    canvas.nativeCanvas.drawText(
                        "Question Today", 0f, textPositionY, textPaintLeft
                    )
                    canvas.nativeCanvas.drawText(
                        "Pass Rate", size.width, textPositionY, textPaintRight
                    )
                    canvas.nativeCanvas.drawText(
                        "0", 0f, size.height, numberTextPaintLeft
                    )
                    canvas.nativeCanvas.drawText(
                        "50", 0f, size.height - lineDistance, numberTextPaintLeft
                    )
                    canvas.nativeCanvas.drawText(
                        "100", 0f, size.height - lineDistance.times(2), numberTextPaintLeft
                    )
                    canvas.nativeCanvas.drawText(
                        "0", size.width, size.height - lineDistance.times(2), numberTextPaintRight
                    )
                    canvas.nativeCanvas.drawText(
                        "50", size.width, size.height - lineDistance.times(3), numberTextPaintRight
                    )
                    canvas.nativeCanvas.drawText(
                        "100", size.width, size.height - lineDistance.times(4), numberTextPaintRight
                    )
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 36.dp, vertical = 24.dp).height(240.dp)
                .align(Alignment.BottomCenter)
        ) {
            Row(
                Modifier.fillMaxSize().horizontalScroll(rememberScrollState())
            ) {
                val barWidth = with(density) { 18.dp.toPx() }
                val selectionWidth = with(density) { 24.dp.toPx() }
                val smallPadding = with(density) { 4.dp.toPx() }
                val textSize = with(density) { 10.sp.toPx() }
                val cornerRadius = with(density) { 4.dp.toPx() }
                val labelSectionHeight = smallPadding.times(2) + textSize
                val barAreas = list.mapIndexed { index, i ->
                    BarArea(
                        index = index,
                        value = i,
                        xStart = horizontalPadding + distance.times(index) - distance.div(2),
                        xEnd = horizontalPadding + distance.times(index) + distance.div(2)
                    )
                }
                var selectedPosition by remember { mutableStateOf(barAreas.first().xStart.plus(1f)) }
                var tempPosition by remember { mutableStateOf(-1000f) }
                val selectedBar by remember(selectedPosition, barAreas) {
                    derivedStateOf {
                        barAreas.find { it.xStart < selectedPosition && selectedPosition < it.xEnd }
                    }
                }
                val paint = Paint().apply {
                    color = 0xffff47586B.toInt()
                    textAlign = Paint.Align.CENTER
                    this.textSize = textSize
                }
                val scope = rememberCoroutineScope()
                val animatable = remember { Animatable(1f) }
                val tempAnimatable = remember { Animatable(0f) }

                Canvas(modifier = Modifier.fillMaxHeight().width(calculatedWidth).tapOrPress(onStart = { position ->
                    scope.launch {
                        selectedBar?.let { selected ->
                            if (position in selected.xStart..selected.xEnd) {
                                // click in selected area - do nothing
                            } else {
                                tempPosition = position
                                scope.launch {
                                    tempAnimatable.snapTo(0f)
                                    tempAnimatable.animateTo(1f, animationSpec = tween(300))
                                }
                            }

                        }
                    }
                }, onCancel = {
                    tempPosition = -Int.MAX_VALUE.toFloat()
                    scope.launch {
                        tempAnimatable.animateTo(0f)
                    }
                }, onCompleted = {
                    val currentSelected = selectedBar
                    scope.launch {
                        selectedPosition = it
                        animatable.snapTo(tempAnimatable.value)
                        selectedBar?.value?.let { value ->
                            barSelected(value)
                        }
                        async {
                            animatable.animateTo(
                                1f, animationSpec = tween(
                                    300.times(1f - tempAnimatable.value).roundToInt()
                                )
                            )
                        }
                        async {
                            tempAnimatable.snapTo(0f)
                            currentSelected?.let {
                                tempPosition = currentSelected.xStart.plus(1f)
                                tempAnimatable.snapTo(1f)
                                tempAnimatable.animateTo(0f, tween(300))
                            }
                        }
                    }
                })) {
                    val lineDistance = size.height.div(4)
                    repeat(5) {
                        drawLine(
                            color = Color.Gray,
                            start = Offset(0f, it.times(lineDistance)),
                            end = Offset(size.width, it.times(lineDistance))
                        )
                    }
                    val scale = calculateScale((size.height / 2 - smallPadding).roundToInt(), list)
                    val chartAreaBottom = size.height - labelSectionHeight
                    val lineChartAreaBottom = size.height / 2 - labelSectionHeight

                    barAreas.forEachIndexed { index, item ->
                        val barHeight = item.value.times(scale).toFloat()
                        if (index < list.size - 1) {
                            val nextBarHeight = barAreas[index + 1].value.times(scale).toFloat()
                            drawLine(color = skyBlue400, start = Offset(
                                x = horizontalPadding + distance.times(index), y = size.height / 2 - barHeight
                            ), end = Offset(
                                x = horizontalPadding + distance.times(index + 1),
                                y = size.height / 2 - nextBarHeight
                            ), strokeWidth = with(density) {
                                5.dp.toPx()
                            })
                            drawCircle(
                                color = Color(0xff757d7d).copy(alpha = 0.6f),
                                radius = with(density) {
                                    6.2.dp.toPx()
                                },
                                center = Offset(
                                    x = horizontalPadding + distance.times(index), y = size.height / 2 - barHeight
                                ),
                            )
                            drawCircle(
                                color = Color(0xff00CA9F),
                                radius = with(density) {
                                    6.dp.toPx()
                                },
                                center = Offset(
                                    x = horizontalPadding + distance.times(index), y = size.height / 2 - barHeight
                                ),
                            )
                            drawCircle(
                                color = Color.White,
                                radius = with(density) {
                                    5.dp.toPx()
                                },
                                center = Offset(
                                    x = horizontalPadding + distance.times(index), y = size.height / 2 - barHeight
                                ),
                            )
                        } else {
                            drawCircle(
                                color = Color(0xff757d7d).copy(alpha = 0.6f),
                                radius = with(density) {
                                    6.2.dp.toPx()
                                },
                                center = Offset(
                                    x = horizontalPadding + distance.times(index), y = size.height / 2 - barHeight
                                ),
                            )
                            drawCircle(
                                color = Color.White,
                                radius = with(density) {
                                    6.dp.toPx()
                                },
                                center = Offset(
                                    x = horizontalPadding + distance.times(index), y = size.height / 2 - barHeight
                                ),
                            )
                            drawCircle(
                                color = Color(0xff00CA9F),
                                radius = with(density) {
                                    5.dp.toPx()
                                },
                                center = Offset(
                                    x = horizontalPadding + distance.times(index), y = size.height / 2 - barHeight
                                ),
                            )
                        }
                        this.drawIntoCanvas { canvas ->
                            val textPositionY = lineChartAreaBottom - barHeight + smallPadding
                            canvas.nativeCanvas.drawText(
                                "${item.value}", horizontalPadding + distance.times(index), textPositionY, paint
                            )
                        }
                    }

                    barAreas.forEachIndexed { index, item ->
                        val barHeight = item.value.times(scale).toFloat()
                        drawRoundRect(
                            color = skyBlue400, topLeft = Offset(
                                x = horizontalPadding + distance.times(index) - barWidth.div(2),
                                y = size.height - barHeight
                            ), size = Size(barWidth, barHeight), cornerRadius = CornerRadius(cornerRadius)
                        )
                        this.drawIntoCanvas { canvas ->
                            val textPositionY = chartAreaBottom - barHeight + smallPadding
                            canvas.nativeCanvas.drawText(
                                "${item.value}", horizontalPadding + distance.times(index), textPositionY, paint
                            )
                        }
                        this.drawIntoCanvas { canvas ->
                            val textPositionY = size.height + smallPadding + textSize
                            canvas.nativeCanvas.drawText(
                                "$index", horizontalPadding + distance.times(index), textPositionY, paint
                            )
                        }
                    }
                    if (selectedBar != null) {
                        drawRoundRect(
                            brush = Brush.verticalGradient(
                                listOf(
                                    skyBlue400.copy(alpha = 0.3f), Color.Transparent
                                )
                            ), topLeft = Offset(
                                x = horizontalPadding + distance.times(selectedBar!!.index) - selectionWidth.div(
                                    2
                                ), y = size.height - size.height.times(animatable.value)
                            ), size = Size(selectionWidth, size.height), cornerRadius = CornerRadius(cornerRadius)
                        )
                    }
                }
            }
        }
    }
}

fun calculateScale(viewHeightPx: Int, values: List<Int>): Double {
    return values.maxOrNull()?.let { max ->
        viewHeightPx.times(0.8).div(max)
    } ?: 1.0
}

fun Modifier.tapOrPress(
    onStart: (offsetX: Float) -> Unit, onCancel: (offsetX: Float) -> Unit, onCompleted: (offsetX: Float) -> Unit
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    this.pointerInput(interactionSource) {
        forEachGesture {
            coroutineScope {
                awaitPointerEventScope {
                    val tap = awaitFirstDown().also { it.consumeDownChange() }
                    onStart(tap.position.x)
                    val up = waitForUpOrCancellation()
                    if (up == null) {
                        onCancel(tap.position.x)
                    } else {
                        up.consumeDownChange()
                        onCompleted(tap.position.x)
                    }
                }
            }
        }
    }
}