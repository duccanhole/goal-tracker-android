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
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.repositories.goal.Goal
import com.example.myapplication.repositories.user.LocalDataUser
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.TextSizeUtils

fun getPercent(list: List<Goal>): Float {
    if(list.isEmpty()) return (0).toFloat()
    val done = list.filter { i -> i.isDone }.size
    return (done/list.size).toFloat()
}

@Composable()
fun DashBoardPreview(list: List<Goal>) {
    val context = LocalContext.current
    val userLocal = LocalDataUser(context)
    val userInfor = userLocal.getUser()

    val percent = getPercent(list)
    Column(modifier = Modifier.padding(20.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                Icons.Rounded.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Xin chào, ${if (userInfor?.username.isNullOrEmpty()) "tài khoản ẩn danh" else userInfor!!.username}",
                    fontSize = TextSizeUtils.LARGE,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Column(modifier = Modifier.padding(top = 10.dp)) {
            Text(text = "Mục tiêu hôm nay: ${percent.toInt()}%", fontSize = TextSizeUtils.MEDIUM)
            LinearProgressIndicator(
                progress = percent,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                color = Color(ColorUtils.primary)
            )
        }
    }
}