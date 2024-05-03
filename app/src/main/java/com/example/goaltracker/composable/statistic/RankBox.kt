package com.example.goaltracker.composable.statistic

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.goaltracker.utils.ColorUtils
import com.example.goaltracker.utils.TextSizeUtils

@SuppressLint("DiscouragedApi")
@Composable
fun RankBox(goalcomplete:Int){
    val context = LocalContext.current
    val rank by remember {
        mutableStateOf(
            when {
                goalcomplete > 99 -> 4
                goalcomplete > 49 -> 3
                goalcomplete > 19 -> 2
                goalcomplete > 9 -> 1
                else -> 0
            }
        )
    }
    val imageFileName = "rank$rank"
    val imageResourceId = context.resources.getIdentifier(imageFileName, "drawable", context.packageName)
    val painter = painterResource(id = imageResourceId)
    Box(
        modifier = Modifier
            .padding(20.dp)
            .background(Color(ColorUtils.accent), shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ){
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painter,
                contentDescription = "Rank__image"
            )
            Column(
                modifier = Modifier
                    .padding(0.dp, 0.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Thành tựu $rank",
                    modifier= Modifier.align(Alignment.CenterHorizontally),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextSizeUtils.LARGE)
                Text(text = "Đã hoàn thành $goalcomplete số mục tiêu đề ra",
                    modifier= Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(20.dp, 0.dp),
                    color = Color.Black,
                    fontSize = TextSizeUtils.SMALL
                )
            }

        }
    }
}