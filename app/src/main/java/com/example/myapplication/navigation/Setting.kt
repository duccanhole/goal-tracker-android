package com.example.myapplication.navigation

import android.graphics.drawable.Icon
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.ImportExport
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.composable.CustomDialog
import com.example.myapplication.composable.DashBoardPreview
import com.example.myapplication.composable.SettingItem
import com.example.myapplication.repositories.goal.LocalData
import com.example.myapplication.repositories.user.LocalDataUser
import com.example.myapplication.repositories.user.UserRepo
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.Navigation
import com.example.myapplication.utils.TextSizeUtils
import java.util.Calendar

interface ChangePassCallback {
    fun onSuccess(result: String)
    fun onError(errormessage: String)
}

fun onChangePassword(
    token: String,
    userid: String,
    oldpassword: String,
    newpassword: String,
    callback: ChangePassCallback
) {
    UserRepo.getInstance().ChangePassword(token, userid, oldpassword, newpassword) { res, err ->
        run {
            if (err != null) {
                callback.onError("Đã  xảy ra lỗi!!!")
            } else {
                callback.onSuccess(res?.result.toString())
            }
        }
    }
}

fun checkvalidatepass(oldpassword: String, newpassword: String): String {
    if (oldpassword.isBlank() || newpassword.isBlank()) return "Không được để trống !!"
    if (oldpassword.length < 6) return "Mật kẩu cũ quá ngắn!!"
    if (newpassword.length < 6) return "Mật kẩu mới quá ngắn!!"
    if (newpassword == oldpassword) return "Mật khẩu mới không được trùng mới mật khẩu cũ!"
    return ""
}

@Composable()
fun SettingPage(navController: NavController) {
    val context = LocalContext.current
    val userLocal = LocalDataUser(context)
    val localData = LocalData(context)
    val userInfor = userLocal.getUser()
    var errorMessage by remember { mutableStateOf("") }
    var loading by remember {
        mutableStateOf(false)
    }
    var oldPassword by remember {
        mutableStateOf("")
    }
    var newPassword by remember {
        mutableStateOf("")
    }
    var showdialog by remember {
        mutableStateOf(false)
    }
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
                text = "Setting",
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

                    item() {
                        Text(
                            text = "Acount",
                            fontSize = TextSizeUtils.MEDIUM,

                            )
                        SettingItem(
                            name = "Xem thông tin ",
                            icon = Icons.Rounded.AccountCircle,
                            onClick = { null })
                        SettingItem(
                            name = "Đổi mật khẩu",
                            icon = Icons.Rounded.Password,
                            onClick = {
                                showdialog = true
                            })

                        SettingItem(
                            name = "Đăng xuất", icon = Icons.Rounded.ExitToApp,
                            onClick = {
//                                navController.navigate(Navigation.HOME)
//                                userLocal.clear()
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
                item() {
                    Text(
                        text = "App",
                        fontSize = TextSizeUtils.MEDIUM,
                    )
                    SettingItem(
                        name = "Chuyển đổi giao diện",
                        icon = Icons.Rounded.WbSunny,
                        onClick = { null })
                    SettingItem(
                        name = "Đồng bộ dữ liệu",
                        icon = Icons.Rounded.Sync,
                        onClick = { null })
                    SettingItem(
                        name = "Thông tin về ứng dụng",
                        icon = Icons.Rounded.ErrorOutline,
                        onClick = { null })
                }
            }
        }
    }
    if (showdialog) {
        CustomDialog(
            onDismissRequest = {
                showdialog = false
            },
            title = "Đổi mật khẩu",
            subtitle = "",
            Body = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    TextField(
                        value = oldPassword,
                        onValueChange = { oldPassword = it },
                        label = { Text(text = "Mật khẩu cũ") },
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .fillMaxWidth(),
                        textStyle = TextStyle(fontSize = TextSizeUtils.MEDIUM),
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true
                    )
                    TextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text(text = "Mật khẩu mới") },
                        modifier = Modifier
                            .padding(bottom = 10.dp, top = 30.dp)
                            .clip(shape = RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .fillMaxWidth(),
                        textStyle = TextStyle(fontSize = TextSizeUtils.MEDIUM),
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true

                    )
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = TextSizeUtils.SMALL,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            },
            DeclineBtn = {
                Button(
                    onClick = {
                        showdialog = false
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = Color.Gray
                    )
                ) {
                    Text(text = "Hủy")
                }
            },
            ConfirmBtn = {
                Button(
                    onClick = {
                        errorMessage = checkvalidatepass(oldPassword, newPassword)
                        loading = true
                        val userid = userInfor?._id.toString()
                        val token = userLocal.getToken().toString()
                        Log.d("App", userid)
                        Log.d("App", token)
                        if (errorMessage == "") {
                            onChangePassword(
                                token,
                                userid,
                                oldPassword,
                                newPassword,
                                object : ChangePassCallback {
                                    override fun onSuccess(result: String) {
                                        Toast.makeText(
                                            context,
                                            "Đổi mật khẩu thành công!!!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        loading = false
                                        showdialog = false
                                        oldPassword = ""
                                        newPassword = ""
                                    }

                                    override fun onError(errormessage: String) {
                                        errorMessage = errormessage
                                        loading = false
                                    }
                                })
                        } else {
                            loading = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(ColorUtils.primary),
                        contentColor = Color.White
                    ),
                    enabled = !loading
                ) {
                    Text(text = if (loading) "Đang xử lý" else "Đổi mật khẩu")
                }
            }
        )
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