package com.example.goaltracker.navigation

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cancelNotification
import com.example.goaltracker.R
import com.example.goaltracker.repositories.goal.GoalRepo
import com.example.goaltracker.repositories.goal.LocalData
import com.example.goaltracker.repositories.goal.UpdateAndCreateGoal
import com.example.goaltracker.utils.ColorUtils
import com.example.goaltracker.utils.Navigation
import com.example.goaltracker.utils.TextSizeUtils
import com.example.goaltracker.utils.TimeUtils
import setupNotification
import java.util.Calendar

data class TimeState(
    val hour: Int,
    val minute: Int
)

@OptIn(ExperimentalMaterial3Api::class)
fun onUpdate(
    context: Context,
    id: String,
    goal: UpdateAndCreateGoal,
    notifyTime: TimeState,
    callback: () -> Unit
) {
    var payload = goal
    val time = TimeUtils.toISOString(notifyTime.hour, notifyTime.minute)
    val notificationTime = TimeUtils.toCalendar(time)?.timeInMillis
    if (goal.hasNotification) {
        payload = goal.copy(notifyAt = time)
    }
    val localData = LocalData(context)
    // remove old notification
    cancelNotification(context, id)
    GoalRepo.getInstance().updateGoal(id, payload) { response, throwable ->
        if (throwable != null) {
            localData.update(id, goal)
        } else {
            localData.update(id, response!!.result)
        }
        // create new notification
        setupNotification(
            context,
            id,
            goal.name!!,
            "Đến giờ thực hiện rồi!!!",
            R.drawable.ic_notifications_black_24dp,
            notificationTime
        )
        callback()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable()
fun UpdateGoalPage(navController: NavHostController, id: String?) {
    val context = LocalContext.current
    val localData = LocalData(context)
    val goal = localData.get(id!!)!!
    var errorMessage by remember { mutableStateOf("") }
    var loading by remember {
        mutableStateOf<Boolean>(false)
    }
    var goalData by remember {
        mutableStateOf<UpdateAndCreateGoal>(UpdateAndCreateGoal(name = ""))
    }
    var notifyAt by remember {
        mutableStateOf(TimeState(hour = 0, minute = 0))
    }

    LaunchedEffect(Unit) {
        loading = true
        GoalRepo.getInstance().getGoalDetail(id) {
            response, throwable ->
            if(throwable != null) {
                val cal = TimeUtils.toCalendar(goal.notifyAt)
                var hour = if(goal.hasNotification && cal != null) cal.get(Calendar.HOUR_OF_DAY) else 0
                val minus = if(goal.hasNotification && cal != null) cal.get(Calendar.MINUTE) else 0
                notifyAt = notifyAt.copy(hour = hour, minute = minus)
                goalData = UpdateAndCreateGoal(name = goal.name, isDone = goal.isDone, hasNotification = goal.hasNotification)
            }
            else {
                val result = response!!.result
                val cal = TimeUtils.toCalendar(result.notifyAt)
                val hour = if(result.hasNotification && cal != null) cal.get(Calendar.HOUR_OF_DAY) else 0
                val minus = if(result.hasNotification && cal != null) cal.get(Calendar.MINUTE) else 0
                Log.d("App", "$hour, $minus line 121")
                notifyAt = notifyAt.copy(hour = hour, minute = minus)
                goalData = UpdateAndCreateGoal(name = result.name, isDone = result.isDone, hasNotification = result.hasNotification)
            }
            loading = false
        }
    }
    LazyColumn(modifier = Modifier.padding(20.dp)) {
       item {
           Text(
               text = "Cập nhật mục tiêu ${notifyAt.hour}, ${notifyAt.minute}",
               fontSize = TextSizeUtils.LARGE,
               fontWeight = FontWeight.Bold
           )
           Spacer(modifier = Modifier.height(15.dp))
           TextField(
               value = goalData.name!!,
               onValueChange = { goalData = goalData.copy(name = it) },
               label = {
                   Text("Tiêu đề")
               },
               modifier = Modifier
                   .clip(shape = RoundedCornerShape(8.dp))
                   .background(Color.White)
                   .fillMaxWidth(),
               textStyle = TextStyle(fontSize = TextSizeUtils.MEDIUM),
               singleLine = true,
               enabled = !loading
           )
           Spacer(modifier = Modifier.height(10.dp))
           Row {
               Text(
                   text = "Thông báo",
                   modifier = Modifier.weight(1f),
                   fontSize = TextSizeUtils.MEDIUM
               )
               Switch(
                   checked = goalData.hasNotification,
                   onCheckedChange = {
                       goalData = goalData.copy(hasNotification = it)
                   },
                   colors = SwitchDefaults.colors(
                       checkedTrackColor = Color(ColorUtils.primary)
                   ),
                   enabled = !loading
               )
           }
           if (goalData.hasNotification) {
               Row {
                   TextField(
                       value = notifyAt.hour.toString(),
                       keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                       onValueChange = {
                           notifyAt = notifyAt.copy(hour = it.toInt())
                       },
                       modifier = Modifier.weight(1f)
                   )
                   Spacer(modifier = Modifier.width(5.dp))
                   TextField(
                       value = notifyAt.minute.toString(),
                       keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                       onValueChange = {
                           notifyAt = notifyAt.copy(minute = it.toInt())
                       },
                       modifier = Modifier.weight(1f)
                   )
               }
//               TimePicker(
//                   state = notifyAt,
//                   modifier = Modifier.fillMaxWidth(),
//                   colors = TimePickerDefaults.colors(selectorColor = Color(ColorUtils.primary))
//               )
           }
           Spacer(modifier = Modifier.height(10.dp))
           if (errorMessage.isNotEmpty()) {
               androidx.compose.material.Text(
                   text = errorMessage,
                   color = Color.Red,
                   fontSize = TextSizeUtils.SMALL,
                   modifier = Modifier.padding(top = 8.dp)
               )
           }
           Button(
               onClick = {
                   navController.navigate(Navigation.HOME)
               },
               modifier = Modifier
                   .fillMaxWidth()
                   .height(40.dp),
               shape = MaterialTheme.shapes.medium,
               colors = ButtonDefaults.buttonColors(
                   containerColor = Color(ColorUtils.accent)
               )
           ) {
               Text(text = "Hủy", fontSize = TextSizeUtils.MEDIUM)
           }
           Spacer(modifier = Modifier.height(5.dp))
           Button(
               onClick = {
                   val time = TimeUtils.toISOString(notifyAt.hour, notifyAt.minute)
                   val notificationTime = TimeUtils.toCalendar(time)?.timeInMillis

                   if (notificationTime != null) {
                       loading = true
                       errorMessage = checkgoal(goalData.name, notificationTime, goal.hasNotification)
                       if (errorMessage == "") {
                           onUpdate(context, id, goalData, notifyAt) {
                               Toast.makeText(context, "Lưu thành công", Toast.LENGTH_LONG).show()
                               navController.navigate(Navigation.HOME)
                               loading = false
                           }
                       } else {
                           loading = false
                       }
                   }
               },
               modifier = Modifier
                   .fillMaxWidth()
                   .height(40.dp),
               shape = MaterialTheme.shapes.medium,
               colors = ButtonDefaults.buttonColors(
                   containerColor = Color(ColorUtils.primary)
               ),
               enabled = !loading
           ) {
               Text(text = "Lưu", fontSize = TextSizeUtils.MEDIUM)
           }
       }
    }
}