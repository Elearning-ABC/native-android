@file:OptIn(ExperimentalComposeUiApi::class)

package com.alva.codedelaroute.screens.test_done_screen.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alva.codedelaroute.models.UITestInfo
import com.alva.codedelaroute.screens.review_list_screen.ReviewPanel
import com.alva.codedelaroute.view_models.TestInfoViewModel

@Composable
fun ReviewDialog(
    openReviewDialog: MutableState<Boolean>, testInfo: UITestInfo, testInfoViewModel: TestInfoViewModel = viewModel()
) {
    val questions = remember {
        testInfoViewModel.getQuestionListByTestInfo(testInfo = testInfo)
    }

    Dialog(
        onDismissRequest = { openReviewDialog.value = false },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            securePolicy = SecureFlagPolicy.SecureOn,
            usePlatformDefaultWidth = false,
        ),
    ) {
        Surface(
            modifier = Modifier.padding(20.dp).fillMaxWidth(), shape = RoundedCornerShape(corner = CornerSize(12.dp))
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Test Review", modifier = Modifier.weight(1f), fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    IconButton(onClick = { openReviewDialog.value = false }) {
                        Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(26.dp))
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                ReviewPanel(modifier = Modifier.weight(1f), questionList = questions, showAnswerInitialValue = true)
            }
        }
    }
}