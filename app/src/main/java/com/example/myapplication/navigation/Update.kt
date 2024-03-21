package com.example.myapplication.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.repositories.goal.Goal
import com.example.myapplication.repositories.goal.LocalData
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.Navigation
import com.example.myapplication.utils.TextSizeUtils
import com.example.myapplication.utils.TimeUtils
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable()
fun UpdateGoalPage(navController: NavHostController, id: String?) {
    val context = LocalContext.current
    val localData = LocalData(context)
    val goal = localData.get(id!!)!!
    var goalData by remember {
        mutableStateOf<Goal>(goal)
    }
    val cal = TimeUtils.toCalendar(goal.notifyAt)
    val hour = if(goal.hasNotfication && cal != null) cal.get(Calendar.HOUR_OF_DAY) else 0
    val minus = if(goal.hasNotfication && cal != null) cal.get(Calendar.MINUTE) else 0
    val notifyAt = rememberTimePickerState(hour, minus, is24Hour = true)
    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            text = "Cập nhật mục tiêu",
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
//                onSave(context, goalData, notifyAt)
                navController.navigate(Navigation.HOME)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(ColorUtils.primary)
            )
        ) {
            Text(text = "Lưu", fontSize = TextSizeUtils.MEDIUM)
        }
    }
    if(!goal.hasNotfication){

    }
}