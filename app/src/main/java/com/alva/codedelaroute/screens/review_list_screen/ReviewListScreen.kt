package com.alva.codedelaroute.screens.review_list_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.alva.codedelaroute.R
import com.alva.codedelaroute.models.UIAnswer
import com.alva.codedelaroute.models.UIQuestion
import com.alva.codedelaroute.navigations.Routes
import com.alva.codedelaroute.screens.review_list_screen.widgets.ReviewButton
import com.alva.codedelaroute.utils.ReviewQuestionProperty
import com.alva.codedelaroute.view_models.QuestionViewModel
import com.alva.codedelaroute.widgets.CustomToast
import kotlinx.coroutines.runBlocking
import java.io.IOException

@Composable
fun ReviewListScreen(
    navController: NavController,
    reviewQuestionProperty: ReviewQuestionProperty,
    questionViewModel: QuestionViewModel = viewModel()
) {
    val questionListByReviewQuestionProperty = when (reviewQuestionProperty) {
        ReviewQuestionProperty.YourFavoriteQuestions -> questionViewModel.getAllFavoriteQuestions()
        ReviewQuestionProperty.AllFamiliarQuestions -> questionViewModel.getAllFamiliarQuestionsList()
        else -> questionViewModel.getQuestionListByReviewProperty(reviewQuestionProperty)
    }

    Surface(modifier = Modifier
        .systemBarsPadding()
        .fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Scaffold(modifier = Modifier.fillMaxSize(),
            backgroundColor = Color.Transparent,
            topBar = {
                SmallTopAppBar(navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                    title = {
                        Text(
                            "${reviewQuestionProperty.name} (${questionListByReviewQuestionProperty.size})",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            lineHeight = 24.sp
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent)
                )
            }) { innerPadding ->
            Column(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()) {
                ReviewPanel(
                    modifier = Modifier.weight(1f),
                    questionList = questionListByReviewQuestionProperty,
                    showAnswerInitialValue = false
                )
                ReviewButton(Modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp)) {
                    navController.navigate(
                        Routes.ReviewGameScreen.name + "/${reviewQuestionProperty.name}",
                    )
                }
            }
        }
    }
}

@Composable
fun ReviewPanel(
    modifier: Modifier = Modifier,
    questionList: MutableList<UIQuestion>,
    showAnswerInitialValue: Boolean,
    questionViewModel: QuestionViewModel = viewModel(),
) {

    val context = LocalContext.current

    LazyColumn(modifier = modifier) {
        items(items = questionList) { item ->
            val showAnswer = rememberSaveable { mutableStateOf(showAnswerInitialValue) }
            val progressList = questionViewModel.getProgressByQuestionId(item.id.toLong())
            val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }
            val choices = item.choices.shuffled()

            try {
                with(context.assets.open("images/${item.image}")) {
                    imageBitmap.value =
                        android.graphics.BitmapFactory.decodeStream(this).asImageBitmap()
                }
            } catch (e: IOException) {
                imageBitmap.value = null
            }

            ReviewItemCard(
                showAnswer = showAnswer,
                progressList = progressList,
                item = item,
                imageBitmap = imageBitmap,
                choices = choices,
                questionViewModel = questionViewModel,
                questionId = item.id,
            )
        }
        item {
            Spacer(modifier = Modifier.size(50.dp))
        }
    }
}

@Composable
fun ReviewItemCard(
    showAnswer: MutableState<Boolean>,
    progressList: MutableList<Int>,
    item: UIQuestion,
    imageBitmap: MutableState<ImageBitmap?>,
    choices: List<UIAnswer>,
    questionViewModel: QuestionViewModel,
    questionId: String,
) {
    val questionProgress =
        questionViewModel.getQuestionProgressByQuestionId(questionId.toLong(), isForChecking = true)

    val bookmark = remember { mutableStateOf(questionProgress.bookmark) }

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(top = 16.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                ambientColor = Color(0xff002395).copy(alpha = 0.3f),
                spotColor = Color(0xff002395).copy(alpha = 0.3f),
            )
            .clickable {
                showAnswer.value = !showAnswer.value
            },
        shape = RoundedCornerShape(corner = CornerSize(8.dp)), backgroundColor = Color.White,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                LazyRow(modifier = Modifier.weight(1f)) {
                    items(items = progressList) {
                        if (it == 1) Icon(
                            Icons.Default.Check,
                            tint = Color(0xFF00C17C),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(2.dp)
                                .size(10.dp)
                        )
                        else {
                            Icon(
                                Icons.Default.Close,
                                tint = Color(0xffEE7874),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(2.dp)
                                    .size(10.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Icon(if (bookmark.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = Color(0xFF0B2EA0),
                    modifier = Modifier.clickable {
                        runBlocking {
                            bookmark.value = !bookmark.value
                            questionViewModel.saveBookmarkToRepo(
                                questionProgress.questionId.toLong(),
                                questionProgress.topicId.toLong(),
                                boolean = bookmark.value
                            )
                            if (bookmark.value) {
                                CustomToast.showToast(
                                    context = context,
                                    message = "Added to favorites!",
                                    icon = R.drawable.check_circle,
                                    textColor = R.color.toast_success_text_color,
                                    toastBackgroundColor = R.color.toast_success_background_color,
                                )
                            } else {
                                CustomToast.showToast(
                                    context = context,
                                    message = "Removed from favorites!",
                                    icon = R.drawable.check_circle,
                                    textColor = R.color.toast_success_text_color,
                                    toastBackgroundColor = R.color.toast_success_background_color,
                                )
                            }
                        }
                    })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(
                    item.text, modifier = Modifier.weight(1f), fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
                imageBitmap.value?.apply {
                    Image(
                        bitmap = this, contentDescription = null, modifier = Modifier.size(60.dp)
                    )
                }
            }
            AnimatedVisibility(visible = showAnswer.value) {
                Column(modifier = Modifier.wrapContentSize()) {
                    Spacer(modifier = Modifier.size(24.dp))
                    for (choice in choices) {
                        if (choice.isCorrect) {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.Transparent,
                                shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                                border = BorderStroke(1.dp, color = Color(0xff00C17C))
                            ) {
                                Column(modifier = Modifier.padding(7.dp)) {
                                    Text(
                                        choice.text,
                                        modifier = Modifier.padding(vertical = 3.dp),
                                        color = Color(0xFF4D4D4D),
                                        fontSize = 16.sp,
                                    )
                                    Divider(color = Color(0xffC4C4C4))
                                    Text(
                                        item.explanation,
                                        modifier = Modifier.padding(vertical = 3.dp),
                                        color = Color(0xFF4D4D4D),
                                        fontSize = 16.sp,
                                    )
                                }
                            }
                        } else {
                            Text(
                                choice.text,
                                modifier = Modifier.padding(
                                    vertical = 10.dp, horizontal = 8.dp
                                ),
                                color = Color(0xFF4D4D4D),
                                fontSize = 16.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}