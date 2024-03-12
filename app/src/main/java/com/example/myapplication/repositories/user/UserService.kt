package com.example.myapplication.repositories.user

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.Objects

data class LoginRequest (
    val username: String,
    val password: String
)
data class LoginResponse (
    val result: LoginResult
)

data class LoginResult (
    val token: String,
    val result: LoginResult
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
interface UserService {
    @POST("/user/create")
    fun createUser(@Body payload: SignupRequest):Call<SignupReponse>

    @POST("/user/login")
    fun login(@Body payload: LoginRequest): Call<LoginResponse>

}