package com.example.goaltracker.composable.statistic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.goaltracker.utils.ColorUtils
import com.example.goaltracker.utils.TextSizeUtils

@Composable
fun StatisticEllipse(percent: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(230.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val centerX = canvasWidth / 2f
            val centerY = canvasHeight / 2f

            val radius = if (canvasWidth < canvasHeight) canvasWidth / 3f else canvasHeight / 3f

            val angle = 360f * percent / 100f



            // Draw the complete ellipse with black color
            drawArc(
                color = Color.White,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
            )

            // Draw the filled portion of the ellipse with blue color
            drawArc(
                color = Color(ColorUtils.primary),
                startAngle = -90f,
                sweepAngle = angle,
                useCenter = false,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = "${percent.toInt()}%",
                modifier= Modifier.align(Alignment.CenterHorizontally),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = TextSizeUtils.LARGE
            )
            Text(
                text = "Mục tiêu đã hoàn thành",
                modifier= Modifier.align(Alignment.CenterHorizontally),
                color = Color.Black,
            )
        }
    }
}