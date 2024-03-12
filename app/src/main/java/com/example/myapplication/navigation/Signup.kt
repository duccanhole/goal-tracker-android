package com.example.myapplication.navigation

import android.util.Log
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
import androidx.navigation.NavController
import com.example.myapplication.repositories.user.UserRepo
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.Navigation
import com.example.myapplication.utils.TextSizeUtils

interface SignupCallback {
    fun onSuccess()
    fun onError(errormessage: String)
}
fun onSignup(username: String, password: String,callback: SignupCallback) {
    UserRepo.getInstance().login(username, password) { res, err ->
        run {
            if (err != null) {
                Log.d("Apps",err.message.toString())
                callback.onError("Tài khoản đã tồn tại hoặc xãy ra lỗi!!")

            } else {
                callback.onSuccess()

            }
        }
    }
}
fun checkvalidate(username: String,password: String,repassword:String):String{
    if(username.isBlank()||password.isBlank()) return "Không được để trống !!"
    if(username.length<3) return "Username quá ngắn!!"
    if(password.length<6) return "Password quá ngắn!!"
    if(repassword!=password) return "Xác nhận mật khẩu không khớp!!"
    return ""
}
@Composable()
fun SignupPage(navControler:NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
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
                    .padding(top = 30.dp)
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
                onClick = {
                    errorMessage = checkvalidate(username, password, repassword )
                    loading = true
                    Log.d("App",errorMessage)
                    if(errorMessage==""){
                        onSignup(username = username, password = password, object : SignupCallback {
                            override fun onSuccess() {
                                navControler.navigate(Navigation.SIGN_IN)

                                loading = false

                            }

                            override fun onError(errormessage: String) {
                                errorMessage=errormessage
                                loading = false
                            }
                        })
                    }
                    else{
                        loading=false
                    }
                    Log.d("App",errorMessage)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(ColorUtils.primary),
                    contentColor = Color.White),
                enabled = !loading
            ) {
                Text(text = if(loading) "Đang xử lý" else "Đăng ký", fontSize = TextSizeUtils.SMALL)
            }
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = TextSizeUtils.SMALL,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { navControler.navigate(Navigation.SIGN_IN)},
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