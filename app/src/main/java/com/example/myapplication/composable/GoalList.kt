package com.example.myapplication.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cancelNotification
import com.example.myapplication.repositories.goal.Goal
import com.example.myapplication.repositories.goal.LocalData
import com.example.myapplication.utils.ColorUtils

fun removeLocalGoal(localData: LocalData, goal: Goal) {
    localData.remove(goal)
}

fun updateLocalGoal(localData: LocalData, goal: Goal) {
    localData.update(goal)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable()
fun GoalList(navController: NavController) {
    val context = LocalContext.current
    val localData = LocalData(context)
    var list = remember {
        mutableStateListOf<Goal>(*localData.getAll())
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
                GoalItem(item, onChecked = {
                    val newItem = item.copy(isDone = it)
                    updateLocalGoal(localData, newItem)
                    list[list.indexOf(item)] = newItem
                },
                    onDelete = {
                        goalSelected.value = item
                        dialogConfirm.value = true

                    },
                    onEdit = {
                        navController.navigate("update/${item._id}")

                    })
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
    if (dialogConfirm.value) {
//        Dialog(onDismissRequest = {
//            goalSelected.value = null
//            dialogConfirm.value = false
//        }) {
//
//            Column(
//                modifier = Modifier
//                    .background(
//                        color = Color.White,
//                        shape = MaterialTheme.shapes.medium
//                    )
//                    .padding(5.dp)
//            ) {
//                Text(
//                    text = "Xác nhận Xóa ?",
//                    fontSize = TextSizeUtils.LARGE,
//                    fontWeight = FontWeight.Bold,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.fillMaxWidth()
//                )
//                Text(
//                    text = "Dữ liệu sau khi xóa sẽ không thể khôi phục",
//                    fontSize = TextSizeUtils.MEDIUM,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.fillMaxWidth()
//                )
//                Button(
//                    onClick = {
//                        goalSelected.value = null
//                        dialogConfirm.value = false
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.Transparent,
//                        contentColor = Color.Gray
//                    )
//                ) {
//                    Text(text = "Hủy")
//                }
//                Button(
//                    onClick = {
//                        removeLocalGoal(localData, goalSelected.value!!)
//                        list.remove(goalSelected.value)
//                        goalSelected.value = null
//                        dialogConfirm.value = false
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(ColorUtils.primary)
//                    )
//                ) {
//                    Text(text = "Xóa")
//                }
//            }
//
//        }
        CustomDialog(onDismissRequest = {
            goalSelected.value = null
            dialogConfirm.value = false
        },
            title = "Xác nhận Xóa ?",
            subtitle = "Dữ liệu sau khi xóa sẽ không thể khôi phục",
            DeclineBtn = {
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
            },
            ConfirmBtn = {
                Button(
                    onClick = {
                        removeLocalGoal(localData, goalSelected.value!!)
                        list.remove(goalSelected.value)
                        goalSelected.value = null
                        dialogConfirm.value = false
                        val notifyId=goalSelected.value?._id?.toInt()
                        if (notifyId != null) {
                            cancelNotification(context,notifyId)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(ColorUtils.primary)
                    )
                ) {
                    Text(text = "Xóa")
                }
            })
    }
}