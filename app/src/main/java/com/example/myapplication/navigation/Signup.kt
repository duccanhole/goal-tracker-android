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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.TextSizeUtils

@Composable()
@Preview
fun SignupPage() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repassword by remember { mutableStateOf("") }
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
                text = "Đăng ký",
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
                textStyle = TextStyle(fontSize = TextSizeUtils.MEDIUM ) ,
                singleLine = true

            )
            TextField(
                value = password,
                onValueChange ={password=it},
                label={ Text(text = "Mật Khẩu")},
                modifier = Modifier
                    .padding( top = 30.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .fillMaxWidth(),
                textStyle = TextStyle(fontSize = TextSizeUtils.MEDIUM ),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true

            )
            TextField(
                value = repassword,
                onValueChange ={repassword=it},
                label={ Text(text = "Xác nhận mật khẩu")},
                modifier = Modifier
                    .padding(bottom = 30.dp, top = 30.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .fillMaxWidth(),
                textStyle = TextStyle(fontSize = TextSizeUtils.MEDIUM ),
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
                Text(text = "Đăng ký ", fontSize = TextSizeUtils.SMALL)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(ColorUtils.secondary), contentColor = Color.White)

            ) {
                Text(text = "Quay lại ", fontSize = TextSizeUtils.SMALL)
            }
        }
    }
}