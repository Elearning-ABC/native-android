package com.alva.codedelaroute.screens.home_screen.tabs.test_tab.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alva.codedelaroute.utils.TitleCardColor
import com.alva.codedelaroute.widgets.CustomLinearProgressBar

import com.alva.codedelaroute.R
import com.alva.codedelaroute.models.UITestInfo
import com.alva.codedelaroute.utils.TestIconStatus
import com.alva.codedelaroute.view_models.TestInfoViewModel
import com.alva.codedelaroute.widgets.CustomToast
import kotlin.math.roundToInt

@Composable
fun TestCard(modifier: Modifier = Modifier, testInfo: UITestInfo, onClick: () -> Unit) {
    val testInfoViewModel = viewModel<TestInfoViewModel>()
    val lockStatus = testInfoViewModel.getOverallTestLockStatus(testInfoId = testInfo.id)
    val allTestProgress = testInfoViewModel.getAllTestProgressByTestInfoId(testInfoId = testInfo.id)

    val testPercentage = testInfoViewModel.getOverallTestProgress(testProgressList = allTestProgress)

    val context = LocalContext.current

    Card(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp).fillMaxWidth().shadow(
            elevation = 10.dp,
            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
            ambientColor = Color(0xff002395).copy(alpha = 0.3f),
            spotColor = Color(0xff002395).copy(alpha = 0.3f),
        ).clickable {
            if (lockStatus) {
                CustomToast.showToast(
                    context = context,
                    message = "Function unavailable at this time",
                    icon = R.drawable.alert_triangle,
                    textColor = R.color.toast_alert_text_color,
                    toastBackgroundColor = R.color.toast_alert_background_color
                )
            } else {
                onClick.invoke()
            }
        },
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        backgroundColor = Color.White,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    testInfo.title + " " + (testInfo.index + 1),
                    color = TitleCardColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 32.sp,
                    modifier = Modifier.weight(1f)
                )
                if (lockStatus) Spacer(modifier = Modifier.size(16.dp))
                if (lockStatus) Box(
                    modifier = Modifier.size(24.dp).clip(RoundedCornerShape(corner = CornerSize(8.dp))),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.lock_icon),
                        contentDescription = null,
                        modifier = Modifier.padding(1.dp)
                    )
                } else {
                    when (testInfoViewModel.checkTestInfoPassed(testInfo, allTestProgress)) {
                        TestIconStatus.Passed -> Box(
                            modifier = Modifier.size(24.dp).clip(RoundedCornerShape(corner = CornerSize(8.dp))),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF00C17C))
                        }
                        TestIconStatus.Failed -> Box(
                            modifier = Modifier.size(24.dp).clip(RoundedCornerShape(corner = CornerSize(8.dp))),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = Color(0xffEE7874))
                        }
                        else -> {}
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Row(modifier = Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1f)) {
                    CustomLinearProgressBar(
                        Modifier.height(8.dp).clip(shape = RoundedCornerShape(4.dp)),
                        Color(0xFFCAD1F5),
                        Color(0xFF002395),
                        testPercentage / 100,
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("${testPercentage.roundToInt()}%")
            }
        }
    }
}