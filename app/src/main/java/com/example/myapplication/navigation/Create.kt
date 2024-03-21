package com.example.myapplication.navigation

import CustomNotification
import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.repositories.goal.Goal
import com.example.myapplication.repositories.goal.LocalData
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.Navigation
import com.example.myapplication.utils.TextSizeUtils
import com.example.myapplication.utils.TimeUtils
import java.sql.Time
import java.util.Calendar
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Locale

fun checkgoal(title:String, time: Long):String{
    if(title.isNullOrEmpty()) return "Không được để trống!!"

    val timenow=TimeUtils.toCalendar(TimeUtils.toISOString(LocalDateTime.now().hour,LocalDateTime.now().minute))?.timeInMillis
    if(time< timenow!!){
        return "Thời gian không được nhỏ hơn thời gian hiện tại!!"
    }
    return ""
}
@OptIn(ExperimentalMaterial3Api::class)
fun onSave(context: Context, goal: Goal, notifyTime: TimePickerState) {
    var payload = goal
    val time=TimeUtils.toISOString(notifyTime.hour, notifyTime.minute)
    val notificationTime= TimeUtils.toCalendar(time)?.timeInMillis
    if (goal.hasNotfication) {
        payload = goal.copy(notifyAt = time)
    }
    val localData = LocalData(context)
    localData.add(payload)
    if (notificationTime != null) {
        CustomNotification(context,goal.name,"Đến giờ thực hiện rồi!!!", R.drawable.ic_notifications_black_24dp,notificationTime)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable()
fun CreateGoalPage(navController: NavController) {
    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var goalData by remember {
        mutableStateOf(Goal(_id = "0", name = "", notifyAt = "", createdAt = "", user = null))
    }
    val notifyAt = rememberTimePickerState(0, 0, is24Hour = true)
    LazyColumn(modifier = Modifier.padding(20.dp)) {
        item { Text(
            text = "Thêm mục tiêu mới",
            fontSize = TextSizeUtils.LARGE,
            fontWeight = FontWeight.Bold
        )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                value = goalData.name,
                onValueChange = { goalData = goalData.copy(name = it) },
                label = {
                    Text("Tiêu đề")
                },
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .fillMaxWidth(),
                textStyle = TextStyle(fontSize = TextSizeUtils.MEDIUM),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(
                    text = "Thông báo",
                    modifier = Modifier.weight(1f),
                    fontSize = TextSizeUtils.MEDIUM
                )
                Switch(
                    checked = goalData.hasNotfication,
                    onCheckedChange = {
                        goalData = goalData.copy(hasNotfication = it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Color(ColorUtils.primary)
                    )
                )
            }
            if (goalData.hasNotfication) TimePicker(
                state = notifyAt,
                modifier = Modifier.fillMaxWidth(),
                colors = TimePickerDefaults.colors(selectorColor = Color(ColorUtils.primary)),
            )
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
                    val time=TimeUtils.toISOString(notifyAt.hour,notifyAt.minute)
                    val notificationTime= TimeUtils.toCalendar(time)?.timeInMillis

                    if (notificationTime != null) {
                        loading=true
                        errorMessage= checkgoal(goalData.name,notificationTime)
                        if(errorMessage==""){
                            onSave(context, goalData, notifyAt)
                            navController.navigate(Navigation.HOME)
                            loading=false
                        }
                        else{
                            loading=false
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
            } }
    }

}


