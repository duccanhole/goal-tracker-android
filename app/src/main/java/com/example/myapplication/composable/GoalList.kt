package com.example.myapplication.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.utils.ColorUtils

@Preview()
@Composable()
fun GoalList() {
    Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
        Row {
            Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(Color(ColorUtils.accent)), shape = MaterialTheme.shapes.small) {
                Text(text = "Undone: 0")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Done: 2")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn {
            items(25) { index ->
                GoalItem()
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}