package com.example.myapplication.navigation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Mail
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material.icons.rounded.Sync
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.composable.CustomDialog
import com.example.myapplication.composable.setting.SettingItem
import com.example.myapplication.composable.SyncDataContainer
import com.example.myapplication.composable.setting.ChangePassword
import com.example.myapplication.composable.setting.SyncData
import com.example.myapplication.repositories.goal.LocalData
import com.example.myapplication.repositories.user.LocalDataUser
import com.example.myapplication.repositories.user.UserRepo
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.Navigation
import com.example.myapplication.utils.TextSizeUtils

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