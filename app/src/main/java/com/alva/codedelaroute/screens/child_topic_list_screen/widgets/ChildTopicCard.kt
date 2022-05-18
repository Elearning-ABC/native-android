package com.alva.codedelaroute.screens.child_topic_list_screen.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alva.codedelaroute.models.Topic
import com.alva.codedelaroute.models.TopicProgress
import com.alva.codedelaroute.widgets.CustomProgressBar

@Composable
fun ChildTopicCard(topic: Topic, modifier: Modifier = Modifier, subTopicProgress: TopicProgress) {
    Surface(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp).fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        color = Color.White.copy(alpha = 0.8f),
        elevation = 0.dp,
    ) {
        var percentage = subTopicProgress.correctNumber.toFloat() / subTopicProgress.totalQuestionNumber

        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Text(
                topic.name, style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
                topic.totalQuestion.toString() + " questions without time limit",
                style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Light, color = Color.DarkGray)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box() {
                CustomProgressBar(
                    Modifier.height(8.dp).clip(shape = RoundedCornerShape(4.dp)),
                    Color(0xFFCAD1F5),
                    Color(0xFF2B5AF5),
                    percentage,
                )
            }
        }
    }
}