package com.example.goaltracker.repositories.user

import android.util.Log
import com.example.goaltracker.utils.BaseUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserRepo {
    private var client: UserService? = null;
    private var instance: UserRepo? = null
    fun getInstance(): UserRepo {
        if (client == null) {
            val retrofit =
                Retrofit.Builder().baseUrl(BaseUrl.URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
            client = retrofit.create(UserService::class.java)
            synchronized(UserRepo::class.java) {
                if (instance == null) {
                    instance = UserRepo
                }
            }

        }

        return instance!!
    }

    fun login(username: String, password: String, callback: (LoginResponse?, Throwable?) -> Unit) {
        val payload = LoginRequest(username, password)
        val call = client?.login(payload)
        call?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful) {
                    response.body()?.let { callback(it, null) }
                }
                else {
                    callback(null, Exception("Error: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback(null, t)
            }

        })
    }

    fun googleLogin(email: String, displayName: String, uid: String, callback: (LoginResponse?, Throwable?) -> Unit) {
        val payload = GoogleLoginRequest(email, displayName, uid)
        val call = client?.googleLogin(payload)
        call?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful) {
                    response.body()?.let { callback(it, null) }
                }
                else {
                    callback(null, Exception("Error: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback(null, t)
            }

        })
    }

    fun register(username: String,password: String,callback: (SignupReponse?, Throwable?) -> Unit){
        val payload=SignupRequest(username, password)
        val call= client?.createUser(payload)
        call?.enqueue(object : Callback<SignupReponse> {
            override fun onResponse(call: Call<SignupReponse>, response: Response<SignupReponse>) {
                if(response.isSuccessful) {
                    response.body()?.let { callback(it, null) }
                }
                else {
                    callback(null, Exception("Error: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<SignupReponse>, t: Throwable) {
                callback(null, t)
            }

        })
    }

    fun ChangePassword(token:String,userId:String,oldPassword:String,newPassword:String ,callback: (ChangePassResponse?, Throwable?) -> Unit){
        val payload=ChangePassRequest(userId, oldPassword, newPassword)
        val call= client?.changePass(token,payload)
        call?.enqueue(object :Callback<ChangePassResponse>{
            override fun onResponse(
                call: Call<ChangePassResponse>,
                response: Response<ChangePassResponse>
            ) {
                if(response.isSuccessful) {
                    response.body()?.let { callback(it, null) }
                    Log.d("App","thanh cong ")
                }
                else {
                    callback(null, Exception("Error: ${response.code()}"))
                    Log.d("App","Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ChangePassResponse>, t: Throwable) {
                callback(null, t)
            }

        })
    }
}