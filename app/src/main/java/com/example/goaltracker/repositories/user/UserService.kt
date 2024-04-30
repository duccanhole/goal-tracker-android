package com.example.goaltracker.repositories.user

import android.provider.ContactsContract.CommonDataKinds.Email
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

data class GoogleLoginRequest(
    val email: String,
    val displayName: String,
    val uid: String
)
data class LoginRequest (
    val username: String,
    val password: String
)
data class LoginResponse (
    val result: LoginResult
)

data class LoginResult (
    val token: String,
    val userData: UserData
)
data class SigupResult(
    val result: LoginResult
)
data class UserData (
    val _id: String,
    val username: String
)
data class SignupRequest(
    var username: String,
    var password: String
)
data class SignupReponse(
    val result: SigupResult
)
data class ChangePassRequest(
    val userId:String,
    val oldPassword:String,
    val newPassword:String
)
data class ChangePassResponse(
    val result: String
)
interface UserService {
    @POST("/user/create")
    fun createUser(@Body payload: SignupRequest):Call<SignupReponse>

    @POST("/user/login")
    fun login(@Body payload: LoginRequest): Call<LoginResponse>

    @POST("/user/google-login")
    fun googleLogin(@Body payload: GoogleLoginRequest): Call<LoginResponse>

    @PUT("/user/change-password")
    fun changePass( @Header("token")token: String,@Body payload:ChangePassRequest):Call<ChangePassResponse>

}