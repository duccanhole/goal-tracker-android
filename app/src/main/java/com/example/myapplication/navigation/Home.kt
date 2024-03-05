package com.example.myapplication.navigation

import android.widget.RelativeLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.composable.DashBoardPreview
import com.example.myapplication.composable.GoalList
import com.example.myapplication.utils.ColorUtils

@Composable()
fun HomePage(navController: NavController) {
//    Button(onClick = {
//        navController.navigate("sign-in")
//    }) {
//        Text(text = "navigate to sign-in page")
//    }
    Box(modifier = Modifier.background(color = Color(ColorUtils.background))){
        Column {
            DashBoardPreview()
            GoalList()
        }
    }
}