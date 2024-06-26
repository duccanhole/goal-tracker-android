package com.example.goaltracker.composable.goal
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cancelNotification
import com.example.goaltracker.composable.CustomDialog
import com.example.goaltracker.navigation.GoalModel
import com.example.goaltracker.repositories.goal.Goal
import com.example.goaltracker.repositories.goal.UpdateAndCreateGoal
import com.example.goaltracker.utils.ColorUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable()
fun GoalList(navController: NavController, goalModel: GoalModel) {
    val context= LocalContext.current
    var dialogConfirm = remember {
        mutableStateOf<Boolean>(false)
    }
    var goalSelected = remember {
        mutableStateOf<Goal?>(null)
    }

    Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
        Row {
            Button(
                onClick = { goalModel.viewByDone = false },
                colors = ButtonDefaults.buttonColors(if (!goalModel.viewByDone) Color(ColorUtils.accent) else Color.Transparent),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Chưa hoàn thành (${goalModel.undone})")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = { goalModel.viewByDone = true },
                colors = ButtonDefaults.buttonColors(if (goalModel.viewByDone) Color(ColorUtils.accent) else Color.Transparent),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Đã hoàn thành (${goalModel.done})")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn {
            items(goalModel.goals) { item ->
                GoalItem(item, loading = goalModel.itemUpdating == item._id,
                    onChecked = {
                        val payload = UpdateAndCreateGoal(isDone = it)
                        goalModel.update(item._id, payload) {}
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
                        goalSelected.value?.let {
                            goalModel.remove(it) {
                                goalSelected.value = null
                                dialogConfirm.value = false
                            }
                            cancelNotification(context,it._id)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(ColorUtils.primary)
                    ),
                    enabled = !goalModel.loadingRemove
                ) {
                    Text(text = "Xóa")
                }
            })
    }
}