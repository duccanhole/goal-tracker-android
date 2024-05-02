package com.example.goaltracker.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WifiOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.goaltracker.utils.ColorUtils
import com.example.goaltracker.utils.TextSizeUtils
import com.example.goaltracker.utils.isInternetAvailable

@Composable()
fun StatisticPage() {
    val context = LocalContext.current
    val isInternetAvailable = isInternetAvailable(context)
    Surface(color = Color(ColorUtils.background)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (isInternetAvailable) {
                Text(text = "Thống kê")
            } else {
                Icon(
                    imageVector = Icons.Rounded.WifiOff,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = "Không có mạng, vui lòng kiểm tra và thử lại.",
                    fontSize = TextSizeUtils.MEDIUM
                )
            }
        }
    }
}