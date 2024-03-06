package com.example.myapplication.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.TextSizeUtils
import com.example.myapplication.utils.TextSizeUtils.LARGE

@Composable()
@Preview
fun SigninPage(){
    var username by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    Surface(
        color = Color(ColorUtils.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Đăng nhập",
                modifier = Modifier
                            .padding(bottom = 60.dp),
                fontSize = TextSizeUtils.LARGE,
                fontWeight = FontWeight.Bold

            )
            TextField(
                value = username,
                onValueChange ={username=it},
                label={ Text(text = "Tên Đăng nhập")},
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .fillMaxWidth(),
                textStyle = TextStyle(fontSize =TextSizeUtils.MEDIUM ) ,
                singleLine = true

            )
            TextField(
                value = password,
                onValueChange ={password=it},
                label={ Text(text = "Mật Khẩu")},
                modifier = Modifier
                    .padding(bottom = 30.dp, top = 30.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .fillMaxWidth(),
                textStyle = TextStyle(fontSize =TextSizeUtils.MEDIUM ),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true

            )
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(ColorUtils.primary), contentColor = Color.White)

            ) {
                Text(text = "Đăng nhập", fontSize = TextSizeUtils.SMALL)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Chưa có tài khoản ?")
                TextButton(
                    onClick = { /*TODO*/ },
                ) {
                    Text(
                        text = "Đăng ký ngay ",
                        style= TextStyle(
                            Color(ColorUtils.primary),
                            textDecoration = TextDecoration.Underline),
                            fontSize = TextSizeUtils.SMALL

                    )
                }
            }
        }
    }

}