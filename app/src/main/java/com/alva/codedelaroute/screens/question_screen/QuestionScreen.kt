package com.alva.codedelaroute.screens.question_screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alva.codedelaroute.R
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding

@Preview
@Composable
fun QuestionScreen(navController: NavController = rememberNavController()) {
    var check = remember { mutableStateOf(QuestionCheck.None) }
    ProvideWindowInsets {
        Surface(modifier = Modifier.systemBarsPadding(true).fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Scaffold(
                topBar = {
                    SmallTopAppBar(navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null)
                        }
                    }, title = {
                        Text(
                            "Signed & Situations",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            lineHeight = 24.sp
                        )
                    }, actions = {
                        IconButton(onClick = {}) {
                            Image(
                                painter = painterResource(R.drawable.font_icon),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                colorFilter = ColorFilter.tint(Color.Black)
                            )
                        }
                        IconButton(onClick = {}) {
                            Image(
                                painter = painterResource(R.drawable.practice_icon),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                colorFilter = ColorFilter.tint(Color.Black)
                            )
                        }
                    })
                },
                backgroundColor = Color.Transparent,
                contentColor = Color.Transparent,
            ) { innerPadding ->
                Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    QuestionProgressBar()
                    QuestionContainer(check.value)
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn {
                        val list = mutableListOf<String>(
                            "You must not make left turn",
                            "You are approaching a traffic is land",
                            "You must make a right turn only.",
                            "There is a hidden intersection ahead."
                        )
                        items(items = list) {
                            Surface(
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                                shape = RoundedCornerShape(corner = CornerSize(12.dp))
                            ) {
                                Row(
                                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        it,
                                        modifier = Modifier.weight(1f),
                                        color = Color(0xFF4D4D4D),
                                        fontSize = 16.sp,
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    IconButton(onClick = {
                                        if (it.equals("You are approaching a traffic is land"))
                                            check.value = QuestionCheck.True
                                        else check.value = QuestionCheck.False
                                    }) {
                                        Icon(Icons.Default.Remove, contentDescription = null)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionProgressBar() {
    Surface(color = Color.Transparent, modifier = Modifier.fillMaxWidth()) {
        LinearProgressIndicator(
            color = Color(0xFFEBAD34),
            backgroundColor = Color.LightGray,
            progress = 0.6f,
        )
        LinearProgressIndicator(
            color = Color(0xFF00E291),
            backgroundColor = Color.Transparent,
            progress = 0.4f,
        )
    }
}

@Composable
fun QuestionContainer(check: QuestionCheck) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.padding(16.dp).fillMaxWidth().clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                .border(
                    BorderStroke(
                        1.dp,
                        color = if (check == QuestionCheck.True) Color(0xFF00C17C) else Color.LightGray
                    ), RoundedCornerShape(corner = CornerSize(16.dp))
                )
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("What does this road sign mean?", modifier = Modifier.weight(1f), fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(
                        painterResource(R.drawable.sign_test),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                }
                if (check == QuestionCheck.True) Spacer(modifier = Modifier.height(10.dp))
                if (check == QuestionCheck.True) Text(
                    "You will not see this question in a while",
                    color = Color(0xFF00C17C),
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                )
            }
        }
        Surface(
            modifier = Modifier.padding(start = 32.dp).background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(226, 228, 238), Color(235, 235, 240)
                    )
                )
            ), color = Color.Transparent
        ) {
            Text(
                if (check == QuestionCheck.True) "CORRECT" else "NEW QUESTION",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                lineHeight = 22.sp,
                color = if (check == QuestionCheck.True) Color(0xFF00C17C) else Color(0xFF4D4D4D),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

enum class QuestionCheck {
    None, True, False
}