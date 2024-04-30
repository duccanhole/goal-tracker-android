package com.example.goaltracker.navigation

import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.goaltracker.repositories.user.UserRepo
import com.example.goaltracker.utils.ColorUtils
import com.example.goaltracker.utils.Navigation
import com.example.goaltracker.utils.TextSizeUtils
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.goaltracker.R
import com.example.goaltracker.composable.GoogleLoginBtn
import com.example.goaltracker.repositories.user.LocalDataUser
import com.example.goaltracker.repositories.user.LoginResult
import com.example.goaltracker.repositories.user.UserData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

interface LoginCallback {
    fun onSuccess(Token: String, Id: String, Username: String)
    fun onError(errormessage: String)
}

fun onLogin(username: String, password: String, callback: LoginCallback) {
    UserRepo.getInstance().login(username, password) { res, err ->
        run {
            if (err != null) {
                callback.onError("Đã xảy ra lỗi khi đăng nhập")
            } else {
                Log.d("App", "login resul: ${res.toString()}")
                callback.onSuccess(
                    res?.result?.token.toString(),
                    res?.result?.userData?._id.toString(),
                    res?.result?.userData?.username.toString()
                )

            }

        }
    }
}

fun checkvalidate(username: String, password: String): String {
    if (username.isBlank() || password.isBlank()) return "Không được để trống !!"
    if (username.length < 3) return "Username quá ngắn!!"
    if (password.length < 6) return "Password quá ngắn!!"
    return ""
}

@Composable()
fun SigninPage(
    navController: NavController
) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var loading by remember {
        mutableStateOf(false)
    }

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
                onValueChange = { username = it },
                label = { Text(text = "Tên Đăng nhập") },
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .fillMaxWidth(),
                textStyle = TextStyle(fontSize = TextSizeUtils.MEDIUM),
                singleLine = true

            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Mật Khẩu") },
                modifier = Modifier
                    .padding(bottom = 30.dp, top = 30.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .fillMaxWidth(),
                textStyle = TextStyle(fontSize = TextSizeUtils.MEDIUM),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true

            )
            Button(
                onClick = {
                    errorMessage = checkvalidate(username, password)
                    loading = true
                    if (errorMessage == "") {
                        onLogin(username = username, password = password, object : LoginCallback {
                            override fun onSuccess(Token: String, Id: String, Username: String) {
                                val localData = LocalDataUser(context)
                                val user = LoginResult(Token, UserData(Id, Username))
                                localData.setUser(user)
                                navController.navigate(Navigation.HOME)
                                loading = false

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
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(ColorUtils.primary),
                    contentColor = Color.White
                ),
                enabled = !loading
            ) {
                Text(
                    text = if (loading) "Đang xử lý" else "Đăng nhập",
                    fontSize = TextSizeUtils.SMALL
                )
            }
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = TextSizeUtils.SMALL,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            GoogleLoginBtn(navController)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Chưa có tài khoản ?")
                TextButton(
                    onClick = { navController.navigate(Navigation.SIGN_UP) },
                ) {
                    Text(
                        text = "Đăng ký ngay ",
                        style = TextStyle(
                            Color(ColorUtils.primary),
                            textDecoration = TextDecoration.Underline
                        ),
                        fontSize = TextSizeUtils.SMALL

                    )
                }
            }
        }
    }

}