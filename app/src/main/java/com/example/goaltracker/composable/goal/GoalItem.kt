package com.example.goaltracker.composable.goal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.goaltracker.repositories.goal.Goal
import com.example.goaltracker.utils.ColorUtils
import com.example.goaltracker.utils.TextSizeUtils
import com.example.goaltracker.utils.TimeUtils

@Composable()
fun GoalItem(goalItem: Goal, loading: Boolean = false, onChecked: (value: Boolean) -> Unit, onDelete: () -> Unit, onEdit: () -> Unit) {
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
                checked = goalItem.isDone,
                onCheckedChange = onChecked,
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Color.White,
                    checkedColor = Color(ColorUtils.secondary)
                ),
                enabled = !loading
            )
            Box(modifier = Modifier.weight(1f)) {
                Column {
                    Text(
                        text = goalItem.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = TextSizeUtils.MEDIUM,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(textDecoration = if(goalItem.isDone) TextDecoration.LineThrough else TextDecoration.None)
                    )
                    if (goalItem.hasNotification) {
                        Row {
                            Icon(
                                Icons.Rounded.Notifications,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Text(
                                text = TimeUtils.formatDate(goalItem.notifyAt),
                                fontSize = TextSizeUtils.MEDIUM,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            if(!goalItem.isDone) {
                IconButton(
                    onClick = onEdit,
                    enabled = !loading
                ) {
                    Icon(Icons.Rounded.Edit, contentDescription = null, tint = Color.White)
                }
            }
            IconButton(
                onClick = onDelete,
                enabled = !loading
            ) {
                Icon(Icons.Rounded.Delete, contentDescription = null, tint = Color(0xFFCC0000))
            }
        }
    }
}