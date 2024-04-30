package com.example.goaltracker.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Mail
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.goaltracker.composable.CustomDialog
import com.example.goaltracker.composable.setting.SettingItem
import com.example.goaltracker.composable.setting.ChangePassword
import com.example.goaltracker.composable.setting.SyncData
import com.example.goaltracker.repositories.goal.LocalData
import com.example.goaltracker.repositories.user.LocalDataUser
import com.example.goaltracker.utils.ColorUtils
import com.example.goaltracker.utils.Navigation
import com.example.goaltracker.utils.TextSizeUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable()
fun SettingPage(navController: NavController) {
    val context = LocalContext.current
    val userLocal = LocalDataUser(context)
    val localData = LocalData(context)
    val userInfor = userLocal.getUser()
    var confirmLogout by remember {
        mutableStateOf(false)
    }

    Surface(
        color = Color(ColorUtils.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Cài đặt",
                modifier = Modifier
                    .padding(bottom = 60.dp, top = 20.dp),
                fontSize = TextSizeUtils.LARGE,
                fontWeight = FontWeight.Bold
            )
            if (userInfor?.username.isNullOrEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                ) {
                    SettingItem(name = "Đăng nhập", icon = Icons.Rounded.Mail, onClick = {
                        navController.navigate(Navigation.SIGN_IN)
                    })
                }

            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                ) {

                    item {
                        Text(
                            text = "Tài khoản",
                            fontSize = TextSizeUtils.MEDIUM,

                            )
                        SettingItem(
                            name = "Xem thông tin",
                            icon = Icons.Rounded.AccountCircle,
                            onClick = { null })
                        ChangePassword()
                        SettingItem(
                            name = "Đăng xuất", icon = Icons.Rounded.ExitToApp,
                            onClick = {
                                confirmLogout = true
                            },
                            color = Color(ColorUtils.primary)
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp)
            ) {
                item {
                    Text(
                        text = "Ứng dụng",
                        fontSize = TextSizeUtils.MEDIUM,
                    )
                    SyncData()
                    SettingItem(
                        name = "Thông tin về ứng dụng",
                        icon = Icons.Rounded.ErrorOutline,
                        onClick = { null })
                }
            }
        }
    }
    if (confirmLogout) {
        CustomDialog(
            onDismissRequest = { confirmLogout = false },
            title = "Xác nhận đăng xuất",
            subtitle = "Dữ liệu chưa được đồng bộ trên máy của bạn sẽ bị mất, đăng xuất?",
            DeclineBtn = {
                androidx.compose.material3.Button(
                    onClick = {
                        confirmLogout = false
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Gray
                    )
                ) {
                    androidx.compose.material3.Text(text = "Hủy")
                }
            },
            ConfirmBtn = {
                androidx.compose.material3.Button(
                    onClick = {
                        userLocal.clear()
                        localData.clear()
                        Firebase.auth.signOut()
                        confirmLogout = true
                        navController.navigate(Navigation.SIGN_IN)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(ColorUtils.primary)
                    ),
                ) {
                    androidx.compose.material3.Text(text = "Đăng xuất")
                }
            }
        )
    }
}