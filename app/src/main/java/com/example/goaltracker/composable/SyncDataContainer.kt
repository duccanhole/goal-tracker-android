package com.example.goaltracker.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.goaltracker.repositories.goal.Goal
import com.example.goaltracker.repositories.goal.GoalRepo
import com.example.goaltracker.repositories.goal.LocalData
import com.example.goaltracker.repositories.goal.UpdateAndCreateGoal
import com.example.goaltracker.utils.TextSizeUtils
import kotlinx.coroutines.delay

@Composable()
fun SyncDataContainer(onFinished: () -> Unit) {
    val context = LocalContext.current
    val localData = LocalData(context)

    var successCount by remember {
        mutableIntStateOf(0)
    }
    var failCount by remember {
        mutableIntStateOf(0)
    }
    var allSyncDone by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        val unSyncList = localData.getAll().filter { i -> i.user.isNullOrEmpty() }
        if (unSyncList.isEmpty()) {
            allSyncDone = true
            delay(2500)
        } else {
            for (g: Goal in unSyncList) {
                val payload = UpdateAndCreateGoal(
                    name = g.name,
                    isDone = g.isDone,
                    hasNotification = g.hasNotification,
                    notifyAt = g.notifyAt
                )
                GoalRepo.getInstance().createGoal(payload) { response, throwable ->
                    if (throwable != null) {
                        failCount++;
                    } else {
                        localData.update(g._id, response!!.result)
                        successCount++;
                    }
                }
                delay(1500)
            }
        }
        onFinished()
    }
    Column {
        if (allSyncDone) {
            Text(
                text = "Dữ liệu của bạn đã được đồng bộ.", fontSize = TextSizeUtils.MEDIUM,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(
                text = "Thành công: $successCount, thất bại: $failCount",
                fontSize = TextSizeUtils.MEDIUM,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )
        }
    }
}