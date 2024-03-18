package com.example.myapplication.navigation

import android.graphics.drawable.Icon
import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.composable.DashBoardPreview
import com.example.myapplication.repositories.user.LocalDataUser
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.Navigation
import com.example.myapplication.utils.TextSizeUtils

@Composable
fun ItemSetting( name:String,icon: ImageVector,onClick: () -> Unit,color:Color= Color(ColorUtils.accent)){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = color, contentColor = Color.White),
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 5.dp, end = 5.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 10.dp)
            )
            Text(
                text = name,
                fontSize = TextSizeUtils.MEDIUM
            )
        }
    }
    Spacer(modifier = Modifier.height(5.dp))
}
@Composable()
fun SettingPage(navController: NavController) {
    val context= LocalContext.current
    val userLocal = LocalDataUser(context)
    val userInfor = userLocal.getUser()
    Surface(
        color= Color(ColorUtils.background)
    ) {
        Column(
            modifier= Modifier.fillMaxSize(),
            horizontalAlignment =Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Setting",
                modifier = Modifier
                    .padding(bottom = 60.dp, top = 20.dp),
                fontSize = TextSizeUtils.LARGE,
                fontWeight = FontWeight.Bold
            )
            if(userInfor?.username.isNullOrEmpty()) {
               Column(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(30.dp)
               ) {
                   ItemSetting(name = "Đăng nhập", icon =Icons.Rounded.Mail, onClick = {
                       navController.navigate(Navigation.SIGN_IN)
                   })
               }
                
            }
                else{
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp)
                ) {

                    item() {
                        Text(
                            text="Acount",
                            fontSize = TextSizeUtils.MEDIUM,

                            )
                        ItemSetting(name = "Xem thông tin ", icon = Icons.Rounded.AccountCircle, onClick = {null})
                        ItemSetting(name = "Đổi mật khẩu", icon = Icons.Rounded.Password, onClick = {})

                        ItemSetting(
                            name= "Đăng xuất"
                            , icon = Icons.Rounded.ExitToApp,
                            onClick = {
                                Log.d("App","đã đăng xuất")
                                navController.navigate(Navigation.HOME)
                                userLocal.clear()
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
                        text="App",
                        fontSize = TextSizeUtils.MEDIUM,
                    )
                    ItemSetting(name = "Chuyển đổi giao diện", icon = Icons.Rounded.WbSunny, onClick = {null})
                    ItemSetting(name = "Đồng bộ dữ liệu", icon = Icons.Rounded.Sync, onClick = {null})
                    ItemSetting(name = "Thông tin  về ứng dụng", icon = Icons.Rounded.ErrorOutline, onClick = {null})

                }
            }
        }
    }

}