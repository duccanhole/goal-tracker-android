package com.example.myapplication.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.myapplication.repositories.goal.Goal
import com.example.myapplication.repositories.goal.LocalData
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.TextSizeUtils

fun removeLocalGoal(localData: LocalData, goal: Goal, list: List<Goal>): List<Goal> {
    localData.remove(goal)
    return list.filter { item -> item._id != goal._id }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview()
@Composable()
fun GoalList(isDone: Boolean = false) {
    val context = LocalContext.current
    val localData = LocalData(context,"local_data.json")
    var list = remember {
        localData.get().toList()
    }

    var dialogConfirm = remember {
        mutableStateOf<Boolean>(false)
    }
    var goalSelected = remember {
        mutableStateOf<Goal?>(null)
    }
    Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
        Row {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(Color(ColorUtils.accent)),
                shape = MaterialTheme.shapes.small
            ) {
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
            items(list) { item ->
                GoalItem(item, onChecked = {}, onDelete = {
                    goalSelected.value = item
                    dialogConfirm.value = true
                })
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
    if (dialogConfirm.value) {
        Dialog(onDismissRequest = {
            goalSelected.value = null
            dialogConfirm.value = false
        }) {

            Column(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(5.dp)
            ) {
                Text(
                    text = "Xác nhận Xóa ?",
                    fontSize = TextSizeUtils.LARGE,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Dữ liệu sau khi xóa sẽ không thể khôi phục",
                    fontSize = TextSizeUtils.MEDIUM
                )
                Button(
                    onClick = {
                        goalSelected.value = null
                        dialogConfirm.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Gray
                    )
                ) {
                    Text(text = "Hủy")
                }
                Button(
                    onClick = {
                        list = removeLocalGoal(localData, goalSelected.value!!, list)
                        goalSelected.value = null
                        dialogConfirm.value = false
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(ColorUtils.primary)
                    )
                ) {
                    Text(text = "Xóa")
                }
            }

        }
    }
}