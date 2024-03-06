package com.example.myapplication

import BottomBar
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.HomePage
import com.example.myapplication.navigation.SettingPage
import com.example.myapplication.navigation.SigninPage
import com.example.myapplication.navigation.SignupPage
import com.example.myapplication.navigation.StatisticPage
import com.example.myapplication.utils.ColorUtils

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

    Column(
        modifier = Modifier.background(color = Color(ColorUtils.background))
    ) {
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.weight(1f)
        ) {
            composable("home") { HomePage(navController) }
            composable("statistic") { StatisticPage() }
            composable("setting") { SettingPage() }
            composable("sign-in") { SigninPage() }
            composable("sign-up") { SignupPage() }
        }
        BottomBar(navController)
    }
}
