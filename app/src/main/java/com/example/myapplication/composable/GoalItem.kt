package com.example.myapplication.composable

import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.TextSizeUtils

@Composable()
@Preview()
fun GoalItem() {
    Box(
        modifier = Modifier.background(
            color = Color(ColorUtils.accent),
            shape = RoundedCornerShape(8.dp)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = false,
                onCheckedChange = {},
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Color.White
                )
            )
            Box(modifier = Modifier.weight(1f)) {
                Column {
                    Text(
                        text = "Goal name",
                        fontWeight = FontWeight.Bold,
                        fontSize = TextSizeUtils.MEDIUM,
                        color = Color.White
                    )
                    Row {
                        Icon(
                            Icons.Rounded.Notifications,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Text(text = "7:30", fontSize = TextSizeUtils.MEDIUM, color = Color.White)
                    }
                }
            }
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(Icons.Rounded.Edit, contentDescription = null, tint = Color.White)
            }
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(Icons.Rounded.Delete, contentDescription = null, tint = Color(0xFFCC0000))
            }
        }
    }
}