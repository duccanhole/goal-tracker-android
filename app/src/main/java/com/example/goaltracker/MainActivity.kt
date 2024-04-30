package com.example.goaltracker

import BottomBar
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.goaltracker.navigation.CreateGoalPage
import com.example.goaltracker.navigation.HomePage
import com.example.goaltracker.navigation.SettingPage
import com.example.goaltracker.navigation.SigninPage
import com.example.goaltracker.navigation.SignupPage
import com.example.goaltracker.navigation.StatisticPage
import com.example.goaltracker.navigation.UpdateGoalPage
import com.example.goaltracker.utils.ColorUtils
import com.example.goaltracker.utils.Navigation

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App() {
    val navController = rememberNavController()

    val currentDestination by navController.currentBackStackEntryAsState()

    Column(
        modifier = Modifier.background(color = Color(ColorUtils.background))
    ) {
        NavHost(
            navController = navController,
            startDestination = Navigation.HOME,
            modifier = Modifier.weight(1f)
        ) {
            composable(Navigation.HOME) { HomePage(navController) }
            composable(Navigation.STATISTIC) { StatisticPage() }
            composable(Navigation.SETTING) { SettingPage(navController) }
            composable(Navigation.SIGN_IN) {
                SigninPage(
                    navController
                )
            }
            composable(Navigation.SIGN_UP) { SignupPage(navController) }
            composable(Navigation.CREAT_GOAL) { CreateGoalPage(navController) }
            composable(Navigation.UPDATE_GOAL) { backStackEntry ->
                UpdateGoalPage(
                    navController,
                    backStackEntry.arguments?.getString("id")
                )
            }
        }
//        val currentDestination = navController.currentDestination?.route
        if (currentDestination?.destination?.route !in listOf(
                Navigation.SIGN_IN,
                Navigation.SIGN_UP
            )
        ) {
            BottomBar(navController)
        }
    }
}
