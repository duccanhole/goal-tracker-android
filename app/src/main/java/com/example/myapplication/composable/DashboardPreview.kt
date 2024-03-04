package com.example.myapplication.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R


@Composable()
@Preview()
fun DashBoardPreview() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.padding(20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.account_circle_outline),
            contentDescription = "avatar",
            modifier = Modifier.size(50.dp)
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Hello, Guest", fontSize = 24.sp, modifier = Modifier.fillMaxWidth().padding(start = 10.dp))
        }
    }
}