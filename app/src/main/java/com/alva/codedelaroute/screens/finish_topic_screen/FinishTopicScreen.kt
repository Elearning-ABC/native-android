package com.alva.codedelaroute.screens.finish_topic_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.alva.codedelaroute.R
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.utils.ReviewQuestionProperty
import com.alva.codedelaroute.widgets.CustomAlertDialog
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.alva.codedelaroute.view_models.TopicViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun FinishTopicScreen(
    navController: NavController,
    subTopicId: String,
    topicViewModel: TopicViewModel = viewModel(
        viewModelStoreOwner = TopicViewModel.viewModelStoreOwner,
        key = TopicViewModel.key
    ),
    questionViewModel: QuestionViewModel = viewModel(
        viewModelStoreOwner = QuestionViewModel.viewModelStoreOwner,
        key = QuestionViewModel.key
    )
) {
    val subTopic = topicViewModel.getTopicById(subTopicId.toLong())
    val mainTopic = topicViewModel.getTopicById(subTopic.parentId.toLong())

    val nextTopicId = topicViewModel.getNextTopicId(subTopic)

    val openDialog = remember { mutableStateOf(false) }

    ProvideWindowInsets {
        Scaffold(topBar = {
            SmallTopAppBar(navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }, title = {
                Text(
                    mainTopic.name + ": " + subTopic.name,
                    color = Color(0xFF0C1827),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    lineHeight = 24.sp
                )
            })
        }, modifier = Modifier.systemBarsPadding()) {
            Surface(modifier = Modifier.padding(it).fillMaxSize(), color = Color.Transparent) {
                Image(
                    painter = painterResource(R.drawable.background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Column(modifier = Modifier.padding(vertical = 20.dp).fillMaxSize()) {
                    Column(
                        modifier = Modifier.padding(horizontal = 52.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Congratulations!",
                            textAlign = TextAlign.Center,
                            color = Color(0xFF002395),
                            fontSize = 24.sp,
                            lineHeight = 32.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Text(
                            "You’ve successfully completed part ${subTopic.name.last()}!",
                            textAlign = TextAlign.Center,
                            color = Color(0xFF272728),
                            fontSize = 16.sp,
                            lineHeight = 22.sp,
                        )
                        Spacer(modifier = Modifier.size(5.dp))
                        Box(modifier = Modifier.padding(horizontal = 32.dp, vertical = 10.dp)) {
                            Image(
                                painter = painterResource(R.drawable.success_human),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth, modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(
                            "Let's take a look at your study progress today",
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            fontSize = 18.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    Image(
                        painter = painterResource(R.drawable.graph_2),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth, modifier = Modifier.padding(start = 24.dp).fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ControlPanel(
                        navController,
                        nextTopicId,
                        openDialog
                    )
                }
            }
        }
    }

    if (openDialog.value) {
        CustomAlertDialog(
            openDialog,
            title = "LEARN AGAIN",
            description = "Do you want to reset all progress of this practice?",
            buttonAcceptTitle = "Reset",
            buttonCancelTitle = "Not Now",
            buttonAcceptClick = {
                runBlocking {
                    topicViewModel.clearSubTopicProgressData(
                        subTopicId = subTopicId.toLong(),
                        parentTopicId = mainTopic.id.toLong()
                    )
                    questionViewModel.clearQuestionProgressData(
                        subTopicId.toLong()
                    )
                    navController.popBackStack()
                    navController.navigate(Routes.QuestionScreen.name + "/${subTopicId}")
                }
                openDialog.value = false
            },
            buttonCancelClick = { openDialog.value = false },
        )
    }
}

@Composable
fun ControlPanel(
    navController: NavController,
    nextTopicId: String? = null,
    openDialog: MutableState<Boolean>
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        CustomButton(
            modifier = Modifier.weight(1f).padding(start = 24.dp, end = 4.dp, top = 10.dp),
            buttonColor = Color(0xFFED2939),
            textColor = Color.White,
            buttonTitle = "Try Again"
        ) {
            openDialog.value = true
        }

        CustomButton(
            modifier = Modifier.weight(1f).padding(start = 4.dp, end = 24.dp, top = 10.dp),
            buttonColor = Color(0xFF002395),
            textColor = Color.White,
            buttonTitle = if (nextTopicId == null) "Go back" else "Go to next part"
        ) {
            if (nextTopicId == null) {
                navController.popBackStack()
            } else {
                navController.popBackStack()
                navController.navigate(Routes.QuestionScreen.name + "/${nextTopicId}")
            }
        }
    }
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    buttonTitle: String,
    buttonColor: Color,
    textColor: Color,
    onClick: () -> Unit = {},
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor, contentColor = textColor),
        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
    ) {
        Text(buttonTitle, color = textColor, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, lineHeight = 22.sp)
    }
}
