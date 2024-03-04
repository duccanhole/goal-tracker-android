package com.example.myapplication.navigation

import android.widget.RelativeLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.composable.DashBoardPreview

@Composable()
fun HomePage(navController: NavController) {
//    Button(onClick = {
//        navController.navigate("sign-in")
//    }) {
//        Text(text = "navigate to sign-in page")
//    }
    Column {
        DashBoardPreview()
    }
}