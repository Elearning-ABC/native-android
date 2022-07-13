package com.alva.codedelaroute.screens.topic_screen.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alva.codedelaroute.models.UITopic
import com.alva.codedelaroute.models.UITopicProgress
import com.alva.codedelaroute.widgets.CustomLinearProgressBar

@Composable
fun TopicCard(topic: UITopic, modifier: Modifier = Modifier, subTopicProgress: UITopicProgress) {
    Surface(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp).fillMaxWidth().shadow(
            elevation = 10.dp,
            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
            ambientColor = Color(0xff002395).copy(alpha = 0.3f),
            spotColor = Color(0xff002395).copy(alpha = 0.3f),
        ), shape = RoundedCornerShape(corner = CornerSize(8.dp)), color = Color.White
    ) {
        val percentage = subTopicProgress.correctNumber.toFloat() / subTopicProgress.totalQuestionNumber

        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Text(
                topic.name,
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.SemiBold, color = Color.Black),
            )
            Text(
                topic.totalQuestion.toString() + " questions without time limit",
                style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Light, color = Color.DarkGray)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box() {
                CustomLinearProgressBar(
                    Modifier.height(8.dp).clip(shape = RoundedCornerShape(4.dp)),
                    Color(0xFFCAD1F5),
                    Color(0xFF002395),
                    percentage,
                )
            }
        }
    }
}