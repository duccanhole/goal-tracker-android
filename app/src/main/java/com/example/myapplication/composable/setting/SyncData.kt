package com.example.myapplication.composable.setting

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Sync
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.composable.CustomDialog
import com.example.myapplication.composable.SyncDataContainer
import com.example.myapplication.repositories.user.LocalDataUser
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.isInternetAvailable
import kotlin.reflect.typeOf

@Composable()
fun SyncData() {
    val context = LocalContext.current
    val userLocal = LocalDataUser(context)
    val userToken = userLocal.getToken()
    var syncDialog by remember {
        mutableStateOf(false)
    }
    var warningDialog by remember {
        mutableStateOf(false)
    }
    SettingItem(
        name = "Đồng bộ dữ liệu",
        icon = Icons.Rounded.Sync,
        onClick = {
            if (isInternetAvailable(context) && userToken is String && userToken.isNotEmpty())
                syncDialog = true
            else
                warningDialog = true
        })
    if (syncDialog) {
        CustomDialog(title = "Đang xử lý, vui lòng chờ ...", onDismissRequest = { /*TODO*/ }, Body = {
            SyncDataContainer(onFinished = {
                syncDialog = false
            })
        })
    }
    if (warningDialog) {
        CustomDialog(title = "Đồng bộ thất bại",
            subtitle = "Vui lòng đăng nhập tài khoản và kết nối internet.",
            onDismissRequest = { warningDialog = false },
            ConfirmBtn = {
                Button(
                    onClick = { warningDialog = false },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(ColorUtils.primary))
                ) {
                    Text(text = "Đã hiểu", color = Color.White)
                }
            })
    }
}