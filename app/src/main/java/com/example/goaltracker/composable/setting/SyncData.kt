package com.example.goaltracker.composable.setting

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
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
import com.example.goaltracker.composable.CustomDialog
import com.example.goaltracker.composable.SyncDataContainer
import com.example.goaltracker.repositories.user.LocalDataUser
import com.example.goaltracker.utils.ColorUtils
import com.example.goaltracker.utils.isInternetAvailable

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