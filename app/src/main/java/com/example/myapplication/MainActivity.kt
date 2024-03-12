package com.example.myapplication

import BottomBar
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.HomePage
import com.example.myapplication.navigation.SettingPage
import com.example.myapplication.navigation.SigninPage
import com.example.myapplication.navigation.SignupPage
import com.example.myapplication.navigation.StatisticPage
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.Navigation

class MainActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }
}

@Composable
fun App() {
    val navController = rememberNavController()

    val currentDestination by navController.currentBackStackEntryAsState()

    Column(
        modifier = Modifier.background(color = Color(ColorUtils.background))
    ) {
        NavHost(
            navController = navController,
            startDestination = Navigation.SIGN_IN,
            modifier = Modifier.weight(1f)
        ) {
            composable(Navigation.HOME) { HomePage(navController) }
            composable(Navigation.STATISTIC) { StatisticPage() }
            composable(Navigation.SETTING) { SettingPage() }
            composable(Navigation.SIGN_IN) { SigninPage(navController) }
            composable(Navigation.SIGN_UP) { SignupPage(navController) }
        }
//        val currentDestination = navController.currentDestination?.route
        if (currentDestination?.destination?.route !in listOf(Navigation.SIGN_IN, Navigation.SIGN_UP)) {
            BottomBar(navController)
        }
    }
}
