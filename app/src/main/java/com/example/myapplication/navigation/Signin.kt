package com.example.myapplication.navigation

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable()
fun SigninPage(navController: NavController){
    Button(onClick = {
        navController.navigate("home")
    }) {
        Text(text = "navigate to home")
    }
}