package com.example.myapplication.navigation

import android.widget.RelativeLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.composable.DashBoardPreview
import com.example.myapplication.composable.GoalList
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.Navigation

@Composable()
fun HomePage(navController: NavController) {
//    Button(onClick = {
//        navController.navigate("sign-in")
//    }) {
//        Text(text = "navigate to sign-in page")
//    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Navigation.CREAT_GOAL)
            }, backgroundColor = Color(ColorUtils.primary)) {
                Icon(Icons.Rounded.Add, contentDescription = null, tint = Color.White)
            }
        },
        backgroundColor = Color(ColorUtils.background)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            DashBoardPreview()
            GoalList()
        }
    }
}