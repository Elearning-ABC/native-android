package com.alva.codedelaroute.screens.home_screen.tabs.review_tab.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alva.codedelaroute.utils.TitleCardColor

import com.alva.codedelaroute.R
import com.alva.codedelaroute.utils.ReviewQuestionProperty

@Composable
fun ReviewCard(
    cardTitle: String,
    reviewQuestionProperty: ReviewQuestionProperty,
    modifier: Modifier = Modifier,
    questionCount: Int
) {
    val icon = when (reviewQuestionProperty) {
        ReviewQuestionProperty.WeakQuestions -> R.drawable.ic_weak
        ReviewQuestionProperty.MediumQuestions -> R.drawable.ic_medium
        ReviewQuestionProperty.StrongQuestions -> R.drawable.ic_strong
        ReviewQuestionProperty.AllFamiliarQuestions -> R.drawable.ic_list
        ReviewQuestionProperty.YourFavoriteQuestions -> R.drawable.ic_favorites
        else -> R.drawable.ic_weak
    }

    Card(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp).fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        backgroundColor = Color.White,
        elevation = 10.dp
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    cardTitle, style = TextStyle(
                        color = TitleCardColor, fontSize = 24.sp, fontWeight = FontWeight.SemiBold, lineHeight = 32.sp
                    ), modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Box(
                    modifier = Modifier.size(24.dp).clip(RoundedCornerShape(corner = CornerSize(8.dp))).background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xff969BFF), Color(0xFF002395)),
                            radius = 100f,
                            center = Offset(60f, 1f)
                        )
                    ), contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(icon),
                        contentDescription = null,
                        modifier = Modifier.padding(3.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Row(modifier = Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("$questionCount questions")
            }
        }
    }
}