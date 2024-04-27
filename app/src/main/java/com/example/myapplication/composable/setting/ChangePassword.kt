package com.example.myapplication.composable.setting

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Password
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.myapplication.composable.CustomDialog
import com.example.myapplication.repositories.user.LocalDataUser
import com.example.myapplication.repositories.user.UserRepo
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.TextSizeUtils

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
fun ChangePassword() {
    val context = LocalContext.current
    val userLocal = LocalDataUser(context)
    val userInfor = userLocal.getUser()
    var showdialog by remember {
        mutableStateOf(false)
    }
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
    SettingItem(
        name = "Đổi mật khẩu",
        icon = Icons.Rounded.Password,
        onClick = {
            showdialog = true
        })
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
}