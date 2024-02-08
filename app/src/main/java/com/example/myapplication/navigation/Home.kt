package com.example.myapplication.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable()
fun HomePage(navController: NavController) {
    Text(text = "this is home page")
    Text(text = "this is home page", color = Color.White)
    Button(onClick = {
        navController.navigate("sign-in")
    }) {
        Text(text = "navigate to sign-in page")
    }
}