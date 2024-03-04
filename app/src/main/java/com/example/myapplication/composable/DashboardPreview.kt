package com.example.myapplication.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.TextSizeUtils


@Composable()
@Preview()
fun DashBoardPreview(name: String = "Guest") {
    Column(modifier = Modifier.padding(20.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = painterResource(id = R.drawable.account_circle_outline),
                contentDescription = "avatar",
                modifier = Modifier.size(50.dp)
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Hello, $name", fontSize = TextSizeUtils.LARGE, modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp)
                    ,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Column(modifier = Modifier.padding(top = 10.dp)) {
            Text(text = "Goal today: 56%", fontSize = TextSizeUtils.MEDIUM)
            LinearProgressIndicator(
                progress = 0.56.toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                ,
                color = Color(ColorUtils.primary)
            )
        }
    }
}