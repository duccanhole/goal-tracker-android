package com.example.goaltracker.composable

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.goaltracker.R
import com.example.goaltracker.repositories.user.LocalDataUser
import com.example.goaltracker.repositories.user.LoginResult
import com.example.goaltracker.repositories.user.UserData
import com.example.goaltracker.repositories.user.UserRepo
import com.example.goaltracker.utils.ColorUtils
import com.example.goaltracker.utils.Navigation
import com.example.goaltracker.utils.TextSizeUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun rememberFirebaseAuthLauncher(
    onAuthComplete: (AuthResult) -> Unit,
    onAuthError: (ApiException) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            scope.launch {
                val authResult = Firebase.auth.signInWithCredential(credential).await()
                onAuthComplete(authResult)
            }
        } catch (e: ApiException) {
            e.printStackTrace()
            Log.e("App", "Exception:" + e?.message)
            onAuthError(e)
        }
    }
}

@Composable()
fun GoogleLoginBtn(navController: NavController) {
    val context = LocalContext.current
    val token = stringResource(id = R.string.web_client_id)
    var loading by remember {
        mutableStateOf(false)
    }
    val launcher = rememberFirebaseAuthLauncher(onAuthComplete = {
        val user = it.user
        if (user?.displayName is String && user.email is String) {
            UserRepo.getInstance()
                .googleLogin(user.email!!, user.displayName!!, user.uid) { response, throwable ->
                    run {
                        if (throwable != null) {
                            Toast.makeText(
                                context,
                                "Đã xảy ra lỗi, vui lòng thử lại",
                                Toast.LENGTH_LONG
                            ).show()
                            Firebase.auth.signOut()
                            loading = false
                        } else {
                            val userLocal = LocalDataUser(context)
                            val userRes = response!!.result.userData
                            val user = LoginResult(
                                response.result.token,
                                UserData(userRes._id, userRes.username)
                            )
                            userLocal.setUser(user)
                            navController.navigate(Navigation.HOME)
                        }
                    }
                }
        }
    }, onAuthError = {
        Firebase.auth.signOut()
        Toast.makeText(context, "Đã xảy ra lỗi, vui lòng thử lại", Toast.LENGTH_LONG).show()
        loading = false
    })
    Button(
        onClick = {
            loading = true
            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token)
                    .requestEmail()
                    .build()
            val googleSignInClient = GoogleSignIn.getClient(context, gso)
            googleSignInClient.signOut()
            launcher.launch(googleSignInClient.signInIntent)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(ColorUtils.secondary),
            contentColor = Color.White
        ),
        enabled = !loading
    ) {
        Text(text = "Đăng nhập bằng tài khoản Google", fontSize = TextSizeUtils.SMALL)
    }
}