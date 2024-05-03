package com.example.goaltracker.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.goaltracker.utils.TextSizeUtils

@Composable()
fun CustomDialog(
    onDismissRequest: () -> Unit,
    title: String = "",
    subtitle: String = "",
    Body: @Composable (() -> Unit)? = null,
    ConfirmBtn: @Composable (() -> Unit)? = null,
    DeclineBtn: @Composable (() -> Unit)? = null) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(5.dp)
        ) {
            Text(
                text = title,
                fontSize = TextSizeUtils.LARGE,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = subtitle,
                fontSize = TextSizeUtils.MEDIUM,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Body?.invoke()
            DeclineBtn?.invoke()
            ConfirmBtn?.invoke()
        }

    }
}